package kwhashmap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

        HashTableChain dict = new HashTableChain(31, 0.8);
        Dictionary d = new Dictionary(dict);
        d.buildDictionary();
        d.wordCorrections();
        
        dict.calcStats();
        dict.printStats();
  
        System.out.println("=======================");
        
        HashTableChain dict1 = new HashTableChain(31, 3.0);
        Dictionary d1 = new Dictionary(dict1);
        d1.buildDictionary();
        d1.wordCorrections();
        
        dict1.calcStats();
        dict1.printStats();
        
    }
}
    
 class Dictionary {
     
    HashTableChain dict; 
    public Dictionary(HashTableChain dict) {
        this.dict = dict;
    }
    
    public HashTableChain buildDictionary() {
        // read the dictionary and populate the hash table
        
        // dictionary file
        File dictionaryFileName = new File("./src/kwhashmap/dictionary.txt").getAbsoluteFile();
        // varaible to hold current line
        String sCurrentLine;
        try {
            BufferedReader br;
            br = new BufferedReader(new FileReader(dictionaryFileName));
            while ((sCurrentLine = br.readLine()) != null) {
                String word = sCurrentLine.trim().toLowerCase();
                this.dict.put(word, word);
            }
        } catch (IOException e) {
            System.out.println("Exception" + e);
        }
        return this.dict;
    }
     
    public void wordCorrections() {
        // input file
        File inputFileName = new File("./src/kwhashmap/input.txt").getAbsoluteFile();
        
        ArrayList al = new ArrayList();
        
        // string a to z as string and array for use in rule 1
        String aToz = "abcdefghijklmnopqrstuvwxyz";
        String[] aTozArray = aToz.split("");
        
        // variable to hold line numbers of the input file
        int lineNumber = 0;
        // read input and generate the non matching string replacements and check if it exists in the dictionary
        
        // read input file and process each line
        try {
            String sCurrentLine;
            BufferedReader ipr;
            ipr = new BufferedReader(new FileReader(inputFileName));
            while ((sCurrentLine = ipr.readLine()) != null) {
                lineNumber += 1;
                if (this.dict.get(sCurrentLine) != null) {
                    // the word exists
                } else {
                    // word does not exist
                    al.clear();
                    System.out.print(sCurrentLine + ", " + lineNumber + " :");
                    // rule 1: change each letter from a to z
                    String tempStr;
                    String[] strArray;
                    for (int i = 0; i < sCurrentLine.length(); i++) {
                        strArray = sCurrentLine.split("");
                        for (int j = 0; j < aToz.length(); j++) {
                            strArray[i] = aTozArray[j];
                            tempStr = "";
                            for (String str : strArray) {
                                tempStr += str;
                            }
                            al.add(tempStr);
                        }
                    }

                    // rule 2: swap adjscent letters
                    String tempSwapStr;
                    for (int i = 0; i < sCurrentLine.length() - 1; i++) {
                        strArray = sCurrentLine.split("");
                        tempSwapStr = strArray[i];
                        strArray[i] = strArray[i + 1];
                        strArray[i + 1] = tempSwapStr;
                        tempStr = "";
                        for (String str : strArray) {
                            tempStr += str;
                        }
                        al.add(tempStr);
                    }

                    // rule 3: remove each letter
                    for (int i = 0; i < sCurrentLine.length(); i++) {
                        strArray = sCurrentLine.split("");
                        strArray[i] = "";
                        tempStr = "";
                        for (String str : strArray) {
                            tempStr += str;
                        }
                        al.add(tempStr);
                    }
                    
                    // check which of the generated replacements exists in the dictionary
                    // and print them as replacements
                    for (int i = 0; i < al.size(); i++) {
                        if (this.dict.get(al.get(i)) != null) {
                            System.out.print(al.get(i));
                            System.out.print(", ");
                        }
                    }
                    System.out.println("");
                }
            }
        } catch (IOException e) {
            System.out.println("Exception" + e);
        }
    }
}
