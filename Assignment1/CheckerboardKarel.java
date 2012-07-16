/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

    // You fill in this part
    public void run()
    {
        fillTwoRows();
        repositionToLeftSecond();
        while (leftIsClear())
        {
            moveUp();
            fillTwoRows();
            repositionToLeftSecond();
        }
    }

    private void fillTwoRows()
    {
        putBeeper();
        while (frontIsClear())
        {
            putSecondRowBeeper();
            if (frontIsClear())
            {
                putFirstRowBeeper();
            }
        }
    }
    
    //pre: one square to the left of the target
    //post: at the target facing right
    private void putSecondRowBeeper()
    {
        move();
        if (leftIsClear())
        {
            turnLeft();
            move();
            putBeeper();
            turnAround();
            move();
            turnLeft();
        }
    }
    
    //pre: one square to the left of target
    //post: at the target facing right
    private void putFirstRowBeeper()
    {
        move();
        putBeeper();
    }
    
    //pre: first row end facing right
    //post: second row start facing right (second row may not exist)
    private void repositionToLeftSecond()
    {
        gotoSecondRowIfPossible();
        turnAround();
        while (frontIsClear())
        {
            move();
        }
        turnAround();
        
    }
    
    private void gotoSecondRowIfPossible()
    {
        if (leftIsClear())
        {
            turnLeft();
            move();
            turnRight();
        }
    }
    
    //pre: left facing right and guaranteed row at the top
    //post: one row to the top facing right
    private void moveUp()
    {
        turnLeft();
        move();
        turnRight();
    }
}
