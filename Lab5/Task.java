package com.company;
import javax.annotation.processing.SupportedSourceVersion;
import java.awt.*;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task
{
    final String path = ".\\src\\com\\company\\data.txt";
    //Видалити з тексту всі слова : a, the, or, are, on, in, out та вивести
    String text = "";
    String regex = "\\s(a|the|or|are|on|in|out)\\s"; //"\\sa\\s|\\sthe\\s|\\sor\\s|\\sare\\s|\\sin\\s|\\son\\s|\\sout\\s";

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getRegex() {
        return regex;
    }
    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Task(String text, String regex)
    {
        this.text = text;
        this.regex = regex;
    }
    public Task(String regex)
    {
        this.regex = regex;
    }
    public Task()
    {
    }

    public void menu()
    {
        Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.println("Menu: input: 1 - from the console, 2 - read from file, 3 - execute reggyx task, 4 - run test. ");
            if (scanner.hasNextInt())
            {
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.println("Go ahead with text:");
                        this.read("in");
                        this.show(text);
                        break;
                    case 2:
                        System.out.println("Read from file");
                        this.read(path);
                        this.show(text);
                        break;
                    case 3:
                        this.reggyxTask();
                        break;
                    case 4:
                        this.runTests();
                        break;
                    default:
                        System.out.println("Option is out of range [1-4]!");
                        break;
                }
            } else {
                System.out.println("Wrong input.");
            }
            System.out.println();
        } while(!scanner.nextLine().equals("exit"));
        scanner.close();
    }

    public void read(String path)
    {
        try
        {
            Scanner scanner = null;
            this.text = "";
            if(path.equals("in"))
            {
                scanner = new Scanner(System.in);
            }
            else
            {
                scanner = new Scanner(new File(path));
            }
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if(line.equals("exit"))
                    break;
                this.text += line;
            }
        }
        catch(Exception exception)
        {
            System.out.println("Exception in \"readFromFile\": " + exception);
        }
    }
    public void show(String line)
    {
        System.out.println("Text :");
        System.out.println(line);
    }

    /*
    Замінити всі останні букви слів тексту на великі і вивести результуючий текст.
     */
    public String reggyxTask()
    {
        String localRegex = "[^\\s+]\\W+?";
        Pattern pattern = Pattern.compile(localRegex);
        Matcher matcher = pattern.matcher(text);
        if(text.equals(""))
        {
            return "Empty text!";
        }
        if(matcher.matches())
        {
            return "Text does not contain letters!";
        }
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(text);
        while (matcher.find())
        {
            String group = matcher.group();
            System.out.println(group);
        }
        String text2 = matcher.replaceAll(" ");
        System.out.println(text2);
        return text2;
    }
    public void runTests()
    {
        Tests tests = new Tests();
        tests.testOrdinary();
        tests.testTextIsEmpty();
        tests.testTextIsNoWord();
    }

}
