package org.example;
/**
 * Главный класс приложения для демонстрации работы инжектора.
 *
 *  * @see <a href="https://github.com/METAMARGINAL/fifth_lab_java"_blank">Репозиторий проекта на GitHub</a>
 *
 */
public class Main {
    public static void main(String[] args) {
        SomeBean sb = (new Injector()).inject(new SomeBean());

        sb.foo();
        System.out.println();
    }
}