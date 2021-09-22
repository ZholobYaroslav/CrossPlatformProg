package com.company;

public class Fraction
{
    private int numerator = 0;
    private int denominator = 1;
    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    public Fraction() {}
    public void add(Fraction other)
    {
        this.numerator = this.numerator * other.denominator + this.denominator * other.numerator;
        this.denominator = this.denominator * other.denominator;
        this.simplify();
    }
    public int gcd(int first, int second)
    {
        while(first != second)
        {
            if(first > second)
            {
                first -= second;
            }
            else
            {
                second -=first;
            }
        }
        return first;
    }
    public void simplify()
    {
        int gcd = this.gcd(this.numerator, this.denominator);
        if(gcd != 1)
        {
            this.numerator /= gcd;
            this.denominator /= gcd;
        }
    }
    @Override
    public String toString() {
        return this.numerator + "/" + this.denominator;
    }
}
