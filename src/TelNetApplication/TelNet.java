package TelNetApplication;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasse zur Verwaltung von Telefonknoten mit (x,y)-Koordinaten
 * und zur Berechnung eines minimal aufspannenden Baums
 * mit dem Algorithmus von Kruskal. Kantengewichte sind
 * durch den Manhattan-Abstand definiert.
 * */
public class TelNet
{
    private int leitungsbegrenzungswert;
    private List<TelVerbindung> minimalSpanningTree;
    private HashMap<TelKnoten, Integer> nodeMap;
    private int nodeCounter;

    /**
     * Legt ein neues Telefonnetz mit dem Leitungsbegrenzungswert lbg an.
     * Parameters: lbg - Leitungsbegrenzungswert.
     * */
    public TelNet(int lbg)
    {
        this.leitungsbegrenzungswert = lbg;
        this.minimalSpanningTree = new LinkedList<>();
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
        return true;
    }

    /**
     * Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem Algorithmus von Kruskal.
     * Returns:
     * true, falls es einen minimal aufspannden Baum gibt, sonst false.
     * */
    public boolean computeOptTelNet()
    {
        return true;
    }

    /**
     * Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen zurück.
     * Returns:
     * Liste von Telefonverbindungen.
     * Throws:
     * java.lang.IllegalStateException - falls nicht zuvor computeOptTelNet() erfolgreich durchgeführt wurde.
     * */
    public java.util.List<TelVerbindung> getOptTelNet() throws java.lang.IllegalStateException
    {
        return new LinkedList<TelVerbindung>();
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
        return 0;
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
        // be happy
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
        // still be happy
    }

    /**
     * Liefert die Anzahl der Knoten des Telefonnetzes zurück.
     *
     * Returns:
     *     Anzahl der Knoten des Telefonnetzes.
     */
    public int size()
    {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args)
    {
        //main :)
    }

}
