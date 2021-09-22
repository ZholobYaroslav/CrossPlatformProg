package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        RealEstateAgencyManager manager = new RealEstateAgencyManager();
        manager.readFromFile();
        manager.callMenu();

    }
}
