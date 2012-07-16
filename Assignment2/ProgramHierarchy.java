/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {    
    
    private static final int BOX_WIDTH = 150;
    private static final int BOX_HEIGHT = 50;
    private static final int VERT_SPACING = 50;
    private static final int HOR_SPACING = 50;
    
    public void run() {
        /* You fill this in. */
        double box1X = (getWidth()-BOX_WIDTH)/2;
        double box1Y = (getHeight() - 2*BOX_HEIGHT - VERT_SPACING)/2;
        
        double box3X = box1X;
        double box3Y = box1Y + BOX_HEIGHT + VERT_SPACING;
        
        double box2X = box3X - BOX_WIDTH - HOR_SPACING;
        double box2Y = box3Y;
        
        double box4X = box3X + BOX_WIDTH + HOR_SPACING;
        double box4Y = box3Y;
        
        drawBox(box1X, box1Y, "Program");
        drawBox(box2X, box2Y, "GraphicsProgram");
        drawBox(box3X, box3Y, "ConsoleProgram");
        drawBox(box4X, box4Y, "DialogProgram");
        
        double startLineX = box1X + BOX_WIDTH/2;
        double startLineY = box1Y + BOX_HEIGHT;
        
        add(new GLine(startLineX, startLineY, box2X + BOX_WIDTH/2, box2Y));
        add(new GLine(startLineX, startLineY, box3X + BOX_WIDTH/2, box3Y));
        add(new GLine(startLineX, startLineY, box4X + BOX_WIDTH/2, box4Y));
        
    }
    
    private void drawBox(double x, double y, String text)
    {
        add(new GRect(x,y,BOX_WIDTH,BOX_HEIGHT));
        GLabel label = new GLabel(text);
        double labelX = x + (BOX_WIDTH - label.getWidth())/2;
        double labelY = y + (BOX_HEIGHT/2) + label.getAscent()/2;
        label.setLocation(labelX, labelY);
        add(label);
    }
}

