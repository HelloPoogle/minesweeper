package com.minesweeper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import javax.swing.*;

public class GridTest extends TestCase {

    /**
     * Tests the InitializeGridMines method of the Grid class.
     * Ensures that all mine locations are unique and not overlapping.
     */
    public void testGridInitializeGridMines() throws Exception {
        JLabel l = new JLabel();
		Board testBoard = new Board();
		Grid testGrid = new Grid(testBoard);
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
    public void testGridInitializeGridLabels() throws Exception {
        int i, j;
		Board testBoard = new Board();
		Grid testGrid = new Grid(testBoard);
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
    public void testGridSetRevealedIcon() throws Exception {
        JLabel l;
        Icon oldIcon;
		Board testBoard = new Board();
		Grid testGrid = new Grid(testBoard);
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
