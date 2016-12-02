package kwhashmap;

import java.util.Iterator;
import java.util.LinkedList;

public class HashTableChain<K, V> implements KWHashMap<K, V> {

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

    //the table
    private LinkedList<Entry<K, V>>[] table;
    //the no.of keys inserted should be increamented
    public int numkeys = 0;
    //the initial capacity of table
    public int capacity = 31;
    //maximum load factor
    public double loadThreshold = 5.0;
    public int collisions = 0;
    public double avgChainLength = 0.0;
    public int maxChainLength = 0;
    
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
        Entry t;
        for(int i = 0; i < table[index].size(); i++) {
            t = table[index].get(i);
            if (t.getKey().equals(key)) {
                return (V) t.getValue();
            }
        }
        return null;
    }

    public void rehash() {
        this.collisions = 0;
        this.numkeys = 0;
        LinkedList<Entry<K, V>>[] oldTable;
        oldTable = this.table;
        this.capacity = (this.capacity * 2) + 1;
        this.table = new LinkedList[this.capacity];
        Entry<K, V> t;
        for(int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null) {
                for(int j = 0; j < oldTable[i].size(); j++) {
                t = oldTable[i].get(j);
                this.put(t.getKey(), t.getValue());
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
            if ((this.numkeys) > (this.loadThreshold * this.capacity)) {
                rehash();
            }
            return (V) val;
        } 
        else {
            this.collisions += 1;
            for(int i = 0; i < table[index].size(); i++) {
                Entry t = this.table[index].get(i);
                if (t.getKey().equals(key)) {
                    val = (V) t.setvalue(value);
                    return val;
                }
            }
            this.table[index].add(new Entry(key, value));
            
            // if load is more than threshold rehash into a new table with more capacity
            this.numkeys += 1;
            
            if ((this.numkeys) > (this.loadThreshold * this.table.length)) {
                rehash();
            }
            return value;
        }
    }

    public void calcStats() {
        double total = 0.0;
        double sum = 0.0;
        int max = 0;
        for (LinkedList<Entry<K, V>> ll : this.table) {
            if (ll != null && ll.size() > 0) {

                total += 1;
                sum += ll.size();
                if (ll.size() > max) {
                    max = ll.size();
                }
            }
        }
        this.avgChainLength = sum / total;
        this.maxChainLength = max;
    }

    public void printStats() {
        System.out.println("no_collisons: " + this.collisions);
        System.out.println("average_chain_length: " + this.avgChainLength);
        System.out.println("max_chain_length: " + this.maxChainLength);
        System.out.println("load_factor: " + this.loadThreshold);
    }

    public int size() {
        int count = 0;
        Iterator<Entry<K, V>> ir;
        Entry<K, V> t;
        for (LinkedList<Entry<K, V>> ll : this.table) {
            if (ll != null) {
                ir = ll.listIterator(0);
                while (ir.hasNext()) {
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
