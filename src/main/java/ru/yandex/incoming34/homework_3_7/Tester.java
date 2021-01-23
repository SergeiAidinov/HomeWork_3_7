/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yandex.incoming34.homework_3_7;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sergei
 */
public class Tester {

    static Method beforeMethod = null;
    static Method afterMethod = null;
    static Object cat;
    static Method[] methods;
    static ArrayList<Method> regularMethods;
    private static final int MAX_PRIORITY = 10;
    private static final int MIN_PRIORITY = 1;

    public static void main(String[] args) throws Exception {
        Class clazz = Cat.class;

        start(clazz);
    }

    public static void start(Class clazz) throws Exception {

        Constructor<Cat> constructor = Cat.class.getConstructor(String.class, String.class);
        cat = constructor.newInstance("Барсик", "рыжий");
        methods = clazz.getDeclaredMethods();
        regularMethods = new ArrayList<>();
        initialize(methods);
        if (!isNull(beforeMethod)) {
            beforeMethod.invoke(cat);
        } else {
            System.out.println("Тестируемый класс не содержит аннотации @BeforeSuit");
        }

        performMethods(regularMethods);
        if (!isNull(afterMethod)) {
            afterMethod.invoke(cat);
        } else {
            System.out.println("Тестируемый класс не содержит аннотации @AfterSuit");
        }

    }

    private static void initialize(Method[] methods) {

        for (int i = 0; i < methods.length; i++) {

            if (methods[i].isAnnotationPresent(BeforeSuit.class)) {
                if (isNull(beforeMethod)) {
                    beforeMethod = methods[i];
                    continue;
                } else {
                    try {
                        throw new RedunduntAnnotationException();
                    } catch (RedunduntAnnotationException ex) {
                        Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            if (methods[i].isAnnotationPresent(AfterSuit.class)) {
                if (afterMethod == null) {
                    afterMethod = methods[i];
                    continue;
                } else {
                    try {
                        throw new RedunduntAnnotationException();
                    } catch (RedunduntAnnotationException ex) {
                        Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (methods[i].isAnnotationPresent(Test.class)) {
                methods[i].setAccessible(true);
                regularMethods.add(methods[i]);
            }
        }

    }

    private static void performMethods(ArrayList<Method> regularMethods) {
        for (int currentPriority = MAX_PRIORITY; currentPriority > MIN_PRIORITY - 1; currentPriority--) {
            for (Method oneMethod : regularMethods) {
                try {
                    if (oneMethod.getAnnotation(Test.class).priority() == currentPriority) {
                        oneMethod.invoke(cat);
                    }
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                    Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
