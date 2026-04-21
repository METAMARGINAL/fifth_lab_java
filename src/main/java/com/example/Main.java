package org.example;
/**
 * Главный класс приложения для демонстрации работы инжектора.
 */
public class Main {
    public static void main(String[] args) {
        SomeBean sb = (new Injector()).inject(new SomeBean());

        sb.foo();
        System.out.println();
    }
}