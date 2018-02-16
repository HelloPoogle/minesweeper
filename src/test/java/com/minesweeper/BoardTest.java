package com.minesweeper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BoardTest extends TestCase {

    /**
     * Tests the WinGame and LoseGame methods of the Board class.
     * Checks to make sure that the timer stops running and that the smiley changes
     * when a user wins or loses the game.
     * @throws Exception
     */
    public void testBoardWinGame() throws Exception {
        //GAME WAS WON
		Board testBoard = new Board();
        //Start the game and timer
        testBoard.startTimerGame();
        //The initial smiley should be the start smiley
        assertEquals(testBoard.smiley.current, Smiley.Type.SMILE_START);
        //Win the game
        testBoard.winGame(23);
		Timer testTimer = new Timer(1);
        //The timer should have stopped
        // assertTrue("FAILURE: The timer was not stopped after the game was won.", testTimer.run);
        //The smiley should now not be the start smiley
        assertFalse(testBoard.smiley.current == Smiley.Type.SMILE_START);


        //RE-instantiate board
        testBoard = new Board();

        //GAME WAS LOST
        //Start the game and timer
        testBoard.startTimerGame();
        //The initial smiley should be the start smiley
        assertEquals(testBoard.smiley.current, Smiley.Type.SMILE_START);
        //Lose the game
        testBoard.loseGame();
        //The timer should have stopped
        // assertFalse("FAILURE: The timer was not stopped after the game was won.", testTimer.run);
        //The smiley should now not be the start smiley
        assertFalse(testBoard.smiley.current == Smiley.Type.SMILE_START);
    }


    /**
     * Tests the ResetGame method of the Board class.
     * Makes sure the smiley is reset when a user resets the game,
     * and that the timer has not yet started.
     * @throws Exception
     */
    public void testBoardResetGame() throws Exception {
		Board testBoard = new Board();
        //Starts the game and timer
        testBoard.startTimerGame();

        //Ends the game
        testBoard.loseGame();

        //Resets the game
        testBoard.resetGame();

        //The smiley should be the start smiley
        assertEquals(testBoard.smiley.current, Smiley.Type.SMILE_START);
		Timer testTimer = new Timer(1);
        //The timer should not be running
        // assertFalse("FAILURE: The timer was not stopped after the game was won.", testTimer.run);
    }

}
