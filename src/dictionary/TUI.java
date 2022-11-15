package dictionary;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class TUI {

    private static Dictionary<String, String> dictionary;

    public static void main(String[] args) throws Exception{
        System.out.println("German - English Dictionary v.1.2\n");
        Scanner sc = new Scanner(System.in);

        for(;;){
            System.out.print("<slinux>$ ");
            String in = sc.nextLine();
            command(in);
        }
    }

    private static void command(String com) throws Exception
    {
        String args[] = com.split(" ");

        switch (args[0])
        {
            case "create":
                sCreate(args);
                break;
            case "read":
                if (dictionaryExists())
                    sRead(Arrays.copyOfRange(args, 1, args.length));
                else
                    System.out.println("Dictionary doesn't exist");
                break;
            case "p":
                if (dictionaryExists())
                    sPrint();
                else
                    System.out.println("Dictionary doesn't exist");
                break;
            case "s":
                if (dictionaryExists())
                    sSearch(Arrays.copyOfRange(args, 1, args.length));
                else
                    System.out.println("Dictionary doesn't exist");
                break;
            case "i":
                if (dictionaryExists())
                    sInsert(Arrays.copyOfRange(args, 1, args.length));
                else
                    System.out.println("Dictionary doesn't exist");
                break;
            case "r":
                if (dictionaryExists())
                    sRemove(Arrays.copyOfRange(args, 1, args.length));
                else
                    System.out.println("Dictionary doesn't exist");
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("wrong command!");
                break;
        }
    }



    private static void sRemove(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("wrong command!");
            return;
        }

        dictionary.remove(args[0]);
    }

    private static void sInsert(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("wrong command!");
            return;
        }

        dictionary.insert(args[0], args[1]);
    }

    private static void sSearch(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("wrong command!");
            return;
        }

        System.out.println(dictionary.search(args[0]));
    }

    private static void sPrint()
    {
        for (var element : dictionary)
            System.out.println(element.getKey() + " - " + element.getValue());
    }

    private static void sRead(String[] args) throws Exception
    {
        if (args.length != 1 && args.length != 2)
        {
            System.out.println("wrong command!");
            return;
        }

        BufferedReader reader = null;
        String line;

        if (args.length == 1)
        {
            reader = new BufferedReader(new FileReader(new File(args[0])));

            while ((line = reader.readLine()) != null)
            {
                String[] words = line.split(" ");
                dictionary.insert(words[0], words[1]);
            }
        }
        else if (args.length == 2)
        {
            reader = new BufferedReader(new FileReader(new File(args[1])));

            int counter = 0;
            while ((line = reader.readLine()) != null && counter < Integer.parseInt(args[0]))
            {
                String[] words = line.split(" ");
                dictionary.insert(words[0], words[1]);
                counter++;
            }
        }
        reader.close();
    }

    private static void sCreate(String[] args) throws Exception
    {
        if (args.length >= 2)
        {
            // to lower case, that command becomes case insensitive
            String arg = args[1].toLowerCase();
            if (arg.equals("hashdictionary")){
                dictionary = new HashDictionary<>(3);
                System.out.println("HashDicitionary sucessfully created!");
                return;
            } else if (arg.equals("binarytreedictionary")){
                System.out.println("//muss erst für die naechste Abgabe implementiert werden :) \n");
                return;
            }
        }
        dictionary = new SortedArrayDictionary<>();
        System.out.println("SortedArrayDictionary successfully created!");

    }

    private static boolean dictionaryExists(){
        return dictionary == null ? false : true;
    }


}
