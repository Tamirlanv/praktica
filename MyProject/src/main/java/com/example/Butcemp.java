package com.example;


import java.util.Random;

public class Butcemp {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(new Random().nextInt(10));
            if (i < 4){
                builder.append(" ");
            }
        }
        String finalString = builder.toString();
        System.out.println(finalString);

    }
}
