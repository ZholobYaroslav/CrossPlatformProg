package com.company;

import org.junit.Test;

import java.util.Locale;

import static junit.framework.TestCase.assertEquals;

public class Tests
{
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    @Test
    public void testOrdinary()
    {
        //Arrange
        String text = "this tHe great on that out whole In ds." ;
        String regex = "\\sa\\s|\\sthe\\s|\\sor\\s|\\sare\\s|\\sin\\s|\\son\\s|\\sout\\s";
        Task task = new Task(text, regex);
        //Act
        String calculatedValue = task.reggyxTask();
        String expectedValue = "this great that whole ds.";
        //Assert
        System.out.println("\n"+ "Test status: " + ANSI_GREEN + calculatedValue.equals(expectedValue) + ANSI_RESET);
        System.out.println("result: "+ calculatedValue);
        System.out.println("expected: "+expectedValue);
        assertEquals(expectedValue, calculatedValue);
        //return calculatedValue.equals(expectedValue);
    }

    @Test
    public void testTextIsEmpty()
    {
        //Arrange
        String text = "" ;
        String regex = "\\sa\\s|\\sthe\\s|\\sor\\s|\\sare\\s|\\sin\\s|\\son\\s|\\sout\\s";
        Task task = new Task(text, regex);
        //Act
        String calculatedValue = task.reggyxTask();
        String expectedValue = "Empty text!";
        //Assert
        System.out.println("\n"+ "Test status: " + ANSI_GREEN + calculatedValue.equals(expectedValue) + ANSI_RESET);
        System.out.println("result: "+calculatedValue);
        System.out.println("expected: "+expectedValue);
        assertEquals(expectedValue, calculatedValue);
        //return calculatedValue.equals(expectedValue);
    }
    @Test
    public void testTextIsNoWord()
    {
        //Arrange
        String text = "*-/-//}[{;" ;
        String regex = "\\sa\\s|\\sthe\\s|\\sor\\s|\\sare\\s|\\sin\\s|\\son\\s|\\sout\\s";
        Task task = new Task(text, regex);
        //Act
        String calculatedValue = task.reggyxTask();
        String expectedValue = "Text does not contain letters!";
        //Assert
        System.out.println("\n"+ "Test status: " +  ANSI_GREEN + calculatedValue.equals(expectedValue) + ANSI_RESET);
        System.out.println("result: "+calculatedValue);
        System.out.println("expected: "+expectedValue);
        assertEquals(expectedValue, calculatedValue);
        //return calculatedValue.equals(expectedValue);
    }

}
