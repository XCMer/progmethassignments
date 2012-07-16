/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
    
    public static final int N_ROUNDS = 13;
    
    public static void main(String[] args) {
        new Yahtzee().start(args);
    }
    
    public void run() {
        IODialog dialog = getDialog();
        nPlayers = dialog.readInt("Enter number of players");
        playerNames = new String[nPlayers];
        for (int i = 1; i <= nPlayers; i++) {
            playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
        }
        display = new YahtzeeDisplay(getGCanvas(), playerNames);
        playGame();
    }

    private void playGame() {
        /* You fill this in */
        initializeScores();
        for (int i=0; i<N_ROUNDS; i++)
        {
            playRound();
        }
        
        for (int i=0; i<nPlayers; i++)
        {
            tallyScores(i);
        }
        showWinner();
    }
    
    private void initializeScores()
    {
        playerScores = new int[nPlayers][N_CATEGORIES];
        for (int i=0; i<nPlayers; i++)
        {
            for (int j=0; j<N_CATEGORIES; j++)
            {
                playerScores[i][j] = -1;
            }
        }
    }
    
    private void playRound()
    {
        for (int i=0; i<nPlayers; i++)
        {
            playTurn(i);
        }
    }
    
    private void playTurn(int player)
    {
        display.printMessage(playerNames[player] + "'s turn.");
        int[] diceConfig = getFinalDiceConfig(player);
        
        display.printMessage("Select a category.");
        int turnCategory = getTurnCategory(player);
        scoreCategory(player, diceConfig, turnCategory);
        
    }
    
    private int[] getFinalDiceConfig(int player)
    {
        display.waitForPlayerToClickRoll(player+1);
        int[] diceConfig = rollRandomDice();
        display.displayDice(diceConfig);
        
        for (int i=0; i<2; i++)
        {
            display.printMessage("Reroll the dice if you wish.");
            display.waitForPlayerToSelectDice();
            boolean selected = false;
            
            for (int j=0; j<N_DICE; j++)
            {
                if (display.isDieSelected(j))
                {
                    diceConfig[j] = rgen.nextInt(1,6);
                    selected = true;
                }
            }
            
            if (!selected) break;
            display.displayDice(diceConfig);
        }
        
        return diceConfig;
    }
    
    private int[] rollRandomDice()
    {
        int[] diceConfig = new int[N_DICE];
        for (int i=0; i<N_DICE; i++)
        {
            diceConfig[i] = rgen.nextInt(1,6);
        }

        return diceConfig;
    }
    
    private int getTurnCategory(int player)
    {
        while (true)
        {
            int category = display.waitForPlayerToSelectCategory();
                   
            if (playerScores[player][category-1] != -1)
            {
                display.printMessage("You cannot select the same category again.");
                continue;
            }
            
            return category;
        }
    }
    
    private void scoreCategory(int player, int[] diceConfig, int category)
    {
        int score = 0;
        //boolean checkCategory = YahtzeeMagicStub.checkCategory(diceConfig, category);
        boolean checkCategory = checkCategory(diceConfig, category);
        if (checkCategory)
        {
            if ((category >= ONES) && (category <= SIXES))
            {
                for (int i=0; i<N_DICE; i++)
                {
                    if (diceConfig[i] == category) score += category;
                }
            }
            else if ((category == THREE_OF_A_KIND) || (category == FOUR_OF_A_KIND) || (category == CHANCE))
            {
                for (int i=0; i<N_DICE; i++) score += diceConfig[i];
            }
            else if (category == FULL_HOUSE)
            {
                score = 25;
            }
            else if (category == SMALL_STRAIGHT)
            {
                score = 30;
            }
            else if (category == LARGE_STRAIGHT)
            {
                score = 40;
            }
            else if (category == YAHTZEE)
            {
                score = 50;
            }
        }
        
        playerScores[player][category-1] = score;
        display.updateScorecard(category, player+1, score);
    }
    
    private void tallyScores(int player)
    {
        int upper = 0;
        int lower = 0;
        int bonus = 0;
        int total = 0;
        
        for (int i=ONES-1; i<=SIXES-1; i++)
        {
            upper += playerScores[player][i];
        }
        
        bonus = upper >= 63 ? 35 : 0;
        
        for (int i=THREE_OF_A_KIND-1; i<= CHANCE-1; i++)
        {
            lower += playerScores[player][i];
        }
        
        total = upper + lower + bonus;
        
        playerScores[player][UPPER_SCORE-1] = upper;
        playerScores[player][LOWER_SCORE-1] = lower;
        playerScores[player][UPPER_BONUS-1] = bonus;
        playerScores[player][TOTAL-1] = total;
        
        display.updateScorecard(UPPER_SCORE, player+1, upper);
        display.updateScorecard(LOWER_SCORE, player+1, lower);
        display.updateScorecard(UPPER_BONUS, player+1, bonus);
        display.updateScorecard(TOTAL, player+1, total);
    }
    
    private void showWinner()
    {
        int score = 0;
        int player = 0;
        for (int i=0; i<nPlayers; i++)
        {
            if (playerScores[i][TOTAL-1] > score)
            {
                score = playerScores[i][TOTAL-1];
                player = i;
            }
        }
        
        display.printMessage("The winner is " + playerNames[player] + " with score " + score);
    }
    
    private boolean checkCategory(int[] diceConfig, int category)
    {
        switch (category)
        {
            case CHANCE:
            case ONES:
            case TWOS:
            case THREES:
            case FOURS:
            case FIVES:
            case SIXES:
                return true;
            case THREE_OF_A_KIND:
                return checkThreeOfAKind(diceConfig);
            case FOUR_OF_A_KIND:
                return checkFourOfAKind(diceConfig);
            case FULL_HOUSE:
                return checkFullHouse(diceConfig);
            case SMALL_STRAIGHT:
                return checkSmallStraight(diceConfig);
            case LARGE_STRAIGHT:
                return checkLargeStraight(diceConfig);
            case YAHTZEE:
                return checkYahtzee(diceConfig);
            default:
                return false;
                
        }               
    }
    
    private boolean checkThreeOfAKind(int[] diceConfig)
    {
        int[] temp = getZeroArray();
        for (int i=0; i<diceConfig.length; i++)
            temp[diceConfig[i]-1]++;
        
        for (int i=0; i<temp.length; i++)
        {
            if (temp[i] == 3)
                return true;
        }
        
        return false;
    }
    
    private boolean checkFourOfAKind(int[] diceConfig)
    {
        int[] temp = getZeroArray();
        for (int i=0; i<diceConfig.length; i++)
            temp[diceConfig[i]-1]++;
        
        for (int i=0; i<temp.length; i++)
        {
            if (temp[i] == 4)
                return true;
        }
        
        return false;
    }
    
    private boolean checkFullHouse(int[] diceConfig)
    {
        int[] temp = getZeroArray();
        boolean two = false;
        boolean three = false;
        for (int i=0; i<diceConfig.length; i++)
            temp[diceConfig[i]-1]++;
        
        for (int i=0; i<temp.length; i++)
        {
            if (temp[i] == 3)
            {
                three = true;
            }
            else if (temp[i] == 2)
            {
                two = true;
            }
        }
        
        return (three && two);
    }
    
    private boolean checkSmallStraight(int[] diceConfig)
    {
        int[] temp = getZeroArray();
        for (int i=0; i<diceConfig.length; i++)
            temp[diceConfig[i]-1]++;
        
        boolean condition1 = ((temp[0] == 1)
                && (temp[1] == 1)
                && (temp[2] == 1)
                &&(temp[3] == 1));
        
         boolean condition2 = ((temp[1] == 1)
                && (temp[2] == 1)
                && (temp[3] == 1)
                &&(temp[4] == 1));
         
         return (condition1 || condition2);
    }
    
    private boolean checkLargeStraight(int[] diceConfig)
    {
        int[] temp = getZeroArray();
        for (int i=0; i<diceConfig.length; i++)
            temp[diceConfig[i]-1]++;
        
        for (int i=0; i<temp.length; i++)
        {
            if (temp[i] != 1)
                return false;
        }
        
        return true;
    }
    
    private boolean checkYahtzee(int[] diceConfig)
    {
        int[] temp = getZeroArray();
        for (int i=0; i<diceConfig.length; i++)
            temp[diceConfig[i]-1]++;
        
        for (int i=0; i<temp.length; i++)
        {
            if (temp[i] == N_DICE)
                return true;
        }
        
        return false;
    }
    
    private int[] getZeroArray()
    {
        int[] temp = new int[6];
        for (int i=0; i<6; i++)
            temp[i] = 0;
        return temp;
    }
        
/* Private instance variables */
    private int nPlayers;
    private String[] playerNames;
    private YahtzeeDisplay display;
    private int[][] playerScores;
    private RandomGenerator rgen = new RandomGenerator();

}
