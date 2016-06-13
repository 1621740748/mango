///*
// * Copyright 2014 mango.jfaster.org
// *
// * The Mango Project licenses this file to you under the Apache License,
// * version 2.0 (the "License"); you may not use this file except in compliance
// * with the License. You may obtain a copy of the License at:
// *
// *   http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// * License for the specific language governing permissions and limitations
// * under the License.
// */
//
//package org.jfaster.mango.sharding;
//
//import org.jfaster.mango.annotation.DB;
//import org.jfaster.mango.annotation.SQL;
//import org.jfaster.mango.annotation.ShardingBy;
//import org.jfaster.mango.operator.Mango;
//import org.jfaster.mango.support.DataSourceConfig;
//import org.jfaster.mango.support.Table;
//import org.jfaster.mango.support.model4table.User;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//
///**
// * @author ash
// */
//public class GenericsDataSourceRouterExceptionTest {
//
//    private final static DataSource ds = DataSourceConfig.getDataSource();
//    private final static Mango mango = Mango.newInstance(ds);
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    @Before
//    public void before() throws Exception {
//        Connection conn = ds.getConnection();
//        Table.USER.load(conn);
//        conn.close();
//    }
//
//    @Test
//    public void testException() throws Exception {
//        thrown.expect(ClassCastException.class);
//        thrown.expectMessage("DataSourceRouter[class org.jfaster.mango.partition.GenericsDataSourceRouterExceptionTest$UserDataSourceRouter]'s generic type[class java.lang.String] must be assignable from the type of parameter Modified @DataSourceShardBy [long], please note that @ShardBy = @TableShardBy + @DataSourceShardBy");
//        UserDao dao = mango.create(UserDao.class, true);
//        System.out.println(dao.getUser(1));
//    }
//
//    @DB(table = "user", dataSourceRouter = UserDataSourceRouter.class)
//    static interface UserDao {
//
//        @SQL("select id, name, age, gender, money, update_time from #table where id = :1")
//        public User getUser(@ShardingBy long id);
//
//    }
//
//    static class UserDataSourceRouter implements DatabaseShardingStrategy<String> {
//        @Override
//        public String getDatabase(String uid, int type) {
//            return "xx";
//        }
//    }
//
//
//}
