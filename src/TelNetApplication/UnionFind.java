package TelNetApplication;

/**
 * Klasse für Union-Find-Strukturen. Unterstützt die effiziente
 * Verwaltung einer Partionierung (disjunkte Zerlegung) der
 * Grundmenge {0, 1, 2, ..., n-1}. union benötigt O(1) und find
 * benötigt O(log(n)).
 * */
public class UnionFind
{
    private int l[];
    private int size;

    /**
     *Legt eine neue Union-Find-Struktur mit der Partitionierung {{0}, {1}, ..., {n-1}} an.
     *
     * Parameters:
     *     n - Größe der Grundmenge.  */
    public UnionFind(int n)
    {
        this.l = new int[n];

        for (int i = 0; i < this.l.length; i++)
        {
            this.l[i] = -1;
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
        return 0;
    }

    /**
     * Vereinigt die beiden Menge s1 und s2. s1 und s2 müssen Repräsentanten der jeweiligen Menge sein. Die Vereinigung wird nur durchgeführt, falls s1 und s2 unterschiedlich sind. Es wird union-by-height durchgeführt.
     *
     * Parameters:
     *     s1 - Element, das eine Menge repräsentiert.
     *     s2 - Element, das eine Menge repräsentiert.
     * Throws:
     *     java.lang.IllegalArgumentException - falls s1 oder s2 nicht zur Grundmenge gehören.
     */
    public void union(int s1, int s2)
    {
        // be happy
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

    }
}
