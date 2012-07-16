/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import java.awt.*;

public class HangmanCanvas extends GCanvas {
    private GLine scaffold;
    private GLine beam;
    private GLine rope;
    private GOval head;
    private GLine body;
    
    private int count;
    
    private GLabel currentStatus;
    private GLabel tries;
    

/** Resets the display so that only the scaffold appears */
    public void reset() {
        /* You fill this in */
        count = 8;
        setBackground(Color.WHITE);
        removeAll();
        initLabels();
        drawScaffold();
    }

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
    public void displayWord(String word) {
        /* You fill this in */
        currentStatus.setLabel(word);
    }

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
    public void noteIncorrectGuess(char letter) {
        /* You fill this in */
        
        tries.setLabel(tries.getLabel()+letter);
        
        count--;
        switch (count)
        {
            case 7:
                drawHead();
                break;
            case 6:
                drawBody();
                break;
            case 5:
                drawLeftArm();
                break;
            case 4:
                drawRightArm();
                break;
            case 3:
                drawLeftLeg();
                break;
            case 2:
                drawLeftFoot();
                break;
            case 1:
                drawRightLeg();
                break;
            case 0:
                drawRightFoot();
                setBackground(Color.RED);
                break;
        }
    }
    
    private void initLabels()
    {
        currentStatus = new GLabel("");
        add(currentStatus,0,getHeight()-currentStatus.getHeight()*2);
        
        tries = new GLabel("");
        add(tries,0,getHeight()-currentStatus.getHeight());
    }
    
    private void drawScaffold()
    {
        double x = (getWidth()/2)-BEAM_LENGTH;
        double y = (getHeight()-SCAFFOLD_HEIGHT)*0.1;
        
        add(scaffold = new GLine(x,y,x,y+SCAFFOLD_HEIGHT));
        
        add(beam = new GLine(x,y,x+BEAM_LENGTH,y));
        
        add(rope = new GLine(x+BEAM_LENGTH,y, x+BEAM_LENGTH, y+ROPE_LENGTH));
    }
    
    private void drawHead()
    {
        GPoint p = rope.getEndPoint();
        double x = p.getX()-HEAD_RADIUS;
        double y = p.getY();
        
        add(head = new GOval(x,y,2*HEAD_RADIUS,2*HEAD_RADIUS));
    }
    
    private void drawBody()
    {
        GPoint p = rope.getEndPoint();
        double x = p.getX();
        double y = p.getY() + 2*HEAD_RADIUS;
        add(body = new GLine(x,y,x,y+BODY_LENGTH));
    }
    
    private void drawLeftArm()
    {
        GPoint p = body.getStartPoint();
        double x = p.getX();
        double y = p.getY()+ARM_OFFSET_FROM_HEAD;
        
        add(new GLine(x,y,x-UPPER_ARM_LENGTH,y));
        add(new GLine(x-UPPER_ARM_LENGTH,y,x-UPPER_ARM_LENGTH,y+LOWER_ARM_LENGTH));
    }
    
    private void drawRightArm()
    {
        GPoint p = body.getStartPoint();
        double x = p.getX();
        double y = p.getY()+ARM_OFFSET_FROM_HEAD;
        
        add(new GLine(x,y,x+UPPER_ARM_LENGTH,y));
        add(new GLine(x+UPPER_ARM_LENGTH,y,x+UPPER_ARM_LENGTH,y+LOWER_ARM_LENGTH));
    }
    
    private void drawLeftLeg()
    {
        GPoint p = body.getEndPoint();
        double y = p.getY();
        double x = p.getX();
        
        add(new GLine(x,y,x-HIP_WIDTH,y));
        add(new GLine(x-HIP_WIDTH,y,x-HIP_WIDTH,y+LEG_LENGTH));
    }
    
    private void drawLeftFoot()
    {
        GPoint p = body.getEndPoint();
        double y = p.getY();
        double x = p.getX();
        
        add(new GLine(x-HIP_WIDTH,y+LEG_LENGTH,x-HIP_WIDTH-FOOT_LENGTH,y+LEG_LENGTH));
    }
    
    private void drawRightLeg()
    {
        GPoint p = body.getEndPoint();
        double y = p.getY();
        double x = p.getX();
        
        add(new GLine(x,y,x+HIP_WIDTH,y));
        add(new GLine(x+HIP_WIDTH,y,x+HIP_WIDTH,y+LEG_LENGTH));
    }
    
    private void drawRightFoot()
    {
        GPoint p = body.getEndPoint();
        double y = p.getY();
        double x = p.getX();
        
        add(new GLine(x+HIP_WIDTH,y+LEG_LENGTH,x+HIP_WIDTH+FOOT_LENGTH,y+LEG_LENGTH));
    }

/* Constants for the simple version of the picture (in pixels) */
    private static final int SCAFFOLD_HEIGHT = 360;
    private static final int BEAM_LENGTH = 144;
    private static final int ROPE_LENGTH = 18;
    private static final int HEAD_RADIUS = 36;
    private static final int BODY_LENGTH = 144;
    private static final int ARM_OFFSET_FROM_HEAD = 28;
    private static final int UPPER_ARM_LENGTH = 72;
    private static final int LOWER_ARM_LENGTH = 44;
    private static final int HIP_WIDTH = 36;
    private static final int LEG_LENGTH = 108;
    private static final int FOOT_LENGTH = 28;

}
