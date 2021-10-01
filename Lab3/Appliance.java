package com.company;
import java.util.Objects;

public class Appliance implements Comparable<Appliance>
{
    private String name = "none";
    private String model = "none";
    private String producer = "none";
    private double cost = 0.0;
    private double maxPower = 0.0;

    public String getName() {
        return name;
    }
    public String getModel() {
        return model;
    }
    public String getProducer() {
        return producer;
    }
    public double getCost() {
        return cost;
    }
    public double getMaxPower() {
        return maxPower;
    }

    public Appliance(String name, String model, String producer, double cost, double maxPower) {
        this.name = name;
        this.model = model;
        this.producer = producer;
        this.cost = cost;
        this.maxPower = maxPower;
    }
    public Appliance()
    {
    }

    @Override
    public String toString() {
        return name + " " +  model + " " + producer + " " + cost + " " +  maxPower;
    }

    public void parse(String line)
    {
        this.name = line.split(" ")[0];
        this.model = line.split(" ")[1];
        this.producer = line.split(" ")[2];
        this.cost = Double.parseDouble(line.split(" ")[3]);
        this.maxPower = Double.parseDouble(line.split(" ")[4]);
    }

    @Override
    public int compareTo(Appliance o) {
        return /*this.cost > o.cost ? 1 : this.cost < o.cost ? -1 : 0;*/
                o.cost > this.cost ? 1 : o.cost < this.cost ? -1 : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appliance appliance = (Appliance) o;
        return Double.compare(appliance.cost, cost) == 0 && Double.compare(appliance.maxPower, maxPower) == 0 && Objects.equals(name, appliance.name) && Objects.equals(model, appliance.model) && Objects.equals(producer, appliance.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, model, producer, cost, maxPower);
    }
}
