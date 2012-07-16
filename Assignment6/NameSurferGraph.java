/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
    implements NameSurferConstants, ComponentListener {
    
    private ArrayList<NameSurferEntry> drawList = new ArrayList<NameSurferEntry>();
    private static final int bottomLabelOffset = 3;
    private static final int topBottomOffset = 15;
    private double sectionWidth;

    /**
    * Creates a new NameSurferGraph object that displays the data.
    */
    public NameSurferGraph() {
        addComponentListener(this);
        //     You fill in the rest //
    }
    
    /**
    * Clears the list of name surfer entries stored inside this class.
    */
    public void clear() {
        //     You fill this in //
        drawList.clear();
        update();
    }
    
    /* Method: addEntry(entry) */
    /**
    * Adds a new NameSurferEntry to the list of entries on the display.
    * Note that this method does not actually draw the graph, but
    * simply stores the entry; the graph is drawn by calling update.
    */
    public void addEntry(NameSurferEntry entry) {
        // You fill this in //
        drawList.add(entry);
        update();
    }
    
    
    
    /**
    * Updates the display image by deleting all the graphical objects
    * from the canvas and then reassembling the display according to
    * the list of entries. Your application must call update after
    * calling either clear or addEntry; update is also called whenever
    * the size of the canvas changes.
    */
    public void update() {
        //     You fill this in //
        removeAll();
        drawGrid();
        
        for (int i=0; i<drawList.size(); i++)
        {
            drawEntry(drawList.get(i), getEntryColor(i));
        }
    }
    
    private void drawGrid()
    {
        double topY = 0;
        double bottomY = getHeight();
        double rightX = getWidth();
        sectionWidth = (double)getWidth()/NDECADES;
        
        //The vertical grids
        for (int i=1; i<NDECADES; i++)
        {
            double x = i * sectionWidth;
            add(new GLine(x,topY,x,bottomY));
        }
        
        //The x axis labels
        add(new GLabel("1900"), bottomLabelOffset, bottomY-bottomLabelOffset);
        int temp = 10;
        for (int i=1; i<10; i++)
        {
            add(new GLabel("19" + temp), i*sectionWidth + bottomLabelOffset, bottomY-bottomLabelOffset);
            temp += 10;
        }
        add(new GLabel("2000"), 10*sectionWidth + bottomLabelOffset, bottomY-bottomLabelOffset);
        
        //Top and bottom margins
        add(new GLine(0, topBottomOffset, rightX, topBottomOffset));
        add(new GLine(0, bottomY-topBottomOffset, rightX, bottomY-topBottomOffset));
    }
    
    private void drawEntry(NameSurferEntry e, Color c)
    {
        double prevX = -1;
        double prevY = -1;
        String name = e.getName();
        
        for (int i=0; i<NDECADES; i++)
        {
            int currentRank = e.getRank(i);
            double x = sectionWidth*i;
            double y = getYFromRank(currentRank);
            
            //Line
            if (prevX != -1)
            {
                GLine line = new GLine(prevX, prevY, x, y);
                line.setColor(c);
                add(line);
            }
            
            //Label
            String currentName;
            if (currentRank == 0)
            {
                currentName = name + " *";
            }
            else
            {
                currentName = name+ " " + currentRank;
            }
            GLabel label = new GLabel(currentName);
            label.setColor(c);
            add(label, x+bottomLabelOffset, y-bottomLabelOffset);
            
            prevX = x;
            prevY = y;
        }
    }
    
    private double getYFromRank(int rank)
    {
        if (rank == 0)
        {
            return (getHeight() - topBottomOffset);
        }
        
        double rankDivision = (double)(getHeight() - 2*topBottomOffset)/MAX_RANK;
        return (topBottomOffset + (rank-1)*rankDivision);
    }
    
    private Color getEntryColor(int i)
    {
        i = i%4;
        switch (i)
        {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.RED;
            case 2:
                return Color.BLUE;
            default:
                return Color.MAGENTA;
        }
    }
    
    
    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) { }
    public void componentMoved(ComponentEvent e) { }
    public void componentResized(ComponentEvent e) { update(); }
    public void componentShown(ComponentEvent e) { }
}
