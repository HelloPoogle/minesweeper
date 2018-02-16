package com.minesweeper;

import java.util.Random;

/**
 * Grid.java - This class creates the coordinates based on the game grid that are
 * used to store locations of mines. Random integers between 0 and 9 are generated
 * for each coordinate point, and the values of each point are accessed via getter methods.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #02 - MineSweeper
 * @category    Display
 * @author      Rafael Grigorian
 * @author      Marek Rybakiewicz
 * @license     MIT License <LICENSE.md>
 */
public class Coordinate{

    /**
     * Integers that act as the coordinate points used by mines on the game grid.
     * @var     int     x           The x coordinate of a mine
     * @var     int     y           The x coordinate of a mine
     */
    private int x, y;


    /**
     * Constructor that creates a coordinate point bound by the 10x10 grid.
     * Each point is a random number between 0 and 9.
     * @var     Random      rand        Instance of random, allows for random int generation
     */
    Coordinate(){
        //Create a new instance of random
        Random rand = new Random();

        //Give two random coordinates between 0 and 9
        x = rand.nextInt(9);
        y = rand.nextInt(9);
    }
    //End of Coordinate constructor


    /**
     * Returns the x coordinate of a given Coordinate pair.
     * @return      int     x       The x coordinate of a mine
     */
    public int getX(){
        //Provide the x coordinate
        return x;
    }

    /**
     * Returns the y coordinate of a given Coordinate pair.
     * @return      int     y       The y coordinate of a mine
     */
    public int getY() {
        //Provide the y coordinate
        return y;
    }
}
//End of Coordinate class
