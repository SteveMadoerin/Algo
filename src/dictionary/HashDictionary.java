package dictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HashDictionary<K,V> implements Dictionary<K,V>{
    private LinkedList<Entry<K, V>> tab[];
    private int size;
    private static final int LOAD_FACTOR = 2;

    // calculate the primes :)
    private boolean isPrime(int number) {
        for (int i = 2; i < number; ++i){
            if(number % i == 0)
                return false;
        }
        return true;
    }

    // hash code holen
    private int extractHashCode(K key){
        int adr = key.hashCode();
        if(adr < 0)
            adr = -adr;
        return adr % tab.length;
    }

    @SuppressWarnings("unchecked")
    public HashDictionary(int cap){
        if(!isPrime(cap)){
            System.out.println("Capacity is not Prime");
        }
        this.tab = new LinkedList[cap];
    }

    private boolean isLoadOkay(){
        return (this.size / this.tab.length > LOAD_FACTOR) ? true : false;
    }

    private int newPrime(int oldPrime){
        int newPrime = oldPrime *2;
        while(!isPrime(newPrime))
            newPrime++;
        return newPrime;
    }

    @SuppressWarnings("unchecked")
    private void doubleCap(){
        List<Entry<K, V>> entries = new ArrayList<>(this.size);
        for(var v : this)
            entries.add(v);
        this.tab = new LinkedList[newPrime(this.tab.length)];
        for(var v : entries)
            this.insert(v.getKey(), v.getValue());

    }

    @Override
    public V insert(K key, V value) {
        int hashCode = extractHashCode(key);
        if(search(key)==null){
            if(isLoadOkay()){
                doubleCap();
                hashCode = extractHashCode(key);
            }
            if(tab[hashCode] == null){
                tab[hashCode] = new LinkedList<Entry<K, V>>();
            }
            tab[hashCode].add(new Entry<K, V>(key, value));
            size++;
        } else {
            for(var entry : tab[hashCode]){
                if(entry.getKey().equals(key)){
                    return entry.setValue(value);
                }
            }
        }
        return null;
    }


/*    @Override
    public V insert(K key, V value) {
        //if(search(key) == null)
        if(adr = searchAdr(key) != -1){
            oldValue = tab[adr].value;
            tab[adr].value = value;
            return oldValue;
        }
        // Neueintrag:
        j = 0;
        do {
            adr = (h(k) + s(j,k)) % m;
            j++;
        } while(tab[adr] != null && tab[adr]!= key  );
        tab[adr].key = key;
        tab[adr].value = value;
        return null;
    }

    int searchAdr(K key) {
        int j = 0;
        do {
            adr = (h(key) + s(j, key)) % m;
            j++;
        } while (tab[adr] != null && tab[adr].key != key);

        if (tab[adr] != null)
            return adr;
        else
            return -1;
    }*/

    @Override
    public V search(K key) {
        int hashCode = extractHashCode(key);
        if(tab[hashCode] != null){
            for(var entry : tab[hashCode]){
                if(entry.getKey().equals(key))
                    return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        if(search(key)!= null){
            int hashCode = extractHashCode(key);
            for(int i = 0; i < tab[hashCode].size(); i++){
                if(tab[hashCode].get(i).getKey().equals(key)){
                    size--;
                    return tab[hashCode].remove(i).getValue();
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Dictionary.Entry<K,V>>(){
            int tabIdx = 0;
            int listIdx = 0;

            @Override
            public boolean hasNext() {
                if(tabIdx < tab.length){
                    if(tab[tabIdx] == null){
                        tabIdx++;
                        return this.hasNext();
                    }
                    if(listIdx < tab[tabIdx].size())
                        return true;
                }
                return false;
            }

            @Override
            public Entry<K, V> next() {
                Entry<K, V> entry = tab[tabIdx].get(listIdx++);
                if(tab[tabIdx].size()== listIdx){
                    tabIdx++;
                    listIdx = 0;
                }
                return entry;
            }
        };
    }
}
