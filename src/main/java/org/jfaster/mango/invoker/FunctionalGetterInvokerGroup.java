/*
 * Copyright 2014 mango.jfaster.org
 *
 * The Mango Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.jfaster.mango.invoker;

import org.jfaster.mango.exception.NotReadablePropertyException;
import org.jfaster.mango.reflect.TypeToken;
import org.jfaster.mango.util.Strings;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ash
 */
public class FunctionalGetterInvokerGroup implements GetterInvokerGroup {

    private final Type finalType;
    private final String parameterName;
    private String propertyPath;
    private final List<GetterInvoker> invokers;

    private FunctionalGetterInvokerGroup(Type type, String parameterName, String propertyPath) {
        this.parameterName = parameterName;
        this.propertyPath = propertyPath;
        invokers = new ArrayList<GetterInvoker>();
        Class<?> rawType = TypeToken.of(type).getRawType();
        if (Strings.isNotEmpty(propertyPath)) {
            NestedProperty np = new NestedProperty();
            NestedProperty pnp = new NestedProperty();
            for (String propertyName : propertyPath.split("\\.")) {
                np.append(propertyName);
                GetterInvoker invoker = InvokerCache.getNullableGetterInvoker(rawType, propertyName);
                if (invoker == null) {
                    String fullName = Strings.getFullName(parameterName, np.getNestedProperty());
                    String pFullName = Strings.getFullName(parameterName, pnp.getNestedProperty());
                    throw new NotReadablePropertyException("property " + fullName + " is not readable, " +
                            "the type of " + pFullName + " is " + type + ", please check it's get method");
                }
                invokers.add(invoker);
                type = invoker.getReturnType();
                rawType = TypeToken.of(type).getRawType();
                pnp.append(propertyName);
            }
        }
        this.finalType = type;
    }

    public static FunctionalGetterInvokerGroup create(Type type, String parameterName, String propertyPath) {
        return new FunctionalGetterInvokerGroup(type, parameterName, propertyPath);
    }

    @Override
    public Type getFinalType() {
        return finalType;
    }

    @Override
    public Object invoke(Object obj) {
        Object r = obj;
        int size = invokers.size();
        for (int i = 0; i < size; i++) {
            if (r == null) {
                NestedProperty np = new NestedProperty();
                for (int j = 0; j < i; j++) {
                    np.append(invokers.get(i).getName());
                }
                String key = i == 0 ? "parameter" : "property";
                String fullName = Strings.getFullName(parameterName, np.getNestedProperty());
                throw new NullPointerException(key + " " + fullName + " is null");
            }
            r = invokers.get(i).invoke(r);
        }
        return r;
    }

    @Override
    public String getPropertyPath() {
        return propertyPath;
    }

    private static class NestedProperty {

        private StringBuilder nestedProperty = new StringBuilder();
        private int num = 0;

        public void append(String property) {
            if (num++ == 0) {
                nestedProperty.append(property);
            } else {
                nestedProperty.append("." + property);
            }
        }

        public String getNestedProperty() {
            return nestedProperty.toString();
        }

    }

}
