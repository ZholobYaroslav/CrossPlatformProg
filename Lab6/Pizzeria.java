package com.company;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

// містить колекцію всіх піц та колекцію всіх користувачів, а також методи, які з цими колекціями можна здійснювати.
public class Pizzeria
{
    private static final String pathname1 = "A:\\University\\3year\\Cross-platform\\IdeaProjects\\Labs\\Lab06\\src\\com\\company\\visitorSet2.txt";
    private Set<Pizza> pizzaSet;
    private Set<Visitor> visitorSet;
    private List<Order> orders;
    private Map<Visitor, List<Order>> visitorOrders;
    public Pizzeria() {
        this.pizzaSet = new LinkedHashSet<>();
        this.visitorSet = new LinkedHashSet<>();
        this.orders = new ArrayList<>();
        visitorOrders = new LinkedHashMap<>();
    }
    public Set<Visitor> getVisitorSet() {
        return visitorSet;
    }

    public void callMenu()
    {
        String falseInput = "";
        Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.println("Choose the option:\n" +
                    "-1 - read from text file(no serialization)\n" +
                    "0 - serialize collection\n" +
                    "1 - execute First Task\n" +
                    "2 - execute Second Task\n" +
                    "3 - execute Third Task\n" +
                    "4 - execute Fourth Task\n" +
                    "5 - execute Fifth Task\n" +
                    "6 - execute Sixth Task");
            if (scanner.hasNextInt())
            {
                this.readFromFile();
                switch (scanner.nextInt()) {
                    case -1:
                        this.showVisitorSet(this.getVisitorSet());
                        break;
                    case 0:
                        this.serializeVisitorSet();
                        break;
                    case 1:
                        this.firstTask();
                        break;
                    case 2:
                        this.secondTask();
                        break;
                    case 3:
                        this.thirdTask();
                        break;
                    case 4:
                        this.fourthTask();
                        break;
                    case 5:
                        this.fifthTask();
                        break;
                    case 6:
                        this.sixthTask();
                        break;
                    default:
                        System.out.println("Option is out of range [1-8]!");
                        break;
                }
            }
            else
            {
                falseInput  = scanner.next();
                if(falseInput.equals("exit"))
                {
                    System.out.println("Have a nice day:)");
                }
                else
                    System.out.println("Wrong input.");
            }
            System.out.println();
        } while(!falseInput.equals("exit"));
        scanner.close();
    }
    public void readFromFile()
    {
        try {
            visitorSet.clear();
            Scanner scanner = new Scanner(new File(pathname1));
            String fulltext = "";
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if(!line.isEmpty() && !line.equals(""))
                {
                    fulltext+=line;
                }
            }
            visitorSet = new Visitor().parse(fulltext);
        }
        catch(Exception exception)
        {
            System.out.println("Exception in \"readFromFile\": " + exception);
        }
    }
    public void showVisitorSet(Set<Visitor> visitorSet)
    {
        for (Visitor visitor: visitorSet)
        {
            System.out.println(visitor.toString());
        }
    }
    public void serializeVisitorSet()
    {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("serVisitorSet.bin")))
        {
            oos.writeObject(visitorSet);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        // десериализация в новый список
        Set<Visitor> visitorSet2 = new LinkedHashSet<>(visitorSet.size());
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serVisitorSet.bin")))
        {
            visitorSet2 = ((LinkedHashSet<Visitor>)ois.readObject());
        }
        catch(Exception ex)
        {
            System.out.println("in serializeVisitorSet deserialization exception");
            System.out.println(ex.getMessage());
        }
        System.out.println();
        System.out.println("Deserialized visitors set:");
        this.showVisitorSet(visitorSet2);
    }
    // Tasks
    public void getOrdersList()
    {
        orders = new ArrayList<>(visitorSet.size());
        for (Visitor visitor: visitorSet)
        {
            for (Order order: visitor.getOrders())
            {
                orders.add(order);
            }
        }
    }
    public void showOrders()
    {
        System.out.println("\nList<Order> orders:");
        for (Order order: orders)
        {
            System.out.println(order.toString());
        }
    }
    public void firstTask()
    {
        this.getOrdersList();
        //this.showOrders();
        Stream<Order> orderStream = this.orders.stream();
        List<Order> sortedOrders = orderStream.sorted(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getDeliveryTime().compareTo(o2.getDeliveryTime());
            }
        }).collect(Collectors.toList());
        System.out.println("\n#1 Task: Відсортувати всі замовлення в піцерії за часом доставки.");
        for (Order order:sortedOrders)
        {
            System.out.println(order);
        }
    }
    public void secondTask()
    {
        List<String> adresses = new ArrayList<>();
        Stream<Visitor> visitorStream = this.visitorSet.stream();
        adresses = visitorStream.filter(v -> v.hasMoreThanTwoPizza()).map(v -> v.getDeliveryAddress()).collect(Collectors.toList());
        /*var res = visitorStream.flatMap(v -> v.getOrders().stream())
                        .flatMap(o -> o.getPizzas().stream())
                        .collect(Collectors.toList());*/
        System.out.println("\n#2 Task: Створити список адрес для користувачів, що замовили  більше ніж 2 піци.");
        for (String adress: adresses)
        {
            System.out.println(adress);
        }
    }
    public void thirdTask()
    {
        System.out.println("\n#3 Task: Перевірити, скільки користувачів замовили піцу із заданою назвою(пошук через задану назву піци).");
        System.out.println("Введіть назву піци:");
        Scanner scanner = new Scanner(System.in);
        String pizzaString = scanner.nextLine();//Kapriciosa
        int count = 0;
        Stream<Visitor> visitorStream = this.visitorSet.stream();
        count = (int)visitorStream.filter(v -> v.hasSuchPizza(pizzaString)).count();
        System.out.println("Кількість користувачів, які замовили піцу \'"+pizzaString+"\' - "+count);
    }
    public Set<String> getPizzaName()
    {
        Set<String> pizzaSet = new LinkedHashSet<>();
        for (Visitor visitor: visitorSet)
        {
            for (Order order: visitor.getOrders())
            {
                for (Pizza pizza: order.getPizzas())
                {
                    if(!pizzaSet.contains(pizza.getName()))//if(!pizzaSet.contains(pizza))
                    {
                        pizzaSet.add(pizza.getName());
                    }
                }
            }
        }
        return pizzaSet;
    }
    public void getPizzaSet() //?
    {
        pizzaSet = new LinkedHashSet<>();
        for (Visitor visitor: visitorSet)
        {
            for (Order order: visitor.getOrders())
            {
                for (Pizza pizza: order.getPizzas())
                {
                    if(!pizzaSet.contains(pizza))
                    {
                        pizzaSet.add(pizza);
                    }
                }
            }
        }
    }
    public void fourthTask()
    {
        System.out.println("\n#4 Task: Знайти найбільшу кількість піц, замовлених користувачем серед запропонованого переліку піц.");
        System.out.println("Запропонований перелік піц:");
        for (String pizzaName: this.getPizzaName())
        {
            System.out.println(pizzaName);
        }
        /*this.getPizzaSet();
        for (Pizza pizza: pizzaSet)
        {
            System.out.println(pizza);
        }*/
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть назву піци, щоб відобразити фаворита цієї піци:");
        String pizzaString = scanner.nextLine();//Kapriciosa
        Stream<Visitor> visitorStream = this.visitorSet.stream();
        List<String> visitorPizza = visitorStream.map(v -> v.getName_Surname() + " - " + v.howManySuchPizza(pizzaString)).collect(Collectors.toList());
        for (String str: visitorPizza)
        {
            System.out.println(str);
        }
    }
    public void fourthTaskRemasteredButNotCompleted()
    {
        System.out.println("\n#4.1 Task: Знайти найбільш популярну/ні піцу/-и.");
        System.out.println("Запропонований перелік піц:");
        for (String pizzaName: this.getPizzaName())
        {
            System.out.println(pizzaName);
        }

        Stream<Visitor> visitorStream = this.visitorSet.stream();
        var list = visitorStream.flatMap(v -> v.getOrders().stream())
                .flatMap(o -> o.getPizzas().stream())
                  //.collect(Collectors.toList()).stream()
                    .collect(groupingBy(n -> n.getName(),
                    mapping(r -> r , Collectors.toList())));
        System.out.println(list);
    }
    public Map<String, List<Pizza>> getVisitorsPizzaListMap()
    {
        Map<String, List<Pizza>> res = new LinkedHashMap<>();
        for (Visitor visitor: visitorSet)
        {
            String visitorName = visitor.getName_Surname();
            List<Pizza> pizzas = new ArrayList<>();
            for (Order order: visitor.getOrders())
            {
                    pizzas.addAll(order.getPizzas());
            }
            res.put(visitorName, pizzas);
        }
        return res;
    }
    public Map<String, List<String>> getVisitorsPizzaStringListMap()
    {
        Map<String, List<String>> res = new LinkedHashMap<>();
        for (Visitor visitor: visitorSet)
        {
            String visitorName = visitor.getName_Surname();
            List<String> pizzas = new ArrayList<>();
            for (Order order: visitor.getOrders())
            {
                for (Pizza pizza:order.getPizzas())
                {
                    pizzas.add(pizza.getName());
                }
            }
            res.put(visitorName, pizzas);
        }
        return res;
    }
    public void fifthTask()
    {
        System.out.println("\n#5 Task: Створити колекцію з переліком піц та списками їх замовників.");
        Map<String, List<Visitor>> pizzaVisitorList = new LinkedHashMap<>();
        Set<String> pizzaSet = this.getPizzaName();
        for (String pizzaStr: pizzaSet)
        {
            List<Visitor> visitors = new ArrayList<>();
            for (Visitor visitor: visitorSet)
            {
                Visitor visitorTemp = visitor.hasThisVisitorSuchPizza(pizzaStr);
                if(visitorTemp != null)
                {
                    visitors.add(visitorTemp);
                }
            }
            pizzaVisitorList.put(pizzaStr, visitors);
        }
        pizzaVisitorList.entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()
                        .stream()
                        .map(v -> v.getName_Surname())
                        .collect(Collectors.joining( "," ) )));

        // Нижче проби і говнокод, хоча говнокод і зверху, але то таке:)
        /*Stream<Visitor> visitorStream = this.visitorSet.stream();
        var res = visitorStream.flatMap(v -> v.getOrders().stream())
                .flatMap(o -> o.getPizzas().stream())
                .collect(Collectors.toList());*/
        /*
        .flatMap(reader -> reader.getBooks().stream())
        .collect(groupingBy(r -> r.getBooks().size() > 2 ? "TOO_MUCH" : "OK",
            mapping(r -> new EmailAddress(r.getEmail()), Collectors.toList())
        var visitorPizza = this.getVisitorsPizzaListMap();
        var visitorPizzaStrings = this.getVisitorsPizzaStringListMap();*/
        /*visitorSet
                .stream()
                .collect(
                Collectors.groupingBy(Pizza::getPizza),
                        mapping(Visitor::getName_Surname, Collectors.toList()));*/
        /* pizzaVisitorList
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().toString() + ": "
                        + entry.getValue()//List<Visitor>
                        .stream()
                        .peek(v -> v.getName_Surname()))
                 .collect(Collectors.toList())
                 .forEach(e -> System.out.println(e));*/
        /*pizzaVisitorList
                 .entrySet()
                 .stream()
                 .map(e -> e.getKey() + " : " + e.getValue())
                 .collect(LinkedHashMap::new, (m,v) -> m.put(m,v), HashMap::putAll)
                .forEach((m,v) -> System.out.println(m.toString() +" | " + v.toString()));*/
        /*Set<Pizza> distinctPizza = this.pizzaSet.stream().distinct().collect(Collectors.toSet());// ?
        // var result = visitorSet.stream()
                .collect(ArrayList::new, List::addAll, List::addAll);*/
        /*var result = this.visitorSet
                .stream().map(v -> v.getOrders()
                        .stream().map(o -> o.getPizzas()
                        .stream().map(p -> p.getName())
                                .collect(Collectors.toList())
                                .stream().collect(Collectors.toList()))
                                .collect(Collectors.toList()));*/
        /*var result = this.visitorSet
                  .stream()
                  .map(v -> v.getOrders().stream().map(o -> o)
                 ).collect(Collectors.toList());
        System.out.println(result);*/
        /*List<String> fields = persons.stream()
               .flatMap(p -> Stream.of(p.name1, p.name2, p.address))
                .collect(Collectors.toList());*/
        /*var result1 = this.visitorSet.stream()
               .flatMap(v -> Stream.of(v.getOrders().stream().map(o -> o).collect(Collectors.joining()))).collect(Collectors.toList())

        var result2 = visitorSet.stream().collect(groupingBy(l -> l.getOrders().stream().
                map(o -> o.getPizzas().stream()).map(p->p).collect(Collectors.toList())));
        //побудувати коллектор, який об'єднає елементи
        */
    }
    public void sixthTask()
    {
        System.out.println("\n#6 Task: Створити список НЕвиконаних замовлень на біжучий час" +
                "вказати:" +
                "час перетермінування, відмітка виконано/невиконано , час створення замовлення.");
        List<Order> orders = new ArrayList<>();
        for (Visitor visitor: visitorSet)
        {
            for (Order order: visitor.getOrders())
            {
                if(!order.getStatus().equals(Order.Status.READY))
                {
                    orders.add(order);
                }
            }
        }
        orders.stream().forEach(o-> System.out.println(o + " \ndelayTime:"+o.getOrderDelayTime() + " hours\n"));

    }

}
