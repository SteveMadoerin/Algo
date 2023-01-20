package TelNetApplication;

import java.util.Arrays;

/**
 * Klasse für Union-Find-Strukturen. Unterstützt die effiziente
 * Verwaltung einer Partionierung (disjunkte Zerlegung) der
 * Grundmenge {0, 1, 2, ..., n-1}. union benötigt O(1) und find
 * benötigt O(log(n)).
 * */
public class UnionFind
{
    private int p[]; // Baume werden mit Elternfeld p dargestellt
    private int size; // Anzahl der Mengen

    /**
     *Legt eine neue Union-Find-Struktur mit der Partitionierung {{0}, {1}, ..., {n-1}} an.
     *
     * Parameters:
     *     n - Größe der Grundmenge.  */
    public UnionFind(int n)
    {
        this.p = new int[n];

        for (int i = 0; i < this.p.length; i++)
        {
            this.p[i] = -1;
        }
        this.size = n;
    }

    /**
     * Liefert den Repräsentanten der Menge zurück, zu der e gehört.
     *
     * Parameters:
     *     e - Element
     * Returns:
     *     Repräsentant der Menge, zu der e gehört.
     * Throws:
     *     java.lang.IllegalArgumentException - falls e nicht zur Grundmenge gehört.
     */
    public int find(int e)
    {
        // 10-24 Find-Algorithmus
        while (p[e]>=0) // e ist keine Wurzel
            e = p[e];

        return e;
    }

    /**
     * Vereinigt die beiden Menge s1 und s2.
     * s1 und s2 müssen Repräsentanten der jeweiligen Menge sein.
     * Die Vereinigung wird nur durchgeführt,
     * falls s1 und s2 unterschiedlich sind.
     * Es wird union-by-height durchgeführt.
     *
     * Parameters:
     *     s1 - Element, das eine Menge repräsentiert.
     *     s2 - Element, das eine Menge repräsentiert.
     * Throws:
     *     java.lang.IllegalArgumentException - falls s1 oder s2 nicht zur Grundmenge gehören.
     */
    public void union(int s1, int s2)
    {
        // unionByHeight
        // Baum mit kleinerer Höhe wird an Wurzel
        // des Baums mit groesserer Hoehe gehaengt
        // Hoehe h laesst sich bei p[e], e ist Wurzel,
        // als negative Zahl abspeicher: p[e] = -1-h

        // prüft ob s1 und s2 Repräsentanten einer Menge sind
        if(p[s1] >= 0 || p[s2] >= 0)
        {
            return;
        }

        // Wenn s1 & s2 dieselbe Menge ist: do nothing.
        if(s1 == s2)
        {
            return;
        }
        // wenn s1 weniger hoch als s2
        if(-p[s1] < -p[s2]) // Hoehe von s1 < Hoehe von s2
        {
            p[s1] = s2;
        }
        else
        {
            // wenn gleichhoch
            if(-p[s1] == -p[s2])
                p[s1]--; // Hoehe von s1 erhoet sich um 1
            p[s2] = s1;
        }


        this.size--;

    }


    /**
     * Liefert die Anzahl der Mengen in der Partitionierung zurück.
     *
     * Returns:
     *     Anzahl der Mengen.
     */
    public int size()
    {
        return this.size;
    }

    public static void main(String[] args)
    {
        // Testen
        int test_size = 14;
        UnionFind forest = new UnionFind(test_size);

        forest.union(5,8);

        forest.union(0,2);
        forest.union(0,1);

        forest.union(6,10);
        forest.union(11,13);
        forest.union(6,11);
        forest.union(4,9);
        forest.union(7,12);
        forest.union(4,7);
        forest.union(6,4);

        System.out.println("8 in: " + forest.find(8));
        System.out.println("4 in: " + forest.find(4));

        System.out.println("Folie 10-28");
        System.out.println("Amount of Partitions: " + forest.size());
        System.out.println("Union-Find Structure: " + Arrays.toString(forest.p) + "\n");

        forest.union(3,5);

        System.out.println("Folie 10-28 - union(3,5)");
        System.out.println("Amount of Partitions: " + forest.size());
        System.out.println("Union-Find Structure: " + Arrays.toString(forest.p) + "\n");

        forest.union(0, 5);

        System.out.println("Folie 10-29 - union(0,5)");
        System.out.println("Amount of Partitions: " + forest.size());
        System.out.println("Union-Find Structure: " + Arrays.toString(forest.p) + "\n");

        forest.union(0, 6);

        System.out.println("Folie 10-30 - union(0,6)");
        System.out.println("Amount of Partitions: " + forest.size());
        System.out.println("Union-Find Structure: " + Arrays.toString(forest.p) + "\n");

    }
}
