package com.company;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import static java.time.temporal.ChronoUnit.SECONDS;

public class Order implements Serializable
{
    public enum Status
    {
        NOT_READY,
        IN_PROGRESS,
        READY
    }
    private int orderNumber;
    private LocalDateTime deliveryTime;
    private LocalDateTime orderCreationTime;
    private int generalCreationTime;
    private Status status;
    private List<Pizza> pizzas;
    private double generalPrice;
    private int VisitorID;

    public Order()
    {
        this.orderNumber = -1;
        this.deliveryTime = LocalDateTime.now();
        this.orderCreationTime = LocalDateTime.now();
        this.generalCreationTime = -1;
        this.status = Status.NOT_READY;
        this.pizzas = new ArrayList<>();
        this.generalPrice = -1;
        this.VisitorID = -1;
    }

    public int getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }
    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public LocalDateTime getOrderCreationTime() {
        return orderCreationTime;
    }
    public void setOrderCreationTime(LocalDateTime orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
    }

    public int getGeneralCreationTime() {
        return generalCreationTime;
    }
    public void setGeneralCreationTime(int generalCreationTime) {
        this.generalCreationTime = generalCreationTime;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }
    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public double getGeneralPrice() {
        return generalPrice;
    }
    public void setGeneralPrice(double generalPrice) {
        this.generalPrice = generalPrice;
    }

    public int getVisitorID() {
        return VisitorID;
    }
    public void setVisitorID(int visitorID) {
        VisitorID = visitorID;
    }

    public long getOrderDelayTime()// час перетермінування =  now - deliveryTime
    {
        return  ChronoUnit.HOURS.between(this.deliveryTime, LocalDateTime.now());
    }

    @Override
    public String toString() {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", deliveryTime=" + deliveryTime.format(myFormatObj) +
                ", orderCreationTime=" + orderCreationTime.format(myFormatObj) +
                ", generalCreationTime=" + generalCreationTime +
                ", status=" + status +
                ", pizzas=" + pizzas.toString() +
                ", generalPrice=" + generalPrice +
                ", VisitorID=" + VisitorID +
                '}';
    }

}
