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

package org.jfaster.mango.operator;

import org.jfaster.mango.datasource.DataSourceFactory;
import org.jfaster.mango.datasource.DataSourceType;

import javax.annotation.Nullable;

/**
 * 简单数据源生成器，利用从{@link org.jfaster.mango.annotation.DB#dataSource()}取得的数据源名称，返回数据源
 *
 * @author ash
 */
public class SimpleDataSourceGenerator extends AbstractDataSourceGenerator {

    private final String database;

    public SimpleDataSourceGenerator(DataSourceFactory dataSourceFactory, DataSourceType dataSourceType, String database) {
        super(dataSourceFactory, dataSourceType);
        this.database = database;
    }

    @Nullable
    @Override
    public String getDatabase(InvocationContext context) {
        return database;
    }

}
