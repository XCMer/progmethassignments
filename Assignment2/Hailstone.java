/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
    public void run() {
        /* You fill this in */
        int number = readInt("Enter a number: ");
        int steps = 0;
        while (number != 1)
        {
            steps++;
            if (number%2 == 0)
            {
                int temp = number/2;
                println(number + " is even, so I take half: " + temp);
                number = temp;
            }
            else
            {
                int temp = 3*number + 1;
                println(number + " is odd, so I take 3n+1: " + temp);
                number = temp;
            }
        }
        println("The process took " + steps + " steps to finish.");
    }
}

