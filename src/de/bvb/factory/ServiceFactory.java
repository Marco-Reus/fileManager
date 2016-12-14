package de.bvb.factory;

import java.util.Properties;

public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T getImpl(String key, Class<T> clazz) {
        try {
            Properties properties = new Properties();
            properties.load(ServiceFactory.class.getClassLoader().getResourceAsStream("service.properties"));
            return (T) Class.forName(properties.getProperty(key)).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
