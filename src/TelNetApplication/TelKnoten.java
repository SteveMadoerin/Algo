package TelNetApplication;


import java.util.HashMap;
import java.util.Objects;

public class TelKnoten
{
     public TelKnoten(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public final int x; // x coordinate
    public final int y; // < coordinate

    @Override
    public int hashCode()
    {
        return Objects.hash(x,y);
    }

    @Override
    public String toString()
    {
        return String.format("(%d/%d)", x, y);
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        TelKnoten tk = (TelKnoten) obj;
        return x == tk.x && y == tk.y;
    }
}
