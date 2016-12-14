package de.bvb.factory;

import java.util.Properties;

public class DaoFactory {
    private static DaoFactory instance = new DaoFactory();

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T getImpl(String key, Class<T> clazz) {
        try {
            Properties properties = new Properties();
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("dao.properties"));
            return (T) Class.forName(properties.getProperty(key)).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
