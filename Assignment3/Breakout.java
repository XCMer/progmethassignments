/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
    private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
    private static final int NBRICKS_PER_ROW = 2;

/** Number of rows of bricks */
    private static final int NBRICK_ROWS = 1;

/** Separation between bricks */
    private static final int BRICK_SEP = 4;

/** Width of a brick */
    private static final int BRICK_WIDTH =
      (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
    private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
    private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
    private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
    private static final int NTURNS = 3;
    
    
/*  Instance Variables & Constants */
    private static final int DELAY = 10;
    private GRect paddle;
    private GOval ball;
    private double vx, vy;
    
    private RandomGenerator rgen = RandomGenerator.getInstance();
    
    private int totalBricks = NBRICKS_PER_ROW * NBRICK_ROWS;
    
    private int currentTurn = NTURNS;
    private boolean hitBottom = false;
    private boolean won = false;
    
    private GLabel lifeLeft;
    

/* Method: run() */
/** Runs the Breakout program. */
    public void run() {
        /* You fill this in, along with any subsidiary methods */
        setupGame();
        playGame();
    }
    
    /* Method: setupGame() */
    /** Performs setup for the game */
    private void setupGame()
    {
        setupBricks();
        setupPaddle();
        setupBall();
        setupScore();
        addMouseListeners();
        pause(1000);
    }
    
    private void setupBricks()
    {
        double brickX = (WIDTH - NBRICKS_PER_ROW*BRICK_WIDTH - (NBRICKS_PER_ROW-1)*BRICK_SEP)/2;
        for (int i=0; i<NBRICK_ROWS; i++)
        {
            drawBrickRow(brickX, BRICK_Y_OFFSET+i*(BRICK_HEIGHT+BRICK_SEP), getRowColor(i));
        }
    }
    
    private void drawBrickRow(double x, double y, Color c)
    {
        for (int i=0; i<NBRICKS_PER_ROW; i++)
        {
            GRect brick = new GRect(x,y,BRICK_WIDTH,BRICK_HEIGHT);
            brick.setColor(getBackground());
            brick.setFillColor(c);
            brick.setFilled(true);
            add(brick);
            x += BRICK_WIDTH+BRICK_SEP;
        }
    }
    
    private Color getRowColor(int i)
    {
        i = i%10;
        if (i<2)
        {
            return Color.RED;
        }
        if (i<4)
        {
            return Color.ORANGE;
        }
        if (i<6)
        {
            return Color.YELLOW;
        }
        if (i<8)
        {
            return Color.GREEN;
        }
        return Color.CYAN;
    }
    
    private void setupPaddle()
    {
        paddle = new GRect((WIDTH-PADDLE_WIDTH)/2,HEIGHT-PADDLE_Y_OFFSET-PADDLE_HEIGHT,PADDLE_WIDTH,PADDLE_HEIGHT);
        paddle.setFilled(true);
        add(paddle);
    }
    
    public void mouseMoved(MouseEvent e)
    {
        if ( (e.getX() >= PADDLE_WIDTH/2) && (e.getX() <= (WIDTH - PADDLE_WIDTH/2)) )
        {
            paddle.setLocation(e.getX()-PADDLE_WIDTH/2, paddle.getY());
        }
    }
    
    private void setupBall()
    {
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5)) vx = -vx;
        vy = 3.0;
        
        ball = new GOval((WIDTH-BALL_RADIUS)/2, (HEIGHT-BALL_RADIUS)/2, 2*BALL_RADIUS, 2*BALL_RADIUS);
        ball.setFilled(true);
        add(ball);
    }
    
    private void setupScore()
    {
        lifeLeft = new GLabel("Life left: " + currentTurn);
        add(lifeLeft,0,lifeLeft.getAscent());
    }
    
    private void playGame()
    {
        while (isGameRunning())
        {
            moveBall();
            checkForBallCollision();
            pause(DELAY);
        }
        showGameOver();
    }
    
    private void moveBall()
    {
        ball.move(vx, vy);
    }
    
    private void checkForBallCollision()
    {
        //Check for wall collision
        checkWallCollision();
        GObject collider = getCollidingObject();
        
        if (collider == paddle)
        {
            bounceOffPaddle();
        }
        else if (collider != null)
        {
            totalBricks--;
            remove(collider);
            vy = -vy;
        }
    }
    
    private void checkWallCollision()
    {
        double x = ball.getX();
        double y = ball.getY();  
        
        double difference; //Should be negative outside bounds
        difference = WIDTH - (x+2*BALL_RADIUS); //Right edge
        if (difference <= 0)
        {
            vx = -vx;
            ball.move(2*difference,0);
        }
        
        difference = HEIGHT - (y+2*BALL_RADIUS); //Bottom edge
        if (difference <= 0)
        {
            hitBottom = true;
        }
        
        if (x < 0) //Left edge
        {
            vx = -vx;
            ball.move(-2*x, 0);
        }
        
        if (y < 0) //Top edge
        {
            vy = -vy;
            ball.move(0,-2*y);
        }
    }
    
    private GObject getCollidingObject()
    {
        double x = ball.getX();
        double y = ball.getY();
        GObject obj = null;
        
        if ((obj = getElementAt(x,y)) != null)
        {
            return obj;
        }
        else if ((obj = getElementAt(x+2*BALL_RADIUS,y)) != null)
        {
            return obj;
        }
        else if ((obj = getElementAt(x,y+2*BALL_RADIUS)) != null)
        {
            return obj;
        }
        else
        {
            return getElementAt(x+2*BALL_RADIUS,y+2*BALL_RADIUS);
        }
        
    }
    
    private void bounceOffPaddle()
    {
        double dy = (ball.getY() + 2*BALL_RADIUS) - paddle.getY();
        ball.move(0,-dy);
        
        vy = -vy;
    }
    
    private boolean isGameRunning()
    {
        if (hitBottom)
        {
            currentTurn--;
            updateLives();
            hitBottom = false;
            if (currentTurn == 0)
            {
                return false;
            }
            else
            {
                remove(ball);
                setupBall();
                pause(1000);
                return true;
            }
        }
        if (totalBricks == 0)
        {
            won = true;
            return false;
        }
        return true;
    }
    
    private void showGameOver()
    {
        removeAll();
        GLabel lab;
        if (won)
        {
            lab = new GLabel("Game Over: YOU WON!");
        }
        else
        {
            lab = new GLabel("Game Over: YOU LOST!");
        }
        
        lab.setLocation((getWidth() - lab.getWidth())/2, (getHeight() - lab.getAscent())/2);
        add(lab);
    }
    
    private void updateLives()
    {
        lifeLeft.setLabel("Life left: " + currentTurn);
    }

}
