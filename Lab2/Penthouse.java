package com.company;

public class Penthouse extends Flat
{
    private double terraceArea;
    private boolean hasPool;

    public Penthouse(double area, int roomCount, String residentalArea, double pricePeSquareMeter, double infrastructureDistance, double terraceArea, boolean hasPool) {
        super(area, roomCount, residentalArea, pricePeSquareMeter, infrastructureDistance);
        this.terraceArea = terraceArea;
        this.hasPool = hasPool;
    }
    public Penthouse() {
        super();
        this.terraceArea = 0.0;
        this.hasPool = false;
    }

    public double getTerraceArea() {
        return terraceArea;
    }
    public void setTerraceArea(double terraceArea) {
        this.terraceArea = terraceArea;
    }

    public boolean isHasPool() {
        return hasPool;
    }
    public void setHasPool(boolean hasPool) {
        this.hasPool = hasPool;
    }

    @Override
    public String toString() {
        return super.toString() +  ", terraceArea='" + terraceArea + '\'' +
                ", hasPool=" + hasPool;
    }

    @Override
    public void parse(String line) {
        super.parse(line);
        this.terraceArea = Double.parseDouble(line.split(" ")[6]);
        this.hasPool = Boolean.parseBoolean(line.split(" ")[7]);
    }
}
