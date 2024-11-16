package com.workflow.helper;

public class ColorGenerator {

    private static final String[] COLOR={"red", "green", "blue", "yellow","violet", "orange"};

    public static String generateColor(){
        return COLOR[(int) (Math.random() * COLOR.length)];
    }

}
