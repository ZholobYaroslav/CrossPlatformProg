package com.company;
import java.math.BigInteger;
public class BigFraction
{
    private BigInteger numerator = BigInteger.valueOf(0);
    private BigInteger denominator = BigInteger.valueOf(1);

    public BigFraction(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    public BigFraction() {
    }

    public void add(BigFraction other)
    {
        this.numerator = this.numerator.multiply(other.denominator).add(this.denominator.multiply(other.numerator));
        this.denominator = this.denominator.multiply(other.denominator);
        this.simplify();
    }

    public BigInteger gcd(BigInteger first, BigInteger second)
    {
        while(!first.equals(second))
        {
            if(first.compareTo(second) > 0)
            {
                first = first.subtract(second);
            }
            else
            {
                second = second.subtract(first);
            }

        }
        return first;
    }
    public void simplify()
    {
        BigInteger gcd = this.gcd(this.numerator, this.denominator);
        if(!gcd.equals(BigInteger.valueOf(1)))
        {
            this.numerator =  numerator.divide(gcd);
            this.denominator = denominator.divide(gcd);
        }
    }

    @Override
    public String toString() {
        return this.numerator + "/" + this.denominator;
    }
}
