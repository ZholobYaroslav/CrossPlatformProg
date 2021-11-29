package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// назву, вагу, вартість та список складників
public class Pizza implements Serializable
{
    private String name;
    private double weight;
    private double price;
    private List<String> ingredients;
    private int creationTime;

    public Pizza(String name, double weight, double price, List<String> ingredients, int creationTime) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.ingredients = ingredients;
        this.creationTime = creationTime;
    }
    public Pizza()
    {
        this.name = "";
        this.weight = 0;
        this.price = 0;
        this.ingredients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", ingredients=" + ingredients +
                ", creationTime=" + creationTime +
                '}';
    }
    public Pizza getPizza()
    {
        return this;
    }
}
