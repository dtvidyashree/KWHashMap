package kwhashmap;

interface KWHashMap<K,V> {

  V get (K key);
  boolean isEmpty();    
  V put( K key, V value);
//  V remove(K key);
//  int size();
//  
 
}