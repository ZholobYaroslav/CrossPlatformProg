package com.company;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Enter n value:");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextBigInteger())
        {
            BigInteger bg = scanner.nextBigInteger();
            Series series = null;
            if (bg.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0)
            {
                series = new Series(bg.intValue());
            }
            else
            {
                series = new Series(bg);
            }
            series.calculate();
            System.out.println("result: " + series.getFractionRes());
        }
        else
        {
            System.out.println("Wrong input. \"n\" should be integer.");
        }
    }
}
