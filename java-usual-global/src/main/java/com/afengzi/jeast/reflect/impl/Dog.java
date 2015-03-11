package com.afengzi.jeast.reflect.impl;

import com.afengzi.jeast.reflect.Animal;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 14-10-27
 * Time: обнГ2:53
 * To change this template use File | Settings | File Templates.
 */
public class Dog implements Animal {
    @Override
    public void run() {
        System.out.println("the dog is run...");
    }
}
