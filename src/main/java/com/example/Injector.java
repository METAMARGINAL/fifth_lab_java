package org.example;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;
/**
 * Метод, вызывающий бизнес-логику внедренных объектов.
 */
public class Injector {

    private Properties properties;

    /**
     * Конструктор инициализирует объект Properties и загружает настройки
     * из файла config.properties, расположенного в ресурсах.
     */
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
    /**
     * Метод внедрения зависимостей. Исследует поля объекта на наличие
     * аннотации @AutoInjectable и внедряет подходящую реализацию.
     *
     * @param obj объект любого класса, в который нужно внедрить зависимости
     * @param <T> тип объекта
     * @return тот же объект с проинициализированными полями
     */
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