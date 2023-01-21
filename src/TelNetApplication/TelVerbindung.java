package TelNetApplication;

public class TelVerbindung
{
    public final TelKnoten u; // Anfangsknoten
    public final TelKnoten v; // Endknoten
    public final int c; // Verbindungskosten

    /**
     * Legt eine neue Telefonverbindung von u nach v mit Verbindungskosten c an.
     * */
    public TelVerbindung(TelKnoten u, TelKnoten v, int c)
    {
        this.u = u;
        this.v = v;
        this.c = c;
    }


    @Override
    public String toString() {
        return String.format("Tv: [start %s --> ende %s] Vk %d ", u, v, c);
    }
}
