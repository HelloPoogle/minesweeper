package com.minesweeper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ScoreTest extends TestCase {

    /**
     * Tests if strings are properly being created and displayed for the High Scores.
     * @throws Exception
     */
    public void testStringify () throws Exception{
        // Create a score object
        Score score = new Score ( 10, "Test #01" );
        // Test that the stringify function works correctly
        assertEquals ( "score.stringify ():", "10\t\tTest #01", score.stringify () );
    }

}
