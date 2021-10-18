package com.company;

// & for every algo -> its own class. Too much classes -> I used anonymous classes, references, lambda
public class IsDividable implements IStrategy
{
    @Override
    public boolean execute(String word)
    {
        Manager manager = new Manager();
        return manager.isDividable(word);
    }
}
