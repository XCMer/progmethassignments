/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
    
    private static final int N_TURNS = 8;
    
    public static void main(String[] args)
    {
        new Hangman().start(args);
    }
    
    public void init()
    {
        canvas = new HangmanCanvas();
        add(canvas);
    }
    
    public void run() {
        /* You fill this in */
        while(true)
        {
            initializeGame();
            playGame();
            println("PRESS ENTER TO PLAY AGAIN!!!");
            readLine();
        }
        
    }
    
    /*
     * Get current word. Initialize the guess array. Set letters remaining to
     * be guessed and the current turn.
     */
    private void initializeGame()
    {
        currentWord = lexicon.getWord(rgen.nextInt(lexicon.getWordCount()));
        
        currentWordState = new char[currentWord.length()];
        for (int i=0; i<currentWordState.length; i++)
        {
            currentWordState[i] = '-';
        }
        
        lettersRemaining = currentWordState.length;
        currentTurn = N_TURNS;
        
        canvas.reset();
        canvas.displayWord(currentStateString());
        
        println("Welcome to Hangman.");
        printCurrentState();
        printGuessesLeft();
    }
    
    private void playGame()
    {
        while (true)
        {
            char guess = getUserGuess();
            if (checkAndUpdateGuess(guess))
            {
                println("Correct guess.");
                if (lettersRemaining == 0)
                {
                    println("You win!");
                    break;
                }
            }
            else
            {
                println("Guess '" + guess + "' is incorrect.");
                currentTurn--;
                canvas.noteIncorrectGuess(guess);
                if (currentTurn == 0)
                {
                    println("You lose!");
                    println("The correct word was: " + currentWord);
                    break;
                }
            }
            canvas.displayWord(currentStateString());
            printCurrentState();
            printGuessesLeft();
        }
    }
    
    private void printCurrentState()
    {
        String guessedSoFar = currentStateString(); 
        println("The word now looks like this: " + guessedSoFar);
    }
    
    private void printGuessesLeft()
    {
        println("You have " + currentTurn + " guesses left.");
    }
    
    private char getUserGuess()
    {
        String guess;
        while (true)
        {
            guess = readLine("Your guess: ");
            if ( (guess.length() == 1) && (Character.isLetter(guess.charAt(0))) )
                return Character.toUpperCase(guess.charAt(0));
            else
                println("Please enter a single letter.");
        }
    }
    
    private boolean checkAndUpdateGuess(char guess)
    {
        boolean correctGuess = false;
        for (int i=0; i<currentWord.length(); i++)
        {
            if (guess == currentWord.charAt(i))
            {
                lettersRemaining--;
                correctGuess = true;
                currentWordState[i] = guess;
            }
        }
        
        return correctGuess;
    }
    
    private String currentStateString()
    {
        String s = "";
        for (int i=0; i<currentWordState.length; i++)
        {
            s += currentWordState[i];
        }
        return s;
    }
    
    
    
    private HangmanLexicon lexicon = new HangmanLexicon();
    private RandomGenerator rgen = new RandomGenerator();
    private HangmanCanvas canvas;
    
    private int currentTurn;
    
    private String currentWord;
    private char[] currentWordState;
    
    private int lettersRemaining;
}
