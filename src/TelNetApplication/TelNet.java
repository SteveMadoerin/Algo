package TelNetApplication;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Klasse zur Verwaltung von Telefonknoten mit (x,y)-Koordinaten
 * und zur Berechnung eines minimal aufspannenden Baums
 * mit dem Algorithmus von Kruskal. Kantengewichte sind
 * durch den Manhattan-Abstand definiert.
 * */
public class TelNet
{
    private int lbg; // leitungsbegrenzungswert
    private List<TelVerbindung> minSpanTree;
    private Map<TelKnoten, Integer> nodeMap;
    private int size; // Anzahl Knoten

    /**
     * Legt ein neues Telefonnetz mit dem Leitungsbegrenzungswert lbg an.
     * Parameters: lbg - Leitungsbegrenzungswert.
     * */
    public TelNet(int lbg)
    {
        this.lbg = lbg;
        this.minSpanTree = new LinkedList<>();
        this.nodeMap = new HashMap<>();
    }

    /**
     * Fügt einen neuen Telefonknoten mit Koordinate (x,y) dazu.
     * Parameters:
     * x - x-Koordinate.
     * y - y-Koordinate.
     * Returns:
     * true, falls die Koordinate neu ist, sonst false.
     * */
    public boolean addTelKnoten(int x, int y)
    {
        TelKnoten tk = new TelKnoten(x,y);

        // coordinate existiert schon
        if(nodeMap.containsKey(tk))
        {
            return false;
        }

        // coodinate ist neu
        nodeMap.put(tk, size++);
        return true;
    }

    int manhattan_distance(TelKnoten u,TelKnoten v)
    {
        return Math.abs(u.x - v.x) + Math.abs(u.y - v.y);
        //return cost <= lbg ? cost : Integer.MAX_VALUE;
    }

    /**
     * Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem Algorithmus von Kruskal.
     * Returns:
     * true, falls es einen minimal aufspannden Baum gibt, sonst false.
     * */
    public boolean computeOptTelNet()
    {
        // angelehnt an 10-17 Unterlagen
        // Forest is Menge von Bauemen -> bestehen anfaenlich aus je einem Knoten aus V.
        UnionFind forest = new UnionFind(size);
        PriorityQueue<TelVerbindung> edges = new PriorityQueue<>(size, Comparator.comparing(x -> x.c));
        //List<TelVerbindung> minSpanTree;

        // fill PrioQueue
        for(var v: nodeMap.entrySet())
        {
            for(var w: nodeMap.entrySet())
            {
                if(v.equals(w))
                    continue;

                // kostencalculieren
                //int cos = (Math.abs(v.getKey().x - w.getKey().x) + Math.abs(v.getKey().y - w.getKey().y));
                int cos = manhattan_distance(v.getKey(), w.getKey());
                if(cos <= lbg)
                {
                    edges.add(new TelVerbindung(v.getKey(),w.getKey(), cos));
                }

            }
        }

        // Alle Kanten werden mit Gewichten in Proliste gesaved -> effizienter zugriff auf Kante mit kleinstem Gewicht.

        // solange wald mehr als 1 Baum enthält & es noch Kanten gibt
        while((forest.size() != 1) && (!edges.isEmpty()))
        {
            TelVerbindung tv = edges.poll();
            var t1 = forest.find(nodeMap.get(tv.u));
            var t2 = forest.find(nodeMap.get(tv.v));

            if (t1 != t2)
            {
                forest.union(t1,t2);
                minSpanTree.add(tv);
            }
        }

        return !edges.isEmpty() || forest.size() == 1;

    }

    /**
     * Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen zurück.
     * Returns:
     * Liste von Telefonverbindungen.
     * Throws:
     * java.lang.IllegalStateException - falls nicht zuvor computeOptTelNet() erfolgreich durchgeführt wurde.
     * */
    public List<TelVerbindung> getOptTelNet() throws java.lang.IllegalStateException
    {
/*        if(!computeOptTelNet())
        {
            throw new IllegalStateException();
        }*/

        return minSpanTree;
    }

    /**
     * Liefert die Gesamtkosten eines optimalen Telefonnetzes zurück.
     * Returns:
     * Gesamtkosten eines optimalen Telefonnetzes.
     * Throws:
     * java.lang.IllegalStateException - falls nicht zuvor computeOptTelNet() erfolgreich durchgeführt wurde.
     * */
    public int getOptTelNetKosten() throws java.lang.IllegalStateException
    {
/*        if(!computeOptTelNet())
        {
            throw new IllegalStateException();
        }*/
        int cost = 0;
        for(var x: minSpanTree){
            cost += x.c;
        }
        return cost;
    }

    /**
     * Zeichnet das gefundene optimale Telefonnetz mit der Größe xMax*yMax in ein Fenster.
     * Parameters:
     * xMax - Maximale x-Größe.
     * yMax - Maximale y-Größe.
     * Throws:
     * java.lang.IllegalStateException - falls nicht zuvor computeOptTelNet() erfolgreich durchgeführt wurde.
     * */
    public void drawOptTelNet(int xMax, int yMax) throws java.lang.IllegalStateException
    {
/*        if(!computeOptTelNet())
        {
            throw new IllegalStateException();
        }*/

        StdDraw.setCanvasSize(256,256);
        StdDraw.setXscale(0, xMax + 1);
        StdDraw.setYscale(0, yMax + 1);

        // draw the grid

        for (int i = 0; i < yMax+1; i++)
        {
            StdDraw.line(0.5, i+0.5,yMax + 0.5, i+0.5);
        }

        for (int i = 0; i < xMax+1; i++)
        {
            StdDraw.line(i+0.5, 0.5, i+0.5, xMax+0.5);
        }

        for(var s: minSpanTree)
        {
            StdDraw.setPenColor(Color.PINK);
            if(s.u.x < s.v.x && s.u.y < s.v.y || s.u.x > s.v.x && s.u.y < s.v.y)
            {
                StdDraw.line(s.u.x, s.u.y, s.u.x, s.v.y);
                StdDraw.line(s.u.x, s.v.y, s.v.x, s.v.y);
            }
            else if(s.u.y == s.v.y || s.u.x == s.v.x  )
            {
                StdDraw.line(s.u.x, s.u.y, s.v.x, s.v.y);
            }
            else if (s.v.x > s.u.x && s.v.y < s.u.y)
            {

                StdDraw.line(s.v.x, s.v.y, s.u.x, s.v.y);
                StdDraw.line(s.u.x, s.u.y, s.u.x, s.v.y);
            }
            else
            {
                StdDraw.line(s.u.x, s.u.y, s.v.x, s.u.y);
                StdDraw.line(s.v.x, s.u.y, s.v.x, s.v.y);
            }


            StdDraw.setPenColor(Color.BLUE);
            StdDraw.filledSquare(s.u.x, s.u.y, 0.5);
            StdDraw.filledSquare(s.v.x, s.v.y, 0.5);
        }

        StdDraw.show(0);
    }

    /**
     * Fügt n zufällige Telefonknoten zum Netz dazu mit x-Koordinate aus [0,xMax] und y-Koordinate aus [0,yMax].
     *
     * Parameters:
     *     n - Anzahl Telefonknoten
     *     xMax - Intervallgrenz für x-Koordinate.
     *     yMax - Intervallgrenz für y-Koordinate.
     */
    public void generateRandomTelNet(int n, int xMax, int yMax)
    {
        Random r = new Random();

        while(n != 0)
        {
            var x = r.nextInt(xMax+1);
            var y = r.nextInt(yMax+1);

            if(addTelKnoten(x,y))
                n--;
        }
    }

    /**
     * Liefert die Anzahl der Knoten des Telefonnetzes zurück.
     *
     * Returns:
     *     Anzahl der Knoten des Telefonnetzes.
     */
    public int size()
    {
        return this.size;
    }

    @Override
    public String toString() {
        return "Telnet: " + "MinSpanTree: " +minSpanTree+ "telnode: " + nodeMap + "size: " + size + "lbg: " + lbg;
    }

    public static void main(String[] args)
    {
        run_aufgabe3();

        //run_aufgabe4();
    }

    public static void run_aufgabe3()
    {
        TelNet tn = new TelNet(7);

        tn.addTelKnoten(1,1);
        tn.addTelKnoten(3,1);
        tn.addTelKnoten(4,2);
        tn.addTelKnoten(3,4);
        tn.addTelKnoten(2,6);
        tn.addTelKnoten(4,7);
        tn.addTelKnoten(7,6);

        System.out.println("minSpanTree ? " + tn.computeOptTelNet());
        System.out.println("Cost: " + tn.getOptTelNetKosten());

        System.out.println(tn);

        tn.drawOptTelNet(7,7);

    }

}
