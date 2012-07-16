/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.io.*;
import java.util.*;

public class HangmanLexicon {
    
    private BufferedReader br;
    private ArrayList<String> dict;
    
    public HangmanLexicon()
    {
        dict = new ArrayList<String>();
        br = openFileReader("ShorterLexicon.txt");

        try
        {
            while (true)
            {
                String line = br.readLine();
                if (line == null) break;
                dict.add(line);
            }
            br.close();
        } catch (IOException e) {

        }
        
    }
    
    private BufferedReader openFileReader(String filename)
    {
        BufferedReader temp = null;
        try
        {
            temp = new BufferedReader(new FileReader(filename));
        } catch (IOException e) {

        }
        return temp;
    }

/** Returns the number of words in the lexicon. */
    public int getWordCount() {
        return dict.size();
    }

/** Returns the word at the specified index. */
    public String getWord(int index) {
        return dict.get(index);
    };
}
