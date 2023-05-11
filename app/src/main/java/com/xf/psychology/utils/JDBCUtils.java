package com.xf.psychology.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * function： 数据库工具类，连接数据库用
 */
public class JDBCUtils {
    private static final String TAG = "mysql-party-JDBCUtils";

    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动

    private static String dbName = "test";// 数据库名称

    private static String user = "root";// 用户名

    private static String password = "root";// 密码

    public static Connection getConn(){
        Connection connection = null;
        try{
            Class.forName(driver);// 动态加载类
            String ip = "10.0.2.2";// 写成本机地址，不能写成localhost，同时手机和电脑连接的网络必须是同一个

            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName,
                    user, password);

        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(ResultSet resultSet, PreparedStatement statement, Connection connection) {
    }
}
