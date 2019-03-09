package ru.stqa.pft.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {
    public static void main(String[] args){
        String[] lands = {"Java", "C#","Python", "PHP"};

        List<String> languages = Arrays.asList("Java", "C#","Python", "PHP");
//        languages.add("Java");
//        languages.add("C#");

        for (String l : languages){
            System.out.println(l);
        }
    }
}
