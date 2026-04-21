package org.example;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {

    private Properties properties;

    public Injector() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Файл config.properties не найден!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public <T> T inject(T obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                String interfaceName = field.getType().getName();
                String implementationClassName = properties.getProperty(interfaceName);

                if (implementationClassName != null) {
                    try {
                        Class<?> implClass = Class.forName(implementationClassName);
                        Object implInstance = implClass.getDeclaredConstructor().newInstance();

                        field.setAccessible(true);
                        field.set(obj, implInstance);
                    } catch (Exception e) {
                        System.err.println("Ошибка при создании экземпляра класса: " + implementationClassName);
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }
}