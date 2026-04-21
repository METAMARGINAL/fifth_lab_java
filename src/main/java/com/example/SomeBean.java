package org.example;

/**
 * Класс, демонстрирующий работу инжектора.
 * Поля инициализируются не вручную, а с помощью механизма рефлексии.
 */
public class SomeBean {

    @AutoInjectable
    private SomeInterface field1;

    @AutoInjectable
    private SomeOtherInterface field2;
    /**
     * Метод, вызывающий логику внедренных объектов.
     */
    public void foo() {
        field1.doSomething();
        field2.doSomeOther();
    }
}