package com.company;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
// знатиме номер, адресу доставки, також список замовлень та бажаний час доставки для кожного замовлення.
public class Visitor implements Serializable
{
    private int visitorID;
    private String name_Surname;
    private String deliveryAddress;
    private List<Order> orders;
    public Visitor()
    {
        this.visitorID = -1;
        this.name_Surname = "none";
        this.deliveryAddress = "none";
        this.orders = new ArrayList<>();
    }

    public List<Order> getOrders()
    {
        return orders;
    }
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public String getName_Surname() {
        return name_Surname;
    }

    public boolean hasMoreThanTwoPizza()//for 2.nd Task
    {
        int result = 0;
        for (Order order: orders)
        {
            result += order.getPizzas().size();
        }
        return result > 2 ? true : false;
    }
    public boolean hasSuchPizza(String pizzaString)// for 3.rd Task
    {
        for (Order order: orders)
        {
            for (Pizza pizza: order.getPizzas())
            {
                if(pizza.getName().equals(pizzaString))
                    return true;
            }
        }
        return false;
    }
    public int howManySuchPizza(String pizzaString)// for 4.rd Task
    {
        int count = 0;
        for (Order order: orders)
        {
            for (Pizza pizza: order.getPizzas())
            {
                if(pizza.getName().equals(pizzaString))
                    count++;
            }
        }
        return count;
    }
    public Visitor hasThisVisitorSuchPizza(String pizzaName)// for 5.th Task
    {
        for (Order order: this.orders)
        {
            for (Pizza pizza: order.getPizzas())
            {
                if(pizza.getName().equals(pizzaName))
                    return this;
            }
        }
        return null;
    }

    private static Visitor parseVisitor(String line)
    {
        Visitor visitor = new Visitor();
        String[] mas = line.split("=");
        visitor.visitorID = Integer.parseInt(mas[0]);
        visitor.name_Surname = mas[1];
        visitor.deliveryAddress = mas[2];
        // 1
        String[] mas2 = mas[3].split("@"); // ]
        List<Order> visitorOrders = new ArrayList<>(mas2.length);
        for (String str: mas2) {
            Order order = new Order();
            String[] mas3 = str.split(";");
            order.setOrderNumber(Integer.parseInt(mas3[0].replace("[", "")));
            LocalDateTime deliveryTime = LocalDateTime.parse(mas3[1], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            LocalDateTime orderCreationTime = LocalDateTime.parse(mas3[2], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            order.setDeliveryTime(deliveryTime);
            order.setOrderCreationTime(orderCreationTime);

            order.setGeneralCreationTime(Integer.parseInt(mas3[3]));
            order.setStatus(Order.Status.values()[Integer.parseInt(mas3[4])]);
            order.setVisitorID(Integer.parseInt(mas3[5]));
            order.setGeneralPrice(Double.parseDouble(mas3[6]));
            // second row done
            String[] mas4 = mas3[7].split("#");
            List<Pizza> pizzaList = new ArrayList<>(mas4.length);
            for (String pizzaStr : mas4) {
                String[] pizzaParts = pizzaStr.split(",");
                String pizzaName = pizzaParts[0].replace("{", "").trim();
                double pizzaWeight = Double.parseDouble(pizzaParts[1]);
                double pizzaPrice = Double.parseDouble(pizzaParts[2]);
                String[] pizzaIngredients = pizzaParts[3].split(" ");
                List<String> pizzaIngredientsList = Arrays.stream(pizzaIngredients).toList();
                int pizzaCreationTime = Integer.parseInt(pizzaParts[4].replace("}", "").replace("]", ""));
                Pizza pizza = new Pizza(pizzaName, pizzaWeight, pizzaPrice, pizzaIngredientsList, pizzaCreationTime);
                pizzaList.add(pizza);
            }
            order.setPizzas(pizzaList);
            visitorOrders.add(order);
        }
        visitor.orders = visitorOrders;
        return visitor;
    }
    public Set<Visitor> parse(String fulltext)
    {
        String[] masVisitors = fulltext.split("!");
        Set<Visitor> visitorSet = new LinkedHashSet<>();
        for (String visitorStr: masVisitors)
        {
            visitorSet.add(Visitor.parseVisitor(visitorStr));
        }
        return visitorSet;
    }
    @Override
    public String toString() {
        return "Visitor{" +
                "visitorID=" + visitorID +
                ", name_Surname='" + name_Surname + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", orders=" + orders.toString() +
                '}';
    }
    /*
            enum Status { NOT_READY, IN_PROGRESS, READY }
            private int orderNumber;
            private LocalDateTime deliveryTime;
            private LocalDateTime orderCreationTime;
            private int generalCreationTime;
            private Order.Status status;
            private int VisitorID;
            private double generalPrice;
            private List<Pizza> pizzas;
                    private String name;
                    private double weight;
                    private double price;
                    private List<String> ingredients;
                    private int creationTime;*/
    /*public void serializeVisitor()
    {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.dat")))
        {
            //Person p = new Person("Sam", 33, 178, true);
            oos.writeObject(p);
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }*/
}
