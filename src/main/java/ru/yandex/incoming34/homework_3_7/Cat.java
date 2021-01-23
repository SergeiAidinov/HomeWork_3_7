/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yandex.incoming34.homework_3_7;

import java.awt.Color;
import java.awt.color.ColorSpace;

/**
 *
 * @author sergei
 */
public class Cat {

    public Cat() {
        name = "Васька";
        color = "серый";
    }

   

    public Cat(String name, String Color) {
        this.name = name;
        this.color = Color;
    }
    String name;
    int dateOfBirth;
    int life;
    String color;

   @BeforeSuit
    public void born() {
        dateOfBirth = (int) (Math.random() * 10 + 2005);
        life = (int) (Math.random() * 10 + 2);
        System.out.println("В " + dateOfBirth + " году родился " + color + " кот " + name + ".");
       
    }
    @Test (priority = 1)
    private void meow(){
        System.out.println("Кот мяукнул.");
    }
    
    @Test (priority = 10)
    private void scratches(){
        System.out.println("Кот царапает обивку на двери.");
    }
    @Test (priority = 4)
    private void breakVase(){
        System.out.println("Кот разбил вазу!");
    }
    
    @Test (priority = 3)
    private void catchesMouse(){
        System.out.println("Кот поймал мышь!");
    }
    
    @AfterSuit
    public void died(){
        System.out.println("В " + (dateOfBirth + life) + " году кот " + name + " завершил свой земной путь...");
    }

}
