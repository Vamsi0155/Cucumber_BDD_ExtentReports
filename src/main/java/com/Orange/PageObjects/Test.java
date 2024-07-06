package com.Orange.PageObjects;

import org.apache.commons.lang3.RandomStringUtils;

public class Test {

    public static void main(String[] args) {

        String random = RandomStringUtils.randomAlphabetic(6, 8) + "001";
        System.out.println(random);
    }
}
