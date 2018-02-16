package com.minesweeper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DisplayTest extends TestCase {

    /**
     * Tests the Display class.
     * Tests if the display is correctly initialized for the smileys
     * @throws Exception
     */
    public void testDisplayInitial () throws Exception {
        // Initialize the class
        Display display = new Display ( 1 );
        // Test the initial state of the smiley class
        assertEquals ( "Display ( 1 )", 1, display.current );
    }

}
