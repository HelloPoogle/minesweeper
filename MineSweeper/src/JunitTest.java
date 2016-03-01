import org.junit.Test;
import javax.swing.*;
import java.io.File;
import static org.junit.Assert.*;

/**
 * JunitTest.java - Tests the minesweeper project's classes using test cases upon
 * instances of the classes.
 * Tests various aspects including boundary cases, array checking, icon checking, game state checking, etc.
 * Since the Grid class had all private methods and instance members, these were set to protected for the
 * purposes of testing, which did not change the program in any significant way but allowed us to proceed
 * with the testing, so it was assumed to be acceptable.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #02 - MineSweeper
 * @category    Tests
 * @author      Rafael Grigorian
 * @author      Marek Rybakiewicz
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class JunitTest {
    /**
     * Instantiates a coordinate, which is a collection of random int data members x and y.
     * x and y must be in the range 0-9
     */
    protected Coordinate testCoordinate = new Coordinate();

    /**
     * Instantiates a board, which holds all elements of the gui.
     * Displays the GUI and has the game at an un-started state, and handles mouse clicks.
     */
    protected Board testBoard = new Board();

    /**
     * Instantiates a grid, which acts as the 10x10 grid of mines.
     * Handles mouse clicks and interacts with the board.
     */
    protected Grid testGrid = new Grid(testBoard);

    /**
     * Instantiates a timer, which keeps track of the time that has passed
     * since the game has started.
     */
    protected Timer testTimer = new Timer(1);

    /**
     * Instantiates a display, which handles output of GUI elements,
     * specifically the timer or mine count, to the screen.
     */
    protected Display display;

    /**
     * Instantiates a HighScores, which handles score processing when a user
     * wins a game. Also handles file manipulation.
     */
    protected HighScores scores;

    /**
     * Alternate files to be used for testing the HighScores class.
     */
    protected String filename = "./testData.txt";
    protected String filename2 = "./testData2.txt";


    /**
     * Instantiates a score, which holds information about the value of the score
     * and the name of the player.
     */
    protected Score score;

    /**
     * Instantiates a starting smiley, that is displayed before the game starts or
     * when it is in progress with no user interaction.
     */
    protected Smiley smiley = new Smiley ( Smiley.Type.SMILE_START );


    /**
     * Tests the Display class.
     * Tests if the display is correctly initialized for the smileys
     * @throws Exception
     */
    @Test
    public void testDisplayInitial () throws Exception{
        // Initialize the class
        display = new Display ( 1 );
        // Test the initial state of the smiley class
        assertEquals ( "Display ( 1 )", 1, display.current );
    }

    /**
     * Tests the eligible method of the HighScores class.
     * Checks if score eligibility is properly checked.
     * @throws Exception
     */
    @Test
    public void testHighScoresEligible () throws Exception{
        // Clean up the data file
        testCleanup ();
        // Test with a max of zero
        scores = new HighScores ( filename, 0 );
        assertEquals ( "eligible ( 10 ):", false, scores.eligible ( 10 ) );
        // Test with a max of two
        scores = new HighScores ( filename, 2 );
        assertEquals ( "eligible ( 10 ):", true, scores.eligible ( 10 ) );
        // Successfully add two scores
        scores.insert ( 10, "Test #01" );
        scores.insert ( 90, "Test #02" );
        // Check that we can add new one with lower score
        assertEquals ( "eligible ( 80 )", true, scores.eligible ( 5 ) );
        // Clean up the data file
        testCleanup ();
    }

    /**
     * Tests the insert method of the HighScores class.
     * Checks if scores and names are properly being handled.
     * @throws Exception
     */
    @Test
    public void testHighScoresInsert () throws Exception{
        // Clean up the data file
        testCleanup ();
        // Test that we can add to new board
        scores = new HighScores ( filename, 2 );
        assertEquals ( "insert ( 10 ):", 10, scores.insert ( 10, "Test #01" ).score );
        // Test that we can add another one
        assertEquals ( "insert ( 90 ):", 90, scores.insert ( 90, "Test #02" ).score );
        scores.insert ( 5, "Test #03" );
        // Clean up the data file
        testCleanup ();
    }

    /**
     * Deletes the data file created for testing.
     */
    public void testCleanup () {
        new File ( filename ).delete ();
    }


    /**
     * Tests the insert method and scores array HighScores class.
     * Tests to make sure that results that are slower than the top 10 high scores
     * do not get added into the data list. Results that are faster should still be added.
     * @throws Exception
     */
    @Test
    public void testHighScoresBoundaries() throws Exception {
        //Create a new high score file
        HighScores testHighScores = new HighScores(filename2, 10);

        int i = 0;
        while (i < 10) {
            //Add 10 scores to the file
            testHighScores.insert(30 + i, "Test");
            i++;
        }

        //Attempt to add a faulty value
        testHighScores.insert(57, "Test");

        i = 0;
        while (i < 10){
            //Check if every score is less than or equal to 39
            assertTrue(testHighScores.scores[i].score <= 39);
            i++;
        }

        //Attempt to add a valid value
        testHighScores.insert(9, "Test");

        i = 0;
        //Initially assume false
        boolean check = false;
        while (i < 10){
            //Checks if the valid value was properly added
            if(testHighScores.scores[i].score == 9){
                check = true;
            }
            i++;
        }
        //Report whether or not the valid value was properly added
        assertTrue("FAILURE: A valid value was not added to the score list", check);

        //Deletes the file after processing is complete
        new File ( filename2 ).delete ();
    }


    /**
     * Tests if strings are properly being created and displayed for the High Scores.
     * @throws Exception
     */
    @Test
    public void testStringify () throws Exception{
        // Create a score object
        score = new Score ( 10, "Test #01" );
        // Test that the stringify function works correctly
        assertEquals ( "score.stringify ():", "10\t\tTest #01", score.stringify () );
    }


    /**
     * Tests the Smiley type of the Smiley class.
     * Test if smiley type changes are occurring properly.
     * @throws Exception
     */
    @Test
    public void testSmileyType () throws Exception{
        // Test the initial state of the smiley class
        assertEquals ( "Smiley ( SMILE_START )", Smiley.Type.SMILE_START, smiley.current );
        // Change it to a different state
        smiley.change ( Smiley.Type.SMILE_O );
        // Test that we detected the change
        assertEquals ( "change ( SMILE_O )", Smiley.Type.SMILE_O, smiley.current );
    }

    /**
     * Tests the Smiley index of the Smiley class.
     * Tests if the smiley array is properly being allocated and holds the
     * correct smiley types in their respective positions.
     * @throws Exception
     */
    @Test
    public void testSmileyIndex () throws Exception{
        // Test that we get the right indexes for the enums of Type in Smiley class
        assertEquals ( "index ( SMILE_GLASSES )", 2, Smiley.index ( Smiley.Type.SMILE_GLASSES ) );
        assertEquals ( "index ( SMILE_DEAD )", 1, Smiley.index ( Smiley.Type.SMILE_DEAD ) );
        assertEquals ( "index ( SMILE_START )", 0, Smiley.index ( Smiley.Type.SMILE_START ) );
        assertEquals ( "index ( SMILE_START )", 0, Smiley.index ( Smiley.Type.SMILE_START ) );
        assertEquals ( "index ( SMILE_O )", 3, Smiley.index ( Smiley.Type.SMILE_O ) );
        assertEquals ( "index ( SMILE_PRESSED )", 4, Smiley.index ( Smiley.Type.SMILE_PRESSED ) );
    }

    /**
     * Tests to see if the gif file paths are properly processed and that
     * the proper images are being read in for the smiley.
     * @throws Exception
     */
    @Test
    public void testFilePath () throws Exception {
        // Test to see if the filepath is correctly returned
        assertEquals ( "filepath ( 0 )", "Images/headSmileStart.gif", Smiley.filepath ( 0 ) );
        assertEquals ( "filepath ( 1 )", "Images/headDead.gif", Smiley.filepath ( 1 ) );
        assertEquals ( "filepath ( 2 )", "Images/headGlasses.gif", Smiley.filepath ( 2 ) );
        assertEquals ( "filepath ( 3 )", "Images/headO.gif", Smiley.filepath ( 3 ) );
        assertEquals ( "filepath ( 4 )", "Images/headSmilePressed.gif", Smiley.filepath ( 4 ) );
        assertEquals ( "filepath ( 5 )", null, Smiley.filepath ( 5 ) );
    }

    /**
     * Tests the getX and GetY method of the Coordinate class.
     * Makes sure the random x and y coordinates generated by the Coordinate class are within the proper boundaries.
     * The coordinates must have a value between 0 and 9.
     * @throws Exception
     */
    @Test
    public void testCoordinateGetXY() throws Exception {
        assertTrue("FAILURE: The generated x coordinate is either less than 0 or greater than 9. The value is: " + testCoordinate.getX(),
                ((testCoordinate.getX() >= 0) && (testCoordinate.getX() < 10)) );

        assertTrue("FAILURE: The generated y coordinate is either less than 0 or greater than 9. The value is: " + testCoordinate.getY(),
                ((testCoordinate.getY() >= 0) && (testCoordinate.getY() < 10)) );
    }


    /**
     * Tests the HighScores data members of the Board class.
     * Checks to make sure that the high score file data.txt is always available to write into.
     * If the file does not exist, it should be created upon instantiating the Board class.
     * @throws Exception
     */
    @Test
    public void testBoardHighScores() throws Exception {
        File f = new File("data.txt");
        assertTrue("FAILURE: The data.txt file does not exist within the directory and was not created.", f.isFile() );
    }


    /**
     * Tests the WinGame and LoseGame methods of the Board class.
     * Checks to make sure that the timer stops running and that the smiley changes
     * when a user wins or loses the game.
     * @throws Exception
     */
    @Test
    public void testBoardWinGame() throws Exception {
        //GAME WAS WON
        //Start the game and timer
        testBoard.startTimerGame();
        //The initial smiley should be the start smiley
        assertEquals(testBoard.smiley.current, Smiley.Type.SMILE_START);
        //Win the game
        testBoard.winGame(23);
        //The timer should have stopped
        assertFalse("FAILURE: The timer was not stopped after the game was won.", testTimer.run);
        //The smiley should now not be the start smiley
        assertNotEquals(testBoard.smiley.current, Smiley.Type.SMILE_START);


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
        assertFalse("FAILURE: The timer was not stopped after the game was won.", testTimer.run);
        //The smiley should now not be the start smiley
        assertNotEquals(testBoard.smiley.current, Smiley.Type.SMILE_START);
    }


    /**
     * Tests the ResetGame method of the Board class.
     * Makes sure the smiley is reset when a user resets the game,
     * and that the timer has not yet started.
     * @throws Exception
     */
    @Test
    public void testBoardResetGame() throws Exception {
        //Starts the game and timer
        testBoard.startTimerGame();

        //Ends the game
        testBoard.loseGame();

        //Resets the game
        testBoard.resetGame();

        //The smiley should be the start smiley
        assertEquals(testBoard.smiley.current, Smiley.Type.SMILE_START);

        //The timer should not be running
        assertFalse("FAILURE: The timer was not stopped after the game was won.", testTimer.run);
    }


    /**
     * Tests the InitializeGridMines method of the Grid class.
     * Ensures that all mine locations are unique and not overlapping.
     */
    @Test
    public void testGridInitializeGridMines() throws Exception {
        JLabel l = new JLabel();
        testGrid.initializeGridMines(l);

        int i;
        boolean check = false;
        //For all mines
        for (i = 0; i < 10; i++){
            //Get the coordinate of a mine
            int x = testGrid.mineLocations[i].getX();
            int y = testGrid.mineLocations[i].getY();

            int j;
            //For all mines
            for(j = 0; j < 10; j++){
                //If the location is not the same
                if ( i != j ) {
                    //If the coordinates match
                    if (testGrid.mineLocations[j].getX() == x && testGrid.mineLocations[j].getY() == y) {
                        //Duplicate found
                        check = true;
                    }
                }
            }
        }
        assertFalse("FAILURE: Duplicate mine locations found.", check);

    }


    /**
     * Tests the InitializeGridLabels method of the Grid class.
     * Checks if all starting icons are the same to the first icon (aka the same for the entire grid).
     * @throws Exception
     */
    @Test
    public void testGridInitializeGridLabels() throws Exception {
        int i, j;

        //One icon from the grid, should be the same everywhere
        Icon testIcon = testGrid.gameGrid[0][0].getIcon();

        //For the entire game grid
        for(i = 0; i < 10; i++){
            for(j = 0; j < 10; j++){
                //Check if the icon is the same as the start icon
                assertEquals(testIcon, testGrid.gameGrid[i][j].getIcon());
            }
        }
    }


    /**
     * Tests the SetRevealedIcon method of the Grid class.
     * Check to make sure that values outside of the corresponding icon ranges (-1..8)
     * do not change the original icon
     * @throws Exception
     */
    @Test
    public void testGridSetRevealedIcon() throws Exception {
        JLabel l;
        Icon oldIcon;

        //Get the first label on the grid
        l = testGrid.gameGrid[0][0];
        //Get the icon at that label
        oldIcon = testGrid.gameGrid[0][0].getIcon();

        //Attempt to set an icon with invalid range
        testGrid.setRevealedIcon(l, -20);
        assertTrue("FAILURE: The icon of the label was changed even though the value was out of range. The old icon was " + oldIcon +
                ", the new icon is " + testGrid.gameGrid[0][0].getIcon(), oldIcon == testGrid.gameGrid[0][0].getIcon());

        //Attempt to set an icon with valid range
        testGrid.setRevealedIcon(l, 5);
        assertFalse("FAILURE: The icon of the label was changed even though the value was out of range. The old icon was " + oldIcon +
                ", the new icon is " + testGrid.gameGrid[0][0].getIcon(), oldIcon == testGrid.gameGrid[0][0].getIcon());


    }


}
//End of JunitTest Class