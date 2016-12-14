package de.bvb.utils;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {
    private static ComboPooledDataSource ds = null;
    static {
        try {
            ds = new ComboPooledDataSource(); //加载xml文件中默认的配置,也就是<default-config>下面的配置
            //  ds = new ComboPooledDataSource("mysql"); //加载xml文件中的 <named-config name="mysql">下面的配置
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }
}
