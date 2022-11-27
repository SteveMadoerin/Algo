// S. Madoerin
// 27.11.2022
package dictionary;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the Dictionary interface as AVL tree.
 * <p>
 * The entries are ordered using their natural ordering on the keys, 
 * or by a Comparator provided at set creation time, depending on which constructor is used. 
 * <p>
 * An iterator for this dictionary is implemented by using the parent node reference.
 * 
 * @param <K> Key.
 * @param <V> Value.
 */
public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    static private class Node<K, V> {
        private K key;
        private V value;
        private int height; // höhe entsprechenden Teilbaums für ALV Bäume
        private Node<K, V> left;
        private Node<K, V> right;
        private Node<K, V> parent; // Elternzeiger here :)

        private Node(K k, V v) {
            key = k;
            value = v;
            height = 0;
            left = null;
            right = null;
            parent = null; // <- elternz.
        }
    }
    
    private Node<K, V> root = null;
    private int size = 0;
    
    // ...
    @Override
    public V search(K key){
        return searchR(key, root);
    }

    private V searchR(K key, Node<K,V> p){
        if(p == null)
            return null;
        else if(key.compareTo(p.key)<0)
            return searchR(key, p.left);
        else if (key.compareTo(p.key)>0)
            return searchR(key, p.right);
        else
            return p.value;
    }

    private V oldValue; // Rückgabeparameter

    @Override
    public V insert(K key, V value){
        root = insertR(key, value, root);
        if(root != null)
            root.parent = null;
        return oldValue;
    }

    //Rückgabewert: neuer Wert ; Eingabeparameter: alter Baum
    private Node<K,V> insertR(K key, V value, Node<K,V>p){
        if (p == null){
            p = new Node(key, value);
            oldValue = null;
        } else if (key.compareTo(p.key)<0) {
            p.left = insertR(key, value, p.left);
            if (p.left != null)
                p.left.parent = p;
        }else if (key.compareTo(p.key)>0) {
            p.right = insertR(key, value, p.right);
            if (p.right != null)
                p.right.parent = p;
        }else{
            // Schlüssel ist bereits vorhanden
            oldValue = p.value;
            p.value = value;
        }
        p = balance(p); // erweiterung fü AVL-Bäume
        return p;
    }


    @Override
    public V remove(K key){
        root = removeR(key, root);
        if(root != null)
            root.parent = null;
        return oldValue;
    }

    // löschen in binären Suchbäumen
    private Node<K,V> removeR(K key, Node<K,V> p){
        if(p == null){ oldValue = null;
        } else if(key.compareTo(p.key) < 0) {
            p.left = removeR(key, p.left);
            if (p.left != null)
                p.left.parent = p;

        }else if(key.compareTo(p.key) > 0) {
            p.right = removeR(key, p.right);
            if (p.right != null)
                p.right.parent = p;
        }else if(p.left == null || p.right == null){
            //p muss gelöscht werden & hat 1 Kind oder kein Kind:
            oldValue = p.value;
            p = (p.left != null) ? p.left : p.right;
        } else {
            // p muss gelöscht werden und hat zwei Kinder:
            MinEntry<K,V> min = new MinEntry<K,V>();
            p.right = getRemMinR(p.right, min);
            if (p.right != null)
                p.right.parent = p;
            oldValue = p.value;
            p.key = min.key;
            p.value = min.value;
        }
        p = balance(p); // wegen AVL-Baum
        return p;
    }

    // getRemMinR löscht im Baum p den Knoten mit kleinstem Schlüssel
    // und liefert Schlüssel und Daten des gelöschten Knotens über min zurück
    private Node<K,V> getRemMinR(Node<K,V> p, MinEntry<K,V> min){
        assert p != null;
        if (p.left == null){
            min.key = p.key;
            min.value = p.value;
            p = p.right;
        } else {
            p.left = getRemMinR(p.left, min);
            if (p.left != null)
                p.left.parent = p;
        }
        p = balance(p); // wegen AVL-Baum
        return p;
    }

    // MinEntry is Hilfsdatentyp für Rückgabe-Parameter min
    // von getRemMinR
    private static class MinEntry<K, V>{
        private K key;
        private V value;
    }

    //impl. von leftMostDescendant
    private Node<K,V> leftMostDescendant(Node<K,V> p){
        assert p != null;
        while (p.left != null)
            p = p.left;
        return p;
    }

    //impl. von parentOfLeftMostAncestor
    private Node<K,V>parentOfLeftMostAncestor(Node<K,V>p){
        assert p!= null;
        while(p.parent != null&& p.parent.right == p)
            p=p.parent;
        return p.parent; // possibly null
    }

    @Override
    public int size(){
        return this.size;
    }

    @Override
    public Iterator<Entry<K,V>> iterator() {
        return new BinaryTreeIterator();
    }
    // Iterator :::::
    private class BinaryTreeIterator implements Iterator<Entry<K,V>>{
        private Node<K,V> p = leftMostDescendant(root);

        @Override
        public boolean hasNext(){
            return p != null;
        }
        @Override
        public Entry<K,V> next(){
            Entry<K,V> current = new Entry<K,V>(p.key, p.value);

            if(p != null){
                if(p.right != null){
                    p = leftMostDescendant(p.right);
                } else {
                    p = parentOfLeftMostAncestor(p);
                }
            }
            return current;
        }
    }

    // erweiterung für ALV-Bäume

    private int getHeight(Node<K,V> p){
        if(p == null)
            return -1; // leerer Teilbaum
        else
            return p.height;
    }

    private int getBalance(Node<K,V>p){
        if(p == null)
            return 0;
        else
            return getHeight(p.right)-getHeight(p.left);
    }

    private Node<K,V>balance(Node<K,V>p){
        if(p==null)
            return null;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1; // hoehe aktualisieren
        // Fall A
        if(getBalance(p) == -2){
            // Fall A1
            if(getBalance(p.left)<=0)
                p= rotateRight(p);
            // Fall A2
            else
                p = rotateLeftRight(p);
        }
        // Fall B
        else if(getBalance(p)== +2){
            // Fall B1
            if(getBalance(p.right)>=0)
                p = rotateLeft(p);
            // Fall B2
            else
                p = rotateRightLeft(p);
        }
        return p;
    }

    private Node<K,V> rotateRight(Node<K,V> p){
        assert p.left != null;
        Node<K,V> q = p.left;
        p.left = q.right;
        q.right = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    // ***************** // ü //
    private Node<K,V> rotateLeft(Node<K,V> p){
        assert p.right != null;
        Node<K,V> q = p.right;
        p.right = q.left;
        if(p.right != null)
            p.right.parent = p;
        q.left = p;
        if(q.left != null)
            q.left.parent = q;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    private Node <K,V> rotateLeftRight(Node<K,V> p){
        assert p.left != null;
        p.left = rotateLeft(p.left);
        if(p.left != null){
            p.left.parent = p;
        }
        return rotateRight(p);
    }

    private Node<K,V> rotateRightLeft(Node<K,V> p){
        assert p.right != null;
        p.right = rotateRight(p.right);
        if(p.right != null){
            p.right.parent = p;
        }
        return rotateLeft(p);
    }

	/**
	 * Pretty prints the tree
	 */
	public void prettyPrint() {
        printR(0, root);
    }

    private void printR(int level, Node<K, V> p) {
        printLevel(level);
        if (p == null) {
            System.out.println("#");
        } else {
            System.out.println(p.key + " " + p.value + "^" + ((p.parent == null) ? "null" : p.parent.key));
            if (p.left != null || p.right != null) {
                printR(level + 1, p.left);
                printR(level + 1, p.right);
            }
        }
    }

    private static void printLevel(int level) {
        if (level == 0) {
            return;
        }
        for (int i = 0; i < level - 1; i++) {
            System.out.print("   ");
        }
        System.out.print("|__");
    }
}
