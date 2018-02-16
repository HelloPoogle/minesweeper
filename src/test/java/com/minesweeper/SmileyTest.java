package com.minesweeper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SmileyTest extends TestCase {

    /**
     * Tests the Smiley type of the Smiley class.
     * Test if smiley type changes are occurring properly.
     * @throws Exception
     */
    public void testSmileyType () throws Exception {
        // Test the initial state of the smiley class
        Smiley smiley = new Smiley ( Smiley.Type.SMILE_START );
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
    public void testSmileyIndex () throws Exception {
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
    public void testFilePath () throws Exception {
        // Test to see if the filepath is correctly returned
        assertEquals ( "filepath ( 0 )", "/images/headSmileStart.gif", Smiley.filepath ( 0 ) );
        assertEquals ( "filepath ( 1 )", "/images/headDead.gif", Smiley.filepath ( 1 ) );
        assertEquals ( "filepath ( 2 )", "/images/headGlasses.gif", Smiley.filepath ( 2 ) );
        assertEquals ( "filepath ( 3 )", "/images/headO.gif", Smiley.filepath ( 3 ) );
        assertEquals ( "filepath ( 4 )", "/images/headSmilePressed.gif", Smiley.filepath ( 4 ) );
        assertEquals ( "filepath ( 5 )", null, Smiley.filepath ( 5 ) );
    }

}
