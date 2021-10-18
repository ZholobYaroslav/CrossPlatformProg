package com.company;

import java.io.File;
import java.util.*;

public class Manager
{
    public Set<String> dictionary;

    public Manager(Set<String> dictionary)
    {
        this.dictionary = dictionary;
    }
    public Manager()
    {
        this.dictionary = new LinkedHashSet<>();
    }

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    public void readFromFile(String fileName)
    {
        try {
            dictionary.clear();
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] strArr = line.split("\\s+");
                for (String str: strArr)
                {
                    if(!str.isEmpty())
                    dictionary.add(str);
                }
            }
        }
        catch(Exception exception)
        {
            System.out.println("Exception in \"readFromFile\": " + exception);
        }
    }
    public void show()
    {
        int i = 0;
        for (String str: dictionary)
        {
            System.out.println("#"+ i++ +" "+str);
        }
        System.out.println("elements count - " + dictionary.size());
    }

    // O(n * k), n - array.size(), k - count of words in "bigWord" ??
    public boolean isDividable(String word) // searches subwords in word, EXCLUDING whole word itself
    {
        if(word != null && word.length() > 0 && this.dictionary.size() > 1)
        {
            int lastIndOfFoundWord = 0;
            int i = 0;
            int count = 0;
            int inOneWord = 0;
            String[] words = dictionary.toArray(String[]::new);

            do {
                ++count;
                if(words[i].length() <= word.length()-lastIndOfFoundWord)
                {
                    String wordSubstring = word.substring(lastIndOfFoundWord,lastIndOfFoundWord+words[i].length());
                    if(wordSubstring.equals(words[i]))
                    {
                        inOneWord++;
                        lastIndOfFoundWord += wordSubstring.length();
                        if(lastIndOfFoundWord == word.length())
                        {
                            if(inOneWord > 1)
                            {
                                System.out.print("Iters = " + count);
                                return true;
                            }
                            else
                            {
                                i++;
                                inOneWord = 0;
                                lastIndOfFoundWord = 0;
                                continue;
                            }
                        }
                        i = 0;
                        continue;
                    }
                }
                ++i;
            }
            while(i != dictionary.size());

        }
        return false;
    }

    public boolean isDividableRecursion(String word)
    {
        return this.isDividableWithRecursion(word, dictionary.toArray(String[]::new), 0,0, 0);
    }
    private boolean isDividableWithRecursion(String word, String[] words, int index, int count, int inOneWord)
    {
        if(word != null && word.length() > 0 && this.dictionary.size() > 1)
        {
            if (word.length() == index)
            {
                if(inOneWord > 1)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            for (int i = 0; i < words.length; i++)
            {
                count++;
                int last = words[i].length() + index;
                if(last <= word.length())
                {
                    if(word.substring(index, last).equals(words[i]))
                    {
                        inOneWord++;
                        // index += words[i].length();
                        // The same here ==
                        // last
                        if(isDividableWithRecursion(word, words, last, count, inOneWord))
                        {
                            System.out.print("Iters = "+count+"; ");//total iters count is FIRST value, all next are due to recursion growing steps
                                                                    //in reversed order, so that: first int was 8 + 4 + 2 = 14 (8, 8+4, (8+4)+2).reversed
                            return true;
                        }
                        inOneWord = 0;
                    }
                }
            }
        }
        return false;
    }

    public boolean naiveEcho(String word)
    {
        return naiveEchoLvivUa(word, dictionary, 0, 0,0);
    }

    private boolean naiveEchoLvivUa(String word, Set<String> words, int index, int count, int inOneWord)
    {
        if(index == word.length())
        {
            if(inOneWord > 1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        for(String str: words)
        {
            count++;
            int end = index + str.length();
            if(end <= word.length())
            {
                if (word.substring(index, end).equals(str))
                {
                    inOneWord++;
                    if(naiveEchoLvivUa(word, words, end, count, inOneWord))
                    {
                        System.out.print("Iters = "+count+"; ");
                        return true;
                    }
                    inOneWord = 0;
                }
            }
        }
        return false;
    }
    //
    //Dynamic programming - O(string length * dict size).
    //
    public boolean dynamic(String word)
    {
        if (word != null && word.length() > 0 && this.dictionary.size() > 1)
        {
            int count = 0;
            int inOneWord = 0;
            boolean[] boolTable = new boolean[word.length() + 1];
            boolTable[0] = true; //set first to be true => Because we need initial state


            for (int i = 0; i < word.length(); i++)
            {
                //continue from match position
                if (boolTable[i]) // if match(true) -> go ahead
                {
                    for (String str : dictionary)
                    {
                        count++;
                        int end = i + str.length();
                        if (end <= word.length())
                        {
                            //count++; --?
                            if (!boolTable[end]) // if true -> no need to compare second time
                            {
                                if (word.substring(i, end).equals(str))
                                {
                                    inOneWord++;
                                    boolTable[end] = true;
                                    // ? break;
                                }
                                if(end == word.length() && inOneWord == 1)
                                {
                                    boolTable[end] = false;
                                }
                            }
                        }
                        //inOneWord = 0;
                    }
                    //
                }
            }
            //this.boolShow(boolTable);
            System.out.print("Iters = " + count);
            return boolTable[word.length()];
        }
        return false;
    }

    public void boolShow(boolean[] mas)
    {
        System.out.print(" { ");
        for (boolean b:mas)
        {
                System.out.print(b+" ");
        }
        System.out.print(" }");
    }

    public boolean wordBreak(String s)
    {
        int[] position = new int[s.length()+1];
        Arrays.fill(position, -1);
        position[0]=0;
        int count = 0;
        int inOneWord = 0;

        for(int i=0; i < s.length(); i++)
        {
            if(position[i]!=-1)// continue from match position
            {
                for(int j = i+1; j <= s.length(); j++)
                {
                    count++;
                    if(dictionary.contains(s.substring(i, j)))
                    {
                        position[j] = i;
                    }
                }
            }
        }
        /*System.out.print(" { ");
        for (int b:position)
        {
            System.out.print(b+" ");
        }
        System.out.print(" }");*/
        System.out.print("Iters = " + count);
        return position[s.length()]!=-1;
    }
    //
    public ArrayList<ArrayList<String>> readFromFile2(String fileName)
    {
        try {
            dictionary.clear();
            Scanner scanner = new Scanner(new File(fileName));
            ArrayList<ArrayList<String>> result = new ArrayList<>();
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] strArr = line.split("\\s+");
                ArrayList<String> node = new ArrayList<>();
                for (String str: strArr)
                {
                    if(!str.isEmpty())
                    {
                        node.add(str);
                        //dictionary.add(str);
                    }
                }
                result.add(node);
            }
            this.showArrArrStr(result);
            return result;
        }
        catch(Exception exception)
        {
            System.out.println("Exception in \"readFromFile\": " + exception);
        }
        return null;

    }
    public void showArrArrStr(ArrayList<ArrayList<String>> result)
    {
        int i = 0; int j = 0;
        for (ArrayList<String> node: result)
        {
            System.out.print("#"+i+" row: { ");
            for (String str: node)
            {
                System.out.print(str+" ");
                j++;
            }
            System.out.println("}.");
            i++;
        }
    }

    public ArrayList<String> secondTask(String searchedWord, String fileName)
    {
        //Замінити шукані слова на перше слово кожного речення, у якому вони знаходяться.
        System.out.println("\n Second task.");
        if(dictionary.contains(searchedWord))
        {
            ArrayList<String> newDictionary = new ArrayList<String>(dictionary);//(ArrayList<String>)dictionary.stream().toList();
            ArrayList<ArrayList<String>> other = this.readFromFile2(fileName);
            int changedWordInd = -1;
            for (int i = 0; i < newDictionary.size(); i++)
            {
                String wordInDict = newDictionary.get(i);
                if(newDictionary.get(i).equals(searchedWord))
                {
                    changedWordInd = i;
                    for (int k = 0; k < other.size(); k++)
                    {
                        for (int j = 0; j < other.get(k).size(); j++)
                        {
                            if(other.get(k).get(j).equals(searchedWord))
                            {
                                newDictionary.set(i,other.get(k).get(0));
                                other.get(k).set(j, other.get(k).get(0));
                                //this.showArrArrStr(other);
                            }
                        }
                    }
                }
            }
            System.out.print("Modified searched word in set: ");
            int i = 0;
            for (String str: newDictionary)
            {
                if(i ==changedWordInd)
                {
                    System.out.printf("%s", ANSI_GREEN + str + ANSI_RESET+" ");
                }
                else
                {
                    System.out.print(str + " ");
                }
                i++;
            }
            return newDictionary;
        }
        else
            return null;
    }
    //
    //4 Output
    //
    public ArrayList<String> output(String algoName, Set<String> searchWords, int flagsWidth, int strategyNumber)
    {
        ArrayList<String> res = new ArrayList<>();
        System.out.println("\n" + algoName + ":");
        IStrategy strategy = null;
        switch(strategyNumber)
        {
            case 1:
                strategy = this::isDividable;
                break;
            case 2:
                strategy = word -> isDividableRecursion(word);
                break;
            case 3:
                strategy = new IStrategy() {
                    @Override
                    public boolean execute(String word) {
                        return naiveEcho(word);
                    }
                };
                break;
            case 4:
                strategy = this::dynamic;
                break;
            case 5:
                strategy = word -> wordBreak(word);
                break;
            default:
                System.out.println("Default switch-case in output method.");
                break;
        }
        for (String str: searchWords)
        {


            System.out.printf("%"+(-flagsWidth)+"s", "word - "+str+".");
            boolean result = strategy.execute(str);
            if(result)
            {
                System.out.printf("%"+flagsWidth+"s %n", " Found status: " + ANSI_GREEN + result + ANSI_RESET);
            }
            else
            System.out.printf("%"+flagsWidth+"s %n", " Found status: " + ANSI_RED + result + ANSI_RESET);
            //System.out.printf("%"+(-flagsWidth)+"s %n", "Found status: " + algoName("swordartonline")); //?
            if(result)
            {
                res.add(str);
            }
        }
        System.out.print("All dividable words in word-set: {");
        int i = 1;
        for (String str:res)
        {
            if(i == res.size())
                System.out.print(ANSI_GREEN + str + ANSI_RESET + ".");
            else
                System.out.print(ANSI_GREEN + str + ANSI_RESET + ", ");
            i++;
        }
        System.out.println("}");
        return res;
    }
    public void firstTask()
    {
        System.out.println("\nFirst task.");
        output("isDividable", dictionary, 25, 1);
        output("isDividableWithRecursion", dictionary, 25, 2);
        output("naiveEcho", dictionary, 25, 3);
        output("dynamic", dictionary, 25, 4);
        //output("wordBreak", dictionary, 25, 5);
    }

}


/*
    public boolean isDividable(String word)
    {
        if(word != null && word.length() > 0 && this.dictionary.size() > 1)
        {
            int lastIndOfFoundWord = 0;
            int i = 0;
            int count = 0;
            String[] words = dictionary.toArray(String[]::new);

            do {
                ++count;
                if(words[i].length() <= word.length()-lastIndOfFoundWord)
                {
                    String wordSubstring = word.substring(lastIndOfFoundWord,lastIndOfFoundWord+words[i].length());
                    if(wordSubstring.equals(words[i]))
                    {
                        lastIndOfFoundWord += wordSubstring.length();
                        if(lastIndOfFoundWord == word.length())
                        {
                            System.out.print("Iters = "+count);
                            return true;
                        }
                        i = 0;
                        continue;
                    }
                }
                ++i;
            }
            while(i != dictionary.size());

        }
        return false;
    }

    public boolean isDividableRecursion(String word)
    {
        return this.isDividableWithRecursion(word, dictionary.toArray(String[]::new), 0,0);
    }
    private boolean isDividableWithRecursion(String word, String[] words, int index, int count)
    {
        if(word != null && word.length() > 0 && this.dictionary.size() > 1)
        {
            if (word.length() == index)
            {
                return true;
            }
            for (int i = 0; i < words.length; i++)
            {
                count++;
                int last = words[i].length() + index;
                if(last <= word.length())
                {
                    if(word.substring(index, last).equals(words[i]))
                    {
                        // index += words[i].length();
                        // The same here ==
                        // last
                        if(isDividableWithRecursion(word, words, last, count))
                        {
                            System.out.print("Iters = "+count+"; ");//total iters count is FIRST value, all next are due to recursion growing steps
                                                                    //in reversed order, so that: first int was 8 + 4 + 2 = 14 (8, 8+4, (8+4)+2).reversed
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean naiveEcho(String word)
    {
        return naiveEchoLvivUa(word, dictionary, 0, 0);
    }

    private boolean naiveEchoLvivUa(String word, Set<String> words, int index, int count)
    {
        if(index == word.length())
        {
            return true;
        }
        for(String str: words)
        {
            count++;
            int end = index + str.length();
            if(end <= word.length())
            {
                if (word.substring(index, end).equals(str))
                {
                    if(naiveEchoLvivUa(word, words, end, count))
                    {
                        System.out.print("Iters = "+count+"; ");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //
    //Dynamic programming - O(string length * dict size).
    //
    public boolean dynamic(String word)
    {
        if (word != null && word.length() > 0 && this.dictionary.size() > 1)
        {
            int count = 0;
            boolean[] boolTable = new boolean[word.length() + 1];
            boolTable[0] = true; //set first to be true => Because we need initial state

            for (int i = 0; i < word.length(); i++)
            {
                //continue from match position
                if (boolTable[i]) // if match(true) -> go ahead
                {
                    for (String str : dictionary)
                    {
                        count++;
                        int end = i + str.length();
                        if (end <= word.length())
                        {
                            //count++; --?
                            if (!boolTable[end]) // if true -> no need to compare second time
                            {
                                if (word.substring(i, end).equals(str))
                                {
                                    boolTable[end] = true;
                                    // ? break;
                                }
                            }
                        }
                    }
                }
            }
            //this.boolShow(boolTable);
            System.out.print("Iters = " + count);
            return boolTable[word.length()];
        }
        return false;
    }

    public void boolShow(boolean[] mas)
    {
        System.out.print(" { ");
        for (boolean b:mas)
        {
                System.out.print(b+" ");
        }
        System.out.print(" }");
    }

    public boolean wordBreak(String s)
    {
        int[] position = new int[s.length()+1];
        Arrays.fill(position, -1);
        position[0]=0;
        int count = 0;

        for(int i=0; i < s.length(); i++)
        {
            if(position[i]!=-1)// continue from match position
            {
                for(int j = i+1; j <= s.length(); j++)
                {
                    count++;
                    if(dictionary.contains(s.substring(i, j)))
                    {
                        position[j] = i;
                    }
                }
            }
        }
        System.out.print(" { ");
        for (int b:position)
        {
            System.out.print(b+" ");
        }
        System.out.print(" }");
        System.out.print("Iters = " + count);
                return position[s.length()]!=-1;
                }
 */
