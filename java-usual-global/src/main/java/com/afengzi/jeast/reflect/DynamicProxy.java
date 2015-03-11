package com.afengzi.jeast.reflect;

import com.afengzi.jeast.reflect.impl.Dog;

import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 14-10-27
 * Time: обнГ3:02
 * To change this template use File | Settings | File Templates.
 */
public class DynamicProxy {
    public static void main(String[] args){
        Animal dog = new Dog();
        Animal animal = (Animal) Proxy.newProxyInstance(
                Animal.class.getClassLoader(),
                new Class[]{Animal.class},
                new DynamicProxyHandler(dog));
        animal.run();
    }
}
