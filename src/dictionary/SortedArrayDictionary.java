package dictionary;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedArrayDictionary<K, V> implements Dictionary<K, V> {

    private final Comparator<? super K> cmp;
    private static final int DEF_CAPACITY = 16;
    private int size;
    private Entry<K,V>[] data;

    // Constructor mit Comparator-Parameter
    @SuppressWarnings("unchecked")
    public SortedArrayDictionary(Comparator<? super K> c){
        size = 0;
        data = new Entry[DEF_CAPACITY];
        if(c == null)
            cmp = (x,y) -> ((Comparable<? super K>) x).compareTo(y);
        else
            cmp = c;
    }

    // Default-Constructor
    @SuppressWarnings("unchecked")
    public SortedArrayDictionary() {
        size = 0;
        data = new Entry[DEF_CAPACITY];
        // Natural Order als Default-Wert:
        cmp = (x,y) -> ((Comparable<? super K>) x).compareTo(y);
    }



    @Override
    public V insert(K key, V value) {
        int i = searchKey(key);

        // Vorhandener Eintrag wird überschrieben:
        if (i != -1) {
            V r = data[i].getValue();
            data[i].setValue(value);
            return r;
        }

        // Neuer Eintrag:
        if (data.length == size){
            data = Arrays.copyOf(data, 2*size);
        }
        int j = size-1;

        //while (j >= 0 && key.compareTo(data[j].key < 0){
        while (j >= 0 && cmp.compare(key, data[j].getKey()) < 0){
            data[j+1] = data[j];
            j--;
        }
        data[j+1] = new Entry<K,V>(key,value);
        size++;
        return null;

    }

    private int searchKey(K key){
        int li = 0;
        int re = size -1;

        while(re >= li) {
            int m = (li + re)/2;
            if (cmp.compare(key, data[m].getKey()) < 0)
            //if (key.compareTo(data[m].key) < 0)
                re = m - 1;
            else if (cmp.compare(key, data[m].getKey()) > 0)
            //else if (key.compareTo(data[m].key) > 0)
                li = m + 1;
            else
                return m; // key gefunden
        }
        return -1; // key nicht gefunden
    }

    @Override
    public V search(K key) {
        int i = searchKey(key);
        if (i >= 0)
            return data[i].getValue();
        else
            return null;

    }

    @Override
    public V remove(K key) {
        int i = searchKey(key);
        if (i == -1)
            return null;
        // Datensatz loeschen und Lücke schliessen
        V r = data[i].getValue();
        for (int j = i; j < size-1; j++)
            data[j] = data[j+1];
        data[--size] = null;
        return r;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new SortedArrayIterator();
    }

    private class SortedArrayIterator implements Iterator<Entry<K, V>> {
        private int position = -1;

        @Override
        public boolean hasNext() {
            return ((position+1) < data.length && data[position+1] != null );
        }

        @Override
        public Entry<K, V> next() {
            if(!hasNext())
                throw new NoSuchElementException();
            position++;
            return data[position];
        }
    }
}
