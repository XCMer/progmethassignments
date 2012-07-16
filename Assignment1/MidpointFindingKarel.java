/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

    // You fill in this part
    public void run()
    {
        placeDiagonalBeepers();
        gotoRightBottom();
        findAndPlaceMidpoint();
        gotoLeftBottom();
        removeDiagonalBeepers();
    }
    
    private void placeDiagonalBeepers()
    {
        putBeeper();
        while (frontIsClear())
        {
            move();
            turnLeft();
            move();
            turnRight();
            putBeeper();
        }
    }
    
    private void gotoRightBottom()
    {
        turnRight();
        while (frontIsClear())
        {
            move();
        }
        turnRight();
    }
    
    private void findAndPlaceMidpoint()
    {
        while (noBeepersPresent())
        {
            if (facingWest())
            {
                move();
                turnRight();
            }
            else
            {
                move();
                turnLeft();
            }
        }
        
        if (facingWest())
        {
            turnLeft();
        }
        else
        {
            turnAround();
        }
        
        while (frontIsClear())
        {
            move();
        }
        putBeeper();
    }
    
    private void gotoLeftBottom()
    {
        turnRight();
        while (frontIsClear())
        {
            move();
        }
        turnAround();
    }
    
    private void removeDiagonalBeepers()
    {
        pickBeeper();
        while (frontIsClear())
        {
            move();
            turnLeft();
            move();
            turnRight();
            pickBeeper();
        }
    }

}
