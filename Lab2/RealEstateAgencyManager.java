package com.company;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class RealEstateAgencyManager
{

    public List<Flat> apartments;

    public RealEstateAgencyManager(List<Flat> apartments)
    {
        this.apartments = apartments;
    }
    public RealEstateAgencyManager() {
        this.apartments = new ArrayList<Flat>();
    }


    public void callMenu()
    {
        Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.println("Choose the option: 1 - add, 2 - remove, 3 - show all, Sort by... 4 - Area, 5 - Room count," +
                    " 6 - price for m^2, 7 - residental area, 8 - infrastructure distance. ");
            if (scanner.hasNextInt())
            {
                switch (scanner.nextInt()) {
                    case 1:
                        this.addApartment();
                        break;
                    case 2:
                        this.removeApartment();
                        break;
                    case 3:
                        this.show();
                        break;
                    case 4:
                        this.sortFlatByArea();
                        this.show();
                        break;
                    case 5:
                        this.sortFlatByRoomCount();
                        this.show();
                        break;
                    case 6:
                        System.out.println("1 - ascending order. 2 - descending order.");
                        if(scanner.nextInt() == 1)
                        {
                            this.sortByFlatComparableforPrice(true);
                        }
                        else
                        {
                            this.sortByFlatComparableforPrice(false);
                        }
                        this.show();
                        break;
                    case 7:
                        System.out.println("1 - ascending order. 2 - descending order.");
                        if(scanner.nextInt() == 1)
                        {
                            this.sortByResidentalAreaLambda(true);
                        }
                        else
                        {
                            this.sortByResidentalAreaLambda(false);
                        }
                        this.show();;
                        break;
                    case 8:
                        this.sortByInfrastructureDistanceAnonymous();
                        this.show();;
                        break;
                    default:
                        System.out.println("Option is out of range [1-8]!");
                        break;
                }
            } else {
                System.out.println("Wrong input.");
            }
            System.out.println();
        } while(!scanner.nextLine().equals("exit"));
    }

    private void addApartment()
    {
        System.out.println("Which type of apartment do You want to add: 1 - Flat, 2 - Penthouse, 3 - Villa.");
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextInt())
        {
            switch(scanner.nextInt())
            {
                case 1:
                    System.out.println("Enter area(double):");
                    double area = scanner.nextDouble();
                    System.out.println("Enter roomCount(int):");
                    int roomCount = scanner.nextInt();
                    System.out.println("Enter residentalArea(String):");
                    scanner.skip("\\R"); // 1 variant - BEST
                    //scanner.nextLine();    // 2 variant - заглушка
                    String residentalArea = scanner.nextLine(); // 3 variant - scanner.next() instead of nextLine();
                    System.out.println("Enter pricePeSquareMeter(double):");
                    double pricePeSquareMeter = scanner.nextDouble();
                    System.out.println("Enter infrastructureDistance(double):");
                    double infrastructureDistance = scanner.nextDouble();

                    Flat flat = new Flat(area, roomCount, residentalArea, pricePeSquareMeter, infrastructureDistance);
                    if(this.apartments.add(flat))
                    {
                        System.out.println("Added!");
                    }
                    this.show();
                    break;
                case 2:
                    System.out.println("Enter area(double):");
                    area = scanner.nextDouble();
                    System.out.println("Enter roomCount(int):");
                    roomCount = scanner.nextInt();
                    scanner.skip("\\R"); // 1 variant - BEST
                    System.out.println("Enter residentalArea(String):");
                    residentalArea = scanner.nextLine();
                    System.out.println("Enter pricePeSquareMeter(double):");
                    pricePeSquareMeter = scanner.nextDouble();
                    System.out.println("Enter infrastructureDistance(double):");
                    infrastructureDistance = scanner.nextDouble();
                    System.out.println("Enter terraceArea(double):");
                    double terraceArea = scanner.nextDouble();
                    System.out.println("Enter hasPool(boolean):");
                    boolean hasPool = scanner.nextBoolean();

                    Penthouse penthouse = new Penthouse(area, roomCount, residentalArea, pricePeSquareMeter, infrastructureDistance, terraceArea, hasPool);
                    if(this.apartments.add(penthouse))
                    {
                        System.out.println("Added!");
                    }
                    this.show();
                    break;
                case 3:
                    System.out.println("Enter area(double):");
                    area = scanner.nextDouble();
                    System.out.println("Enter roomCount(int):");
                    roomCount = scanner.nextInt();
                    scanner.skip("\\R"); // 1 variant - BEST
                    System.out.println("Enter residentalArea(String):");
                    residentalArea = scanner.nextLine();
                    System.out.println("Enter pricePeSquareMeter(double):");
                    pricePeSquareMeter = scanner.nextDouble();
                    System.out.println("Enter infrastructureDistance(double):");
                    infrastructureDistance = scanner.nextDouble();
                    System.out.println("Enter landArea(double):");
                    double landArea = scanner.nextDouble();
                    System.out.println("Enter hasGarden(boolean):");
                    boolean hasGarden = scanner.nextBoolean();

                    Villa villa = new Villa(area, roomCount, residentalArea, pricePeSquareMeter, infrastructureDistance, landArea, hasGarden);
                    if(this.apartments.add(villa))
                    {
                        System.out.println("Added!");
                    }
                    this.show();
                    break;
                default:
                    System.out.println("Wrong. Should be in range[1-3].");
                    break;
            }
        }
        else
        {
            System.out.println("Wrong input. Should be in range [1-3]");
        }

    }
    private void removeApartment()
    {
        this.show();
        System.out.println("Enter index:");
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextInt())
        {
            int n = scanner.nextInt();
            if(n < apartments.size())
            {
                this.apartments.remove(n);
                System.out.println("Removed!");
                this.show();
            }
            else
            {
                System.out.println("Index to remove out ot range.");
            }
        }
        else
        {
            System.out.println("Wrong input. Should be in range [1-" + this.apartments.size()+ "]");
        }
    }
    private void show()
    {
        for(int i = 0; i < apartments.size(); i++)
        {
            System.out.println("#"+i + "| " + apartments.get(i));
        }
    }
    public void readFromFile()
    {
        try {
            apartments.clear();
            Scanner scanner = new Scanner(new File("A:\\University\\3year\\Cross-platform\\IdeaProjects\\Labs\\Lab02\\src\\com\\company\\Apartment.txt"));
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                Flat flat = null;
                switch(checkClass(line))
                {
                    case 'f':
                        flat = new Flat();
                        break;
                    case 'p':
                        flat = new Penthouse();
                        break;
                    case 'v':
                        flat = new Villa();
                        break;
                    default:
                        System.out.println("checkClass could't resolve the class");
                        break;
                }
                flat.parse(line);
                apartments.add(flat);
            }
        }
        catch(Exception exception)
        {
            System.out.println("Exception in \"readFromFile\": " + exception);
        }
    }
    private static char checkClass(String str)// can also return enum
    {
        return str.split(" ")[0].charAt(0);
    }

    private void sortByInfrastructureDistanceAnonymous()
    {
        apartments.sort(new Comparator<Flat>() {
            @Override
            public int compare(Flat o1, Flat o2) {
                return o1.getInfrastructureDistance() > o2.getInfrastructureDistance() ? 1 :
                        o1.getInfrastructureDistance() < o2.getInfrastructureDistance() ? -1 : 0;
            }
        });
    }
    private  void sortByResidentalAreaLambda(boolean ascending)
    {
        //apartments.sort(Comparator.naturalOrder()); - works but with that field that is used in Comparable interface in Flat class - how change field?

        apartments.sort((o1,o2) -> o1.getResidentalArea().compareTo(o2.getResidentalArea()));//  - works (lambda)
        //apartments.sort(Comparator.comparing(Flat::getResidentalArea));// - works
        if(!ascending)
        {
            Collections.reverse(apartments);
        }

    }
    private void sortByResAreaS_STREAM()
    {
        List<Flat> sorted = apartments.stream()
                .sorted(Comparator.comparing(Flat::getResidentalArea))
                .collect(Collectors.toList());
        System.out.println("Sorted Temp collection:");
        for(int i = 0; i < sorted.size(); i++)
        {
            System.out.println("#"+i + "| " + sorted.get(i));
        }
        System.out.println();
    }
    private void sortByFlatComparableforPrice(boolean ascending)// via Comparable interface in Flat
    {
        //apartments.sort(Comparator.naturalOrder()); - same
        Collections.sort(apartments);
        if(!ascending)
        {
            Collections.reverse(apartments);
        }
    }
    private void sortFlatByArea()
    {
        apartments.sort(new RealEstateAgencyManager.AreaComparator_static());
    }
    private void sortFlatByRoomCount()
    {
        apartments.sort(this.new RoomCountComparator_inner());
    }
    // static inner & inner classes with Comparator realization
    private static class AreaComparator_static implements Comparator<Flat>
    {
        @Override
        public int compare(Flat o1, Flat o2) {
            return o1.getArea() > o2.getArea() ? 1 : o1.getArea() < o2.getArea() ? -1 : 0;
        }
    }
    private class RoomCountComparator_inner implements Comparator<Flat>
    {

        @Override
        public int compare(Flat o1, Flat o2) {
            return o1.getRoomCount() > o2.getRoomCount() ? 1 : o1.getRoomCount() < o2.getRoomCount() ? -1 : 0;
        }
    }

}
