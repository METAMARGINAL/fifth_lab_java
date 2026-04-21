package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования работы Injector.
 */
class InjectorTest {

    private Injector injector;

    @BeforeEach
    void setUp() {
        injector = new Injector();
    }

    @Test
    void testInjectInitializesFields() throws Exception {
        SomeBean bean = new SomeBean();

        // Проверяем, что до внедрения поля равны null
        Field field1 = SomeBean.class.getDeclaredField("field1");
        field1.setAccessible(true);
        assertNull(field1.get(bean), "До вызова inject() поле должно быть null");

        // Вызываем внедрение зависимостей
        injector.inject(bean);

        // Проверяем, что поля проинициализированы
        assertNotNull(field1.get(bean), "После вызова inject() поле не должно быть null");

        // Проверяем, что внедрен правильный класс (согласно config.properties)
        assertTrue(field1.get(bean) instanceof SomeImpl, "Должен быть внедрен экземпляр SomeImpl");
    }
}