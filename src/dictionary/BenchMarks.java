package dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BenchMarks {

    public static void main(String[] args) throws FileNotFoundException {
        sortedArrayTest();
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        hashTest();
    }

    private static void sortedArrayTest() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/dictionary/dtengl.txt"));
        long startTime = System.currentTimeMillis();
        Dictionary<String, String> dict1 = new SortedArrayDictionary<>();
        for (int i = 0; i < 8000; i++) {
            if (sc.hasNextLine()) {
                String[] currentArgs = sc.nextLine().split(" ");
                dict1.insert(currentArgs[0], currentArgs[1]);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("SortedArrayDictionary: Aufbau fuer 8000 Eintraege = " + (endTime - startTime) + "ms");

        sc = new Scanner(new File("src/dictionary/dtengl.txt"));
        startTime = System.currentTimeMillis();
        Dictionary<String, String> dict2 = new SortedArrayDictionary<>();
        for (int i = 0; i < 16000; i++) {
            if (sc.hasNextLine()) {
                String[] currentArgs = sc.nextLine().split(" ");
                dict2.insert(currentArgs[0], currentArgs[1]);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("SortedArrayDictionary: Aufbau fuer 16000 Eintraege = " + (endTime - startTime) + "ms");

        sc = new Scanner(new File("src/dictionary/dtengl.txt"));
        ArrayList<String> list8k = new ArrayList<>();
        for (int i = 0; i < 8000; i++) {
            if (sc.hasNextLine()) {
                String[] currentArgs = sc.nextLine().split(" ");
                list8k.add(currentArgs[0]);
            }
        }
        sc = new Scanner(new File("src/dictionary/dtengl.txt"));
        ArrayList<String> list16k = new ArrayList<>();
        for (int i = 0; i < 16000; i++) {
            if (sc.hasNextLine()) {
                String[] currentArgs = sc.nextLine().split(" ");
                list16k.add(currentArgs[0]);
            }
        }
        sc = new Scanner(new File("src/dictionary/dtengl.txt"));
        Dictionary<String, String> dict3 = new SortedArrayDictionary<>();
        for (int i = 0; i < 8000; i++) {
            if (sc.hasNextLine()) {
                String[] currentArgs = sc.nextLine().split(" ");
                dict3.insert(currentArgs[0], currentArgs[1]);
            }
        }
        sc = new Scanner(new File("src/dictionary/dtengl.txt"));
        Dictionary<String, String> dict4 = new SortedArrayDictionary<>();
        for (int i = 0; i < 8000; i++) {
            if (sc.hasNextLine()) {
                String[] currentArgs = sc.nextLine().split(" ");
                dict4.insert(currentArgs[0], currentArgs[1]);
            }
        }
        startTime = System.currentTimeMillis();
        for (String s : list8k) {
            dict3.search(s);
        }
        endTime = System.currentTimeMillis();
        System.out.println("SortedArrayDictionary: Suchen fuer 8000 Eintraege = " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        for (String s : list16k) {
            dict4.search(s);
        }
        endTime = System.currentTimeMillis();
        System.out.println("SortedArrayDictionary: Suchen fuer 16000 Eintraege = " + (endTime - startTime) + "ms");

        sc = new Scanner(new File("src/dictionary/dtengl.txt"));
        ArrayList<String> list8knotfound = new ArrayList<>();
        for (int i = 0; i < 8000; i++) {
            if (sc.hasNextLine()) {
                String[] currentArgs = sc.nextLine().split(" ");
                list8knotfound.add(currentArgs[1]);
            }
        }
        sc = new Scanner(new File("src/dictionary/dtengl.txt"));
        ArrayList<String> list16knotfound = new ArrayList<>();
        for (int i = 0; i < 16000; i++) {
            if (sc.hasNextLine()) {
                String[] currentArgs = sc.nextLine().split(" ");
                list16knotfound.add(currentArgs[1]);
            }
        }
        sc = new Scanner(new File("src/dictionary/dtengl.txt"));
        Dictionary<String, String> dict5 = new SortedArrayDictionary<>();
        for (int i = 0; i < 8000; i++) {
            if (sc.hasNextLine()) {
                String[] currentArgs = sc.nextLine().split(" ");
                dict5.insert(currentArgs[0], currentArgs[1]);
            }
        }
        sc = new Scanner(new File("src/dictionary/dtengl.txt"));
        Dictionary<String, String> dict6 = new SortedArrayDictionary<>();
        for (int i = 0; i < 8000; i++) {
            if (sc.hasNextLine()) {
                String[] currentArgs = sc.nextLine().split(" ");
                dict6.insert(currentArgs[0], currentArgs[1]);
            }
        }
        startTime = System.currentTimeMillis();
        for (String s : list8knotfound) {
            dict3.search(s);
        }
        endTime = System.currentTimeMillis();
        System.out.println("SortedArrayDictionary: Nicht erfolgreiches Suchen fuer 8000 Eintraege = " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        for (String s : list16knotfound) {
            dict4.search(s);
        }
        endTime = System.currentTimeMillis();
        System.out.println("SortedArrayDictionary: Nicht erfolgreiches Suchen fuer 16000 Eintraege = " + (endTime - startTime) + "ms");
    }

    private static void hashTest() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/dictionary/dtengl.txt"));
        long startTime = System.currentTimeMillis();
        Dictionary<String, String> dict1 = new HashDictionary<>(3);
        for (int i = 0; i < 8000; i++) {
            if (scanner.hasNextLine()) {
                String[] currentArgs = scanner.nextLine().split(" ");
                dict1.insert(currentArgs[0], currentArgs[1]);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("HashDictionary: Aufbau fuer 8000 Eintraege = " + (endTime - startTime) + "ms");

        scanner = new Scanner(new File("src/dictionary/dtengl.txt"));
        startTime = System.currentTimeMillis();
        Dictionary<String, String> dict2 = new HashDictionary<>(3);
        for (int i = 0; i < 16000; i++) {
            if (scanner.hasNextLine()) {
                String[] currentArgs = scanner.nextLine().split(" ");
                dict2.insert(currentArgs[0], currentArgs[1]);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("HashDictionary: Aufbau fuer 16000 Eintraege = " + (endTime - startTime) + "ms");

        scanner = new Scanner(new File("src/dictionary/dtengl.txt"));
        ArrayList<String> list8k = new ArrayList<>();
        for (int i = 0; i < 8000; i++) {
            if (scanner.hasNextLine()) {
                String[] currentArgs = scanner.nextLine().split(" ");
                list8k.add(currentArgs[0]);
            }
        }
        scanner = new Scanner(new File("src/dictionary/dtengl.txt"));
        ArrayList<String> list16k = new ArrayList<>();
        for (int i = 0; i < 16000; i++) {
            if (scanner.hasNextLine()) {
                String[] currentArgs = scanner.nextLine().split(" ");
                list16k.add(currentArgs[0]);
            }
        }
        scanner = new Scanner(new File("src/dictionary/dtengl.txt"));
        Dictionary<String, String> dict3 = new HashDictionary<>(3);
        for (int i = 0; i < 8000; i++) {
            if (scanner.hasNextLine()) {
                String[] currentArgs = scanner.nextLine().split(" ");
                dict3.insert(currentArgs[0], currentArgs[1]);
            }
        }
        scanner = new Scanner(new File("src/dictionary/dtengl.txt"));
        Dictionary<String, String> dict4 = new HashDictionary<>(3);
        for (int i = 0; i < 8000; i++) {
            if (scanner.hasNextLine()) {
                String[] currentArgs = scanner.nextLine().split(" ");
                dict4.insert(currentArgs[0], currentArgs[1]);
            }
        }
        startTime = System.currentTimeMillis();
        for (String s : list8k) {
            dict3.search(s);
        }
        endTime = System.currentTimeMillis();
        System.out.println("HashDictionary: Suchen fuer 8000 Eintraege = " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        for (String s : list16k) {
            dict4.search(s);
        }
        endTime = System.currentTimeMillis();
        System.out.println("HashDictionary: Suchen fuer 16000 Eintraege = " + (endTime - startTime) + "ms");

        scanner = new Scanner(new File("src/dictionary/dtengl.txt"));
        ArrayList<String> list8knotfound = new ArrayList<>();
        for (int i = 0; i < 8000; i++) {
            if (scanner.hasNextLine()) {
                String[] currentArgs = scanner.nextLine().split(" ");
                list8knotfound.add(currentArgs[1]);
            }
        }
        scanner = new Scanner(new File("src/dictionary/dtengl.txt"));
        ArrayList<String> list16knotfound = new ArrayList<>();
        for (int i = 0; i < 16000; i++) {
            if (scanner.hasNextLine()) {
                String[] currentArgs = scanner.nextLine().split(" ");
                list16knotfound.add(currentArgs[1]);
            }
        }
        scanner = new Scanner(new File("src/dictionary/dtengl.txt"));
        Dictionary<String, String> dict5 = new HashDictionary<>(3);
        for (int i = 0; i < 8000; i++) {
            if (scanner.hasNextLine()) {
                String[] currentArgs = scanner.nextLine().split(" ");
                dict5.insert(currentArgs[0], currentArgs[1]);
            }
        }
        scanner = new Scanner(new File("src/dictionary/dtengl.txt"));
        Dictionary<String, String> dict6 = new HashDictionary<>(3);
        for (int i = 0; i < 8000; i++) {
            if (scanner.hasNextLine()) {
                String[] currentArgs = scanner.nextLine().split(" ");
                dict6.insert(currentArgs[0], currentArgs[1]);
            }
        }
        startTime = System.currentTimeMillis();
        for (String s : list8knotfound) {
            dict5.search(s);
        }
        endTime = System.currentTimeMillis();
        System.out.println("HashDictionary: Nicht erfolgreiches Suchen fuer 8000 Eintraege = " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        for (String s : list16knotfound) {
            dict6.search(s);
        }
        endTime = System.currentTimeMillis();
        System.out.println("HashDictionary: Nicht erfolgreiches Suchen fuer 16000 Eintraege = " + (endTime - startTime) + "ms");
    }
}
