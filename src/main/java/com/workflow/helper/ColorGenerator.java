package com.workflow.helper;

import java.util.List;
import java.util.Random;
public class ColorGenerator {

    private ColorGenerator(){
        // Prevent instantiation of this class
        throw new IllegalStateException("Utility class");
    }

    private static final List<String> COLORS=List.of("red", "green", "blue", "yellow","violet", "orange");

    public static String generateColor(){
        return COLORS.get(new Random().nextInt(COLORS.size()));
    }

}
