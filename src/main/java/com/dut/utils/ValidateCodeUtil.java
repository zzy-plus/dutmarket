package com.dut.utils;

import java.util.Random;

public class ValidateCodeUtil {
    public static String generateCode(){
        Random random = new Random();
        int num = random.nextInt(8999) + 1000;
        return Integer.toString(num);
    }
}
