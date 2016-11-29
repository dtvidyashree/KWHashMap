
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author survi
 */
public class SpellChecker {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    HashTableChain hm = new HashTableChain();
    
    System.out.println(hm.isEmpty());
    //System.out.println(hm.size());
    //hm.put(0,"a");
//    hm.put(2,"b");
//    hm.put(10,"z");
//    hm.put(5,"f");
//    hm.put(4,"d");
//    System.out.println(hm.isEmpty());
//    
      System.out.println(hm.get(7));
//    System.out.println(hm.size());

		try {

			String sCurrentLine;

			BufferedReader br;
      br = new BufferedReader(new FileReader("C:\\Users\\vidyashree\\Desktop\\input.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
  } 
}