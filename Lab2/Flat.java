package com.company;

import java.util.Comparator;
import java.util.Scanner;

public class Flat implements Comparable<Flat>
{
    private double area;
    private int roomCount;
    private String residentalArea;
    private double pricePeSquareMeter;
    private double infrastructureDistance;

    public Flat(double area, int roomCount, String residentalArea, double pricePeSquareMeter, double infrastructureDistance ) {
        this.area = area;
        this.roomCount = roomCount;
        this.residentalArea = residentalArea;
        this.pricePeSquareMeter = pricePeSquareMeter;
        this.infrastructureDistance = infrastructureDistance;
    }
    public Flat() {
        this.area = 0.0;
        this.roomCount = 0;
        this.residentalArea = "none";
        this.pricePeSquareMeter = 0.0;
        this.infrastructureDistance = 0.0;
    }

    public double getArea() {
        return area;
    }
    public void setArea(double area) {
        this.area = area;
    }

    public int getRoomCount() {
        return roomCount;
    }
    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public String getResidentalArea() {
        return residentalArea;
    }
    public void setResidentalArea(String residentalArea) {
        this.residentalArea = residentalArea;
    }

    public double getPricePeSquareMeter() {
        return pricePeSquareMeter;
    }
    public void setPricePeSquareMeter(double pricePeSquareMeter) {
        this.pricePeSquareMeter = pricePeSquareMeter;
    }

    public double getInfrastructureDistance() {
        return infrastructureDistance;
    }
    public void setInfrastructureDistance(double infrastructureDistance) {
        this.infrastructureDistance = infrastructureDistance;
    }

    @Override
    public String toString() {
        return this.getClass().getName().split("\\.")[2] + "{" +
                "area=" + area +
                ", roomCount=" + roomCount +
                ", residentalArea='" + residentalArea + '\'' +
                ", pricePeSquareMeter=" + pricePeSquareMeter +
                ", infrastructureDistance=" + infrastructureDistance +
                '}';
    }

    public void parse(String line)
    {
        this.area = Double.parseDouble(line.split(" ")[1]);
        this.roomCount = Integer.parseInt(line.split(" ")[2]);
        this.residentalArea = line.split(" ")[3];
        this.pricePeSquareMeter = Double.parseDouble(line.split(" ")[4]);
        this.infrastructureDistance = Double.parseDouble(line.split(" ")[5]);

    }
    @Override
    public int compareTo(Flat flat)
    {
        return this.getPricePeSquareMeter() > flat.getPricePeSquareMeter() ? 1 :
                this.getPricePeSquareMeter() < flat.getPricePeSquareMeter() ? -1 : 0;
    }
}
