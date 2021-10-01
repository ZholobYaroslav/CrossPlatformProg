package com.company;
import java.io.File;
import java.util.*;
import java.util.concurrent.CountedCompleter;
import java.util.stream.Collectors;

public class ApplianceManager
{
    private static final String pathname1 = "A:\\University\\3year\\Cross-platform\\IdeaProjects\\Labs\\Lab03\\src\\com\\company\\Appliances1.txt";
    private static final String pathname1Mixed = "A:\\University\\3year\\Cross-platform\\IdeaProjects\\Labs\\Lab03\\src\\com\\company\\AppliancesMixed.txt";
    private static final String pathname2 = "A:\\University\\3year\\Cross-platform\\IdeaProjects\\Labs\\Lab03\\src\\com\\company\\AppliancesSecond.txt";
    private List<Appliance> appliances;
    private Map<String, List<String >> producer_models_Map;

    public ApplianceManager() {
        this.appliances = new ArrayList<>();
        this.producer_models_Map = new LinkedHashMap<>();
    }

    public void callMenu()
    {
        String falseInput = "";
        Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.println("Choose the option: 1 - create Map & show first \'n\' records, 2 - find frequency characteristic of different names,\n" +
                    " 3 - read from 2 files. Sort by cost in descending order without permission to modify it. Find the number of devices, the total cost of all devices. ");
            if (scanner.hasNextInt())
            {
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.println("Enter Number of records to display: ");
                        if (scanner.hasNextInt())
                        {
                            this.readFromFile(pathname1Mixed);
                            this.showAppliances();
                            this.initializeMap();
                            this.showMapFirstN_iterator(scanner.nextInt());
                        }
                        else
                        {
                            System.out.println("Wrong N input number.");
                        }
                        break;
                    case 2:
                        this.frequencyOfDiffNames();
                        break;
                    case 3:
                        this.thirdTask(pathname1Mixed, pathname2);
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

    public void readFromFile(String pathname)
    {
        try {
            Scanner scanner = new Scanner(new File(pathname));
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                Appliance appliance = new Appliance();
                appliance.parse(line);
                appliances.add(appliance);
            }
        }
        catch(Exception exception)
        {
            System.out.println("Exception in \"readFromFile\": " + exception);
        }
    }
    public void showAppliances()
    {
        for(int i = 0; i < appliances.size(); i++)
        {
            System.out.println("#"+i + "| " + appliances.get(i));
        }
        System.out.println();
    }
    public void showAppliancesSpecific(List<Appliance> list)
    {
        for(int i = 0; i < list.size(); i++)
        {
            System.out.println("#"+i + "| " + appliances.get(i));
        }
        System.out.println();
    }


    public void initializeMap()
    {
        this.producer_models_Map = new LinkedHashMap<>(this.appliances.size());
        for(int i = 0; i < appliances.size(); i++)
        {
            String key = appliances.get(i).getProducer();
            if(!producer_models_Map.containsKey(key));
            {
                ArrayList<String> value = new ArrayList<>();
                for(int j = 0; j < appliances.size(); j++)
                {
                    if(appliances.get(j).getProducer().equals(key))
                    {
                        value.add(appliances.get(j).getModel());
                    }
                }
                this.producer_models_Map.put(key, value);
            }
        }
    }
    public void showMapAll()
    {
        for(Map.Entry<String, List<String>> m : producer_models_Map.entrySet())
        {
            System.out.println(m.getKey() + " : " + m.getValue());
        }
        System.out.println();
    }
    public void showMapFirstN_entrySet(int n)
    {
        try {
            for (Map.Entry<String, List<String>> m : producer_models_Map.entrySet()) {
                if (m.getValue().size() >= n) {

                    System.out.println(m.getKey() + " : " + m.getValue().subList(0, n));
                } else
                    System.out.println(m.getKey() + " : " + m.getValue());
            }
            System.out.println();
        }
        catch (Exception e)
        {
            System.out.println("Exception in \"showMapFirstN(int n)\": " + e);
        }
    }
    public void showMapFirstN_forEach(int n)
    {
        try
        {
        producer_models_Map.forEach((k,v) ->
        {
            if(v.size() >= n)
            System.out.println( k + " : " + v.subList(0, n));
            else
            System.out.println( k + " : " + v);

        });
        }
        catch(Exception e)
        {
            System.out.println("Exception in \"showMapFirstN_forEach(int n)\": " + e);
        }
    }
    public void showMapFirstN_iterator(int n)
    {
        try
        {
            Iterator<Map.Entry<String, List<String >>> iterator = producer_models_Map.entrySet().iterator();

            while(iterator.hasNext())
            {
                Map.Entry<String, List<String>> entry = iterator.next();
                if (entry.getValue().size() >= n)
                    System.out.println(entry.getKey()+": " + entry.getValue().subList(0,n));
                else
                    System.out.println(entry.getKey()+": " + entry.getValue());
            }
        }
        catch(Exception e)
        {
            System.out.println("Exception in \"showMapFirstN_iterator(int n)\": " + e);
        }
    }


    public void frequencyOfDiffNames()
    {
        LinkedHashMap<String, Integer> frequencies =new LinkedHashMap<>();
        if(appliances.size() == 0)
        {
            this.readFromFile(pathname1Mixed);
        }
        for (Appliance app: appliances)
        {
             if(!frequencies.containsKey(app.getName()))
             {
                 frequencies.put(app.getName(),1);
             }
             else
             {
                 Integer value = frequencies.get(app.getName()) + 1;
                 frequencies.replace(app.getName(), value);
             }
        }
        System.out.println("\nFrequency characteristics of different appliances name: ");

        Map<String, Integer> result = frequencies.entrySet()// sort by value
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        for(Map.Entry<String, Integer> m : result.entrySet())
        {
            System.out.println(m.getKey() + " : " + m.getValue());
        }
        System.out.println();

    }


    public void thirdTask(String pathname1,String pathname2)
    {
        List<Appliance> unmodifiableList = Collections.unmodifiableList(appliances);
        appliances.clear();
        this.readFromFile(pathname1);
        this.readFromFile(pathname2);
        System.out.println("Common collection: ");
        this.showAppliances();
        this.appliances.sort(Comparator.naturalOrder());
        System.out.println("Sorted Common collection (descending order) unmodifiableList: ");
        this.showAppliancesSpecific(unmodifiableList);
        System.out.println("Are unmodifiable & modifiable collections equal: "+ this.collectionsAreEqual(appliances, unmodifiableList));
        int n = this.appliances.size();
        System.out.println("Total appliances count: "+n);
        double totalPrice = 0.0;
        for (Appliance appliance: appliances)
        {
            totalPrice+= appliance.getCost();
        }
        System.out.println("Total price for all appliances is: "+totalPrice);
    }
    public boolean collectionsAreEqual(List<Appliance> l1, List<Appliance> l2)
    {
        if(l1.size() == l2.size())
        {
            for (int i = 0; i < l1.size(); i++)
            {
                if(!l1.get(i).equals(l2.get(i)))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
