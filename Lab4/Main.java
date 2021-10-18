package com.company;

import javax.lang.model.type.NullType;

public class Main {

    public static void main(String[] args)
    {
        Manager manager = new Manager();
        String fileName1 = "A:\\University\\3year\\Cross-platform\\IdeaProjects\\Labs\\TaskString\\src\\com\\company\\Dictionary.txt";
        String fileName2 = "A:\\University\\3year\\Cross-platform\\IdeaProjects\\Labs\\TaskString\\src\\com\\company\\Ukrainian.txt";
        // English
        System.out.println("English version");
        manager.readFromFile(fileName1);
        manager.show();
        manager.firstTask();
        manager.secondTask("leet", fileName1);
        // Ukrainian
        System.out.println("\n\nUkrainian version");
        manager.readFromFile(fileName2);
        manager.show();
        manager.firstTask();
        manager.secondTask("слива", fileName2);

    }

}
