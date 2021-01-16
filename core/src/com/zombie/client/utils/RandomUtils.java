package com.zombie.client.utils;

import java.util.Random;

public class RandomUtils{
    public static float RandFloat(float min, float max) {
        Random rand = new Random();
        float result = rand.nextFloat() * (max - min) + min;
        return result;
    }

    public static int RandInt(int min, int max) {
        Random rand = new Random();
        int result = rand.nextInt(max - min + 1) + min;
        return result;
    }

}