
import java.util.Iterator;
import java.util.LinkedList;

public class HashTableChain<K, V> implements KWHashMap<K, V> {

  //the table
  private LinkedList<Entry<K, V>>[] table;
  //the no.of keys inserted should be increamented
  private int numkeys;
  //the initial capacity of table
  private static final int Capacity = 1;
  //maximum load factor
  private static final double Load_Threshold = 3.0;

  private static class Entry<K, V> {

    private K key;
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
    table = new LinkedList[Capacity];
  }

  @Override
  public V get(K key) {
    System.out.println("hello world");
    int index = key.hashCode() % table.length;
    if (index < 0) {
      index += table.length;
    } else {
      if (table[index] == null) {
        return null;
      }
    }
    Iterator<Entry<K,V>> ir = table[index].listIterator();
    while (ir.hasNext()) {
      Entry t = ir.next();
      if (t.key.equals(key)) {
        return (V) t.getValue();
      }
    }
    return null;
  }

  public boolean isEmpty() {
    return table.length < 0;
  }
}

//        Object cur = table[i];
//        while (cur != null) {
//          if (key.equals(table[i].key)) {
//            
//          }
//        }
//
//      }
//    return null;
//  }
//}
//  public HashTableChain() {
//    table = new LinkedList[Capacity];
//  }
//
//  public int hash(K key) {
//    int index = key.hashCode() % table.length;
//    if (index < 0) {
//      index += table.length;
//    } else {
//      if (table[index] == null) {
//        return 0;
//      }
//    }
//    return 0;
//  }
//
//  @Override
//  public V get(K key) {
//    int index = hash(key);
//      System.out.println("helo");
//    for (Entry<K, V> nextitem : table[index]) {
//      if (nextitem.key.equals(key)) {
//        return nextitem.value;
//      }
//    }
//    return null;
//  }
//
//  @Override
//  public boolean isEmpty() {
//    return (table.length < 0);
//  }
//
//  @Override
//  public V put(K key, V value) {
//    int index = hash(key);
//    for (Entry<K, V> nextitem : table[index]) {
//      //if successful replace with new value 
//      if (nextitem.key.equals(key)) {
//        //if the key is same replace value for the key  
//        V oldval = nextitem.value;
//        nextitem.setvalue(value);
//        return oldval;
//      }
//    }
//    table[index].addFirst(new Entry(key, value));
//    numkeys++;
//    System.out.println("num of keys");
//    System.out.println(numkeys);
//    if (numkeys > (Load_Threshold * table.length)) {
//      System.out.println("rehashing");
//      rehash();
//    }
//    return null;
//  }
//
//  @Override
//  public V remove(K key) {
//    int index = hash(key);
//
//    for (Entry<K, V> nextitem : table[index]) {
//      //if search successful delete value 
//      if (nextitem.key.equals(key)) {
//        //if the key is same replace value for the key  
//        V oldval = nextitem.value;
//        nextitem.value = null;
//        numkeys--;
//        return oldval;
//      }
//    }
//    return null;
//  }
//
//  @Override
//  public int size() {
//    int count = 0;
//    for (int i = 0; i < table.length; i++) {
//      count++;
//    }
//    return count;
//  }
//
//  public void rehash() {
//    //saving reference of table in oldtable
//    LinkedList<Entry<K, V>>[] oldtable = table;
//    table = new LinkedList[2 * oldtable.length + 1];
//    numkeys = 0;
//    for(int i = 0; i < oldtable.length; i++) {
//      for (Entry<K, V> nextitem : oldtable[i]) {
//        if (nextitem.key != null) {
//          put(nextitem.key, nextitem.value);
//        }
//      }
//    }
//  }
//}
