package com.company;

import java.math.BigInteger;

public class Series
{
    private Fraction result;
    private Object n;

    public Series(Object n) {
        this.n = n;
        result = new Fraction();
    }
    public Fraction getResult() {
        return result;
    }
    public Fraction getFractionRes(){
        return this.result;
    }
    public void calculate()
    {
        for(int i = 1; i <= (int)n; i++)
        {
            this.result.add(new Fraction(1, i));
        }
    }
    public void calculateBig()
    {
        for(int i = 1; i <= ((BigInteger)n).intValue(); i++)
        {
            this.result.add(new Fraction(1, i));
        }
    }

}
