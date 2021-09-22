package com.company;
import java.math.BigInteger;
public class Series
{
    public static final int VALUE = 15;// static final == const
    private Object result;
    private Object n;
    public Series(Object n) {
        this.n = n;
        result = null;//new Fraction();
    }

    public Object getFractionRes(){
        return this.result;
    }

    private void calculateFraction()
    {
        this.result = new Fraction();
        for(int i = 1; i <= (int)n; i++)
        {
            ((Fraction)result).add(new Fraction(1, i));
        }
    }
    private void calculateBig()
    {
        this.result = new BigFraction();
        for(int i = 1; i <= (int)n; i++)
        {
            ((BigFraction)result).add(new BigFraction(BigInteger.valueOf(1), BigInteger.valueOf(i)));
        }
    }

    public void calculate()
    {
        if((int)n <= VALUE)
        {
            this.calculateFraction();
        }
        else
        {
            this.calculateBig();
        }
    }
}
