/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
    
    private static final double INCH_TO_PIXEL = 72;
    
    public void run() {
        /* You fill this in. */
        drawCenteredCircle(1, Color.RED);
        drawCenteredCircle(0.65, Color.WHITE);
        drawCenteredCircle(0.3, Color.RED);
    }
    
    //Radius in inches
    private void drawCenteredCircle(double radius, Color color)
    {
        GOval current;
        current = new GOval(getWidth()/2-radius*INCH_TO_PIXEL, getHeight()/2-radius*INCH_TO_PIXEL, 2*radius*INCH_TO_PIXEL, 2*radius*INCH_TO_PIXEL);
        current.setColor(color);
        current.setFilled(true);
        add(current);
    }
}
