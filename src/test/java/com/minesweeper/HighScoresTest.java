package com.minesweeper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.File;

public class HighScoresTest extends TestCase {

    /**
     * Tests the eligible method of the HighScores class.
     * Checks if score eligibility is properly checked.
     * @throws Exception
     */
    public void testHighScoresEligible () throws Exception {
        // Clean up the data file
        testCleanup ();
		String filename = "./testData.txt";
        // Test with a max of zero
        HighScores scores = new HighScores ( filename, 0 );
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
    public void testHighScoresInsert () throws Exception {
        // Clean up the data file
        String filename = "./testData.txt";
        testCleanup ();
        // Test that we can add to new board
        HighScores scores = new HighScores ( filename, 2 );
        assertEquals ( "insert ( 10 ):", 10, scores.insert ( 10, "Test #01" ).score );
        // Test that we can add another one
        assertEquals ( "insert ( 90 ):", 90, scores.insert ( 90, "Test #02" ).score );
        scores.insert ( 5, "Test #03" );
        // Clean up the data file
        testCleanup ();
    }

    /**
     * Tests the insert method and scores array HighScores class.
     * Tests to make sure that results that are slower than the top 10 high scores
     * do not get added into the data list. Results that are faster should still be added.
     * @throws Exception
     */
    public void testHighScoresBoundaries() throws Exception {
        //Create a new high score file
		String filename2 = "./testData2.txt";
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
     * Deletes the data file created for testing.
     */
    public void testCleanup () {
		String filename = "./testData.txt";
        new File ( filename ).delete ();
    }

}
