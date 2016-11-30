package kwhashmap;

import java.util.Iterator;
import java.util.LinkedList;

public class HashTableChain<K, V> implements KWHashMap<K, V> {

    //the table
    private LinkedList<Entry<K, V>>[] table;
    //the no.of keys inserted should be increamented
    private int numkeys;
    //the initial capacity of table
    private int capacity = 31;
    //maximum load factor
    private double loadThreshold = 8.0;

    private static class Entry<K, V> {

        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setvalue(V val) {
            V oldvalue = value;
            value = val;
            return oldvalue;
        }
    }

    public HashTableChain() {
        this.table = new LinkedList[capacity];
    }

    public HashTableChain(int capacity, double load) {
        this.table = new LinkedList[capacity];
        this.loadThreshold = load;
        this.capacity = capacity;
    }

    public int hash(K key, int length) {
        int index = key.hashCode() % length;
        if (index < 0) {
            index += length;
        }
        return index;
    }

    @Override
    public V get(K key) {
        int index = hash(key, this.table.length);
        if (this.table[index] == null) {
            return null;
        }
        Iterator<Entry<K, V>> ir = this.table[index].listIterator(0);
        while (ir.hasNext()) {
            Entry t = ir.next();
            if (t.key.equals(key)) {
                return (V) t.getValue();
            }
        }
        return null;
    }
    
    private void rehash() {
        Iterator<Entry<K, V>> ir;
        LinkedList<Entry<K, V>>[] oldTable;
        oldTable = this.table;
        this.table = new LinkedList[capacity * 2 + 1];
        
        Entry<K,V> t;
                
        for (LinkedList<Entry<K, V>> table1 : this.table) {
            if (table1 != null) {
                ir = table1.listIterator(0);
                while(ir.hasNext()) {
                    t = ir.next();
                    this.put(t.key, t.value);
                }
            }
        }
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key, this.table.length);
        V val = null;
        if (this.table[index] == null) {
            // if no linked list add the key value to the linked list
            LinkedList ll = new LinkedList();
            ll.add(new Entry(key, value));
            this.table[index] = ll;
            this.numkeys = this.numkeys + 1;
            
            // if load is more than threshold rehash into a new table with more capacity
            if ((this.numkeys / this.capacity) > this.loadThreshold) {
                rehash();
            }
            return (V) val;
        } else {
            Iterator<Entry<K, V>> ir = this.table[index].listIterator(0);
            while (ir.hasNext()) {
                Entry t = ir.next();
                // if key already exist update the value and return the old value
                if (t.key.equals(key)) {
                    val = (V) t.value;
                    t.value = value;
                    return val;
                }
            }
            // if key does not exist then add the key, value to the linked list and return null
            this.table[index].add(new Entry(key, value));
            // if load is more than threshold rehash into a new table with more capacity
            if ((this.numkeys / this.capacity) > this.loadThreshold) {
                rehash();
            }
            return value;
        }
    }

    public int size() {
        int count = 0;
        Iterator<Entry<K, V>> ir;
        Entry<K,V> t;
        for(LinkedList<Entry<K, V>> ll : this.table) {
            if (ll != null) {
                ir = ll.listIterator(0);
                while(ir.hasNext()) {
                    t = ir.next();
                    if (t.key != null) {
                        count = count + 1;
                    }
                }
            }
        }
        return count;
    }
    
    public boolean isEmpty() {
        return (this.size() < 1);
    }
}