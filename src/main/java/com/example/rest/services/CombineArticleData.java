package com.example.rest.services;

import java.util.function.BiFunction;

public class CombineArticleData implements BiFunction<String, String, String> {
    @Override
    public String apply(String a, String b) {
        return a + "***\n" + b;
    }
}
