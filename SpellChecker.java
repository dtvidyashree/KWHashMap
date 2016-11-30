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
        // input file
        File inputFileName = new File("./src/kwhashmap/input.txt").getAbsoluteFile();
        
        // dictionary file
        File dictionaryFileName = new File("./src/kwhashmap/dictionary.txt").getAbsoluteFile();
        
        // dictionary hash table object
        HashTableChain dict = new HashTableChain();

        System.out.println("Size before reading the dictionary");
        System.out.println(dict.size());
        
        // read the dictionary and populate the hash table
        String sCurrentLine;
        try {
            BufferedReader br;
            br = new BufferedReader(new FileReader(dictionaryFileName));
            while ((sCurrentLine = br.readLine()) != null) {
                dict.put(sCurrentLine, sCurrentLine);
            }
        } catch (IOException e) {
            System.out.println("Exception" + e);
        }

        System.out.println("Size after reading the dictionary");
        System.out.println(dict.size());
        
        ArrayList al = new ArrayList();
        
        // string a to z as string and array for use in rule 1
        String aToz = "abcdefghijklmnopqrstuvwxyz";
        String[] aTozArray = aToz.split("");
        
        // read input and generate the non matching string replacements and check if it exists in the dictionary
        try {
            BufferedReader ipr;
            ipr = new BufferedReader(new FileReader(inputFileName));
            while ((sCurrentLine = ipr.readLine()) != null) {
                if (dict.get(sCurrentLine) != null) {
                    // the word exists
                    System.out.println("In the dictionary =========" + sCurrentLine);
                } else {
                    // word does not exist
                    al.clear();
                    System.out.println("Not in the dictionary =========== " + sCurrentLine);
                    // rule 1: change each letter from a to z
                    //System.out.println("Rule 1 ========================");
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
                            //System.out.println(tempStr);
                        }
                    }

                    // rule 2: swap adjscent letters
                    //System.out.println("Rule 2 =========================");
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
                        //System.out.println(tempStr);
                    }

                    // rule 3: remove each letter
                    //System.out.println("Rule 3 =========================");
                    for (int i = 0; i < sCurrentLine.length(); i++) {
                        strArray = sCurrentLine.split("");
                        strArray[i] = "";
                        tempStr = "";
                        for (String str : strArray) {
                            tempStr += str;
                        }
                        al.add(tempStr);
                        //System.out.println(tempStr);
                    }
                    
                    // check which generated replacements exists in the dictionary
                    System.out.println("Valid replacements for " + sCurrentLine);
                    for (int i = 0; i < al.size(); i++) {
                        if (dict.get(al.get(i)) != null) {
                            System.out.println(al.get(i));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Exception" + e);
        }
    }
}
