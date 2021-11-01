package com.company;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    /*
    Видалити з тексту всі слова, які є артиклями в англійській мові (a, the), or, are, on, in, out та вивести результуючий текст .
     */
    public static void main(String[] args)
    {
        Task task = new Task();
        Tests tests = new Tests();
        task.menu();
    }
}
