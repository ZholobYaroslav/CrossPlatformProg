package com.company;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        System.out.println("Enter n value:");
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextInt())
        {
            int n = scanner.nextInt();
            if(n <= 13)
            {
                Series series = new Series(n);
                series.calculate();
                System.out.println("Ordinary result: " +series.getFractionRes());
            }
            else
            {
                BigInteger bigN = BigInteger.valueOf(n);
                Series series = new Series(bigN);
                series.calculateBig();
                System.out.println("Big result: " + series.getFractionRes());
            }
        }
        else
        {
            System.out.println("Wrong input. \"n\" should be integer.");
        }
    }
}
