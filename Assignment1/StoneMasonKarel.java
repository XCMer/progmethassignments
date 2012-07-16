/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

    // You fill in this part
    public void run()
    {
        fillCurrentRow();
        while (frontIsClear())
        {
            gotoNextColumn();
            fillCurrentRow();
        }
    }
    
    private void fillCurrentRow()
    {
        turnLeft();
        placeBeeperIfNotPresent();
        while (frontIsClear())
        {
            move();
            placeBeeperIfNotPresent();
        }
        returnToBottom();
    }
    
    private void returnToBottom()
    {
        turnAround();
        while (frontIsClear())
        {
            move();
        }
        turnLeft();
    }
    
    private void placeBeeperIfNotPresent()
    {
        if (noBeepersPresent())
        {
            putBeeper();
        }
    }
    
    private void gotoNextColumn()
    {
        move();
        move();
        move();
        move();
    }

}
