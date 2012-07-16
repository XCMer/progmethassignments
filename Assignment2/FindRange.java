/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
    public void run() {
        /* You fill this in */
        println("This program prints the largest and the smallest numbers.");
        int largest, smallest;
        largest = smallest = 0;
        
        while (true)
        {
            int input = readInt("? ");
            if (input == 0) break;
            if (largest == 0)
            {
                largest = smallest = input;
            }
            else
            {
                if (input > largest) largest = input;
                if (input < smallest) smallest = input;
            }
        }
        
        if (largest == 0)
        {
            println("You didn't provide an input.");
        }
        else
        {
            println("Largest: " + largest + ", Smallest: " + smallest);
        }
    }
}

