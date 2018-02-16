package com.minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;

/**
 * Grid.java - This class holds the actual game grid of 100 labels, as well as its supporting
 * data containers and methods. The grid is initialized as an array of 100 labels that the user
 * can alter with left and right clicks. Labels are revealed or marked until win or lose conditions
 * of the game are met, at which point execution is passed to the Board class. Grid interacts
 * with the Board class for timer starting, flag display, and score calculation as well.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #02 - MineSweeper
 * @category    Display
 * @author      Rafael Grigorian
 * @author      Marek Rybakiewicz
 * @license     MIT License <LICENSE.md>
 */
public class Grid extends JPanel implements MouseListener {
    //BEGINNING OF DATA MEMBERS

    /**
     * 2D array of labels that acts as the game grid upon which all game actions are processed.
     * Stores the icons of each label by proxy.
     * Used for the display as well as calculations.
     * @var     JLabel[][]  gameGrid            The game grid that stores all labels(icons)
     */
    protected JLabel[][] gameGrid;

    /**
     * Array of Coordinates (x,y) that stores locations of mines on the grid.
     * Used for the display as well as calculations.
     * @var     Coordinate[]    mineLocations       Locations of every mine
     */
    protected Coordinate[] mineLocations;


    /**
     * 2D array that acts as a revealed version of gameGrid.
     * Stores the count of how many mines surround a label at a coordinate,
     * or if the label is a mine (stores -1).
     * @var     int[][]     revealedGrid        array storing mine counts for each label
     */
    protected int[][] revealedGrid;

    /**
     * 2D array that holds flag and question mark locations on the grid.
     * 0 = Blank icon.
     * 1 = Flag icon.
     * 2 = Question mark icon.
     * @var     int[][]     flagLocations       array storing locations of markers
     */
    protected int[][] flagLocations;


    /**
     * An instance of the Board class, used for relaying information about score,
     * win/loss state, flag count, timer starting, as well as smiley face manipulation.
     * @var     Board       board       An instance of the board class used to access member methods
     */
    protected Board board;

    //Checker to see if startTimerGame method has already been called
    //-1 = game lost and not yet reset, 0 = not started, 1 = started,

    /**
     * Checks what state the game is currently in.
     * -1 = game is lost and not yet reset.
     *  0 = game has not yet started.
     *  1 = game is started.
     *  @var    int     gameStart       Tracks game state
     */
    private int gameStart;

    /**
     * Holds number of flags that have not yet been placed on the grid.
     * @var     int     flagCount       Tracks number of flags that are yet to be placed
     */
    private int flagCount;


    /**
     * Various ImageIcon's that will be placed upon labels, holding visual information about
     * blank tiles, surrounding mine counts, markers, and bombs.
     * @var     ImageIcon   ...         Icons from images that hold visual game information
     */
    private ImageIcon blankStart;       //Initial label
    private ImageIcon blankPressed;     //Pressed label, no surrounding mines

    private ImageIcon mine1;            //Surrounding number of mines
    private ImageIcon mine2;
    private ImageIcon mine3;
    private ImageIcon mine4;
    private ImageIcon mine5;
    private ImageIcon mine6;
    private ImageIcon mine7;
    private ImageIcon mine8;

    private ImageIcon minePressed;      //Indicates that a user pressed a mine
    private ImageIcon mineRevealed;     //Used at the end game, reveals an unflagged and unpressed mine

    private ImageIcon flag;             //Flag, flags a mine location
    private ImageIcon questionMark;     //Question mark, not sure if a mine is there
    private ImageIcon wrongFlag;        //Game end, reveals if an incorrect flag was placed

    //END OF DATA MEMBERS

    /**
     * Grid constructor that creates the 10x10 game grid, saves an instance of the Board for later use,
     * creates icons from images, intializes the game grid to blank labels, and sets starting conditions.
     * At the end of the constructor, further execution is handled by mouseListeners.
     * @param   Board   board       Instance of the Board class
     */
    Grid( Board board ){
        //Create a new JPanel
        super(new GridLayout(10,10));

        //Create a 10x10 grid of JLabels that will act as the game grid
        gameGrid = new JLabel[10][10];

        //Save an instance of the Board class
        //Used for method access relating to the entire board
        this.board = board;

        //Create all icons from images that will be used on the grid
        initializeLabelIcons();

        //Fill the grid with blank icons initially
        initializeGridLabels();

        //Initialize the array of flags
        flagLocations = new int[10][10];

        //Set the game to not yet started
        gameStart = 0;

        //No tiles have been flagged yet
        flagCount = 10;

    }
    //End of Grid constructor





    //BEGINNING OF INITIALIZATION METHODS

    /**
     * Attempts to initialize all image files needed for the data members.
     * If a file is found, the icon is scaled to appropriate size and placed in the associated data member.
     * If the file is not found, an exception is thrown.
     * @var     Image   img         Attempts to open and store an image file
     * @var     Image   newimg      Scales the corresponding image
     * @return  void
     */
    private void initializeLabelIcons(){
        //Attempt to open a file
        try {
            //Attempt to get the image
            Image img = ImageIO.read(getClass().getResource("/images/blankStart.gif"));
            //Scale the image properly
            Image newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            //Create the icon from the image
            blankStart = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/blankPressed.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            blankPressed = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/bomb1.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine1 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/bomb2.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine2 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/bomb3.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine3 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/bomb4.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine4 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/bomb5.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine5 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/bomb6.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine6 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/bomb7.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine7 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/bomb8.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine8 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/bombPressed.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            minePressed = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/bombRevealed.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mineRevealed = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/flag.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            flag = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/questionStart.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            questionMark = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("/images/wrongFlag.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            wrongFlag = new ImageIcon(newimg);


        }
        //If a file could not be opened, throw an exception
        catch (IOException ex){
            System.out.println("Could not find a file");
        }

    }
    //End of initializeLabelIcons method


    /**
     * Places a blankStart icon at every position of the gameGrid (i.e. a new game).
     * Adds a mouse listener to every icon so that it may be manipulated with the mouse.
     * @return  void
     */
    private void initializeGridLabels(){
            //For the grid of labels
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    //Add a blankStart Icon
                    gameGrid[i][j] = new JLabel(blankStart);

                    //Set the icon to have no border
                    gameGrid[i][j].setBorder(BorderFactory.createEmptyBorder());

                    //Add a mouse listener to the icon
                    gameGrid[i][j].addMouseListener(this);

                    //Add the icon to the grid
                    this.add(gameGrid[i][j]);
                }
            }
    }
    //End of initializeGridLabels method

    /**
     * Initializes the mineLocations with 10 randomly generated mine coordinates.
     * If a generated coordinate is the location of the first user label click, the coordinate is ignored.
     * @var     Coordinate  coordinate  An (X,Y) coordinate that acts as a mine location
     * @param   JLabel      l           Initial clicked label, where a mine should not be
     *                                  placed in order to ensure a game can be played
     * @return  void
     */
    protected void initializeGridMines(JLabel l){
        //Create a new mine array
        mineLocations = new Coordinate[10];

        int i = 0;
        //While 10 coordinates have not been added
        while(i  < 10){
            //Get a new coordinate
            Coordinate coordinate = new Coordinate();

            //Checker to see if the instance is a unique coordinate (not in the mineLocations)
            //0 if unique, 1 if not unique
            int unique = 0;

            int j = 0;
            //If the coordinate is not unique, do not add nor increment
            while(j < i){
                if( (mineLocations[j].getX() == coordinate.getX()) &&
                    (mineLocations[j].getY() == coordinate.getY())    ){
                        //The coordinate is not unique
                        unique = 1;
                }
                //Increment iterator
                j++;
            }

            //If the coordinate is the location of the first user-clicked label, do not add nor increment
            if(gameGrid[coordinate.getX()][coordinate.getY()] == l){
                //The coordinate cannot be added
                unique = 1;
            }

            //If the coordinate is unique
            if(unique != 1) {
                //Add the coordinate as a valid mine location
                mineLocations[i] = coordinate;
                //Increment iterator
                i++;
            }
        }
    }
    //End of initializeGridMines method

    //END OF INITIALIZATION METHODS





    //BEGINNING OF GRID CHECKER METHODS

    /**
     * Takes a label, attempts to find it in the grid, and marks the type of flag on the flag location holder.
     * Then, the gameGrid is checked to make sure the proper flags are displayed in accordance with the location holder.
     * @param   JLabel      l       The label that is to be added to the flag location holder
     * @return  void
     */
    private void setFlagLocations(JLabel l){
        int a, b;   //Iterators

        //For every position on the grid
        for(a = 0; a < 10; a++){
            for(b = 0; b < 10; b++){
                //If the icon is found
                if(l == gameGrid[a][b]){
                    //If the icon is a blankStart
                    if(l.getIcon() == blankStart) {
                        //Display it on the flag location holder
                        flagLocations[a][b] = 0;
                    }
                    //Else if the icon is a flag
                    else if(l.getIcon() == flag) {
                        //Display it on the flag location holder
                        flagLocations[a][b] = 1;
                    }
                    //Else if the icon is a question mark
                    else if(l.getIcon() == questionMark) {
                        //Display it on the flag location holder
                        flagLocations[a][b] = 2;
                    }
                }
            }
        }

        //For every position on the grid
        for(a = 0; a < 10; a++){
            for(b = 0; b < 10; b++){
                //If the location is a flag on the location holder
                if(flagLocations[a][b] == 1) {
                    //Make sure the grid also displays a flag
                    gameGrid[a][b].setIcon(flag);
                }
                //Else if the location is a question mark on the location holder
                else if (flagLocations[a][b] == 2){
                    //Make sure the grid also displays a question mark
                    gameGrid[a][b].setIcon(questionMark);
                }
            }
        }

    }
    //End of setFlagLocations method

    /**
     * Creates a secondary "grid" that holds each labels surrounding mine count, including if the label is a mine.
     * @var     int[][]     revealedGrid        2D array corresponding to grid coordinates, that holds information
     *                                          about surrounding mines at a specified label
     * @return  void
     */
    private void createRevealedGrid(){
        int x, y, z;                        //Indices
        int count = 0;                      //Count surrounding mines
        revealedGrid = new int[10][10];     //Initialize grid

        //For every label
        for(x = 0; x < 10; x++) {
            for (y = 0; y < 10; y++) {
                //For every mine
                for (z = 0; z < 10; z++) {
                    //Get the coordinates of the mine
                    int mineX = mineLocations[z].getX();
                    int mineY = mineLocations[z].getY();

                    //If we are at a mine
                    if ((mineX == x) && (mineY == y)) {
                        //Put a mine is at that position in the grid
                        revealedGrid[x][y] = -1;
                    }

                    //If the current mine's coordinates are adjacent, increment surrounding mine count
                    //Above
                    if (mineX == (x - 1)) {
                        //Left
                        if (mineY == (y-1))
                            count++;
                        //Middle
                        if (mineY == (y))
                            count++;
                        //Right
                        if (mineY == (y + 1))
                            count++;
                    }
                    //Same row
                    if (mineX == x) {
                        //Left
                        if (mineY == (y - 1))
                            count++;
                        //Right
                        if (mineY == (y + 1))
                            count++;
                    }
                    //Under
                    if (mineX == (x + 1)) {
                        //Left
                        if (mineY == (y - 1))
                            count++;
                        //Middle
                        if (mineY == (y))
                            count++;
                        //Right
                        if (mineY == (y + 1))
                            count++;
                    }
                }
                //If we are not at a mine
                if(revealedGrid[x][y] != -1){
                    //Provide the surrounding mine count at that position
                    revealedGrid[x][y] = count;
                }
                //Reset the mine count;
                count = 0;
            }
        }
    }
    //End of createRevealedGrid method

    /**
     * Counts the number of labels in the gameGrid that have been cleared so far and returns the count as an int.
     * @var     int     count       Number of labels in the gameGrid that have been cleared so far
     * @return  int     count
     */
    private int countCleared(){
        int a,b;        //Iterators
        int count = 0;  //Counts the number of labels that have been cleared

        //For every label in the grid
        for(a = 0; a < 10; a++){
            for(b = 0; b < 10; b++){
                //If the label at that position is cleared
                if( (gameGrid[a][b].getIcon() != blankStart) && (gameGrid[a][b].getIcon() != flag) && (gameGrid[a][b].getIcon() != questionMark)) {
                    //Increment number of labels cleared
                    count++;
                }
            }
        }
        //Return number of labels cleared
        return count;
    }
    //End of countCleared method

    //END OF GRID CHECKER METHODS





    //BEGINNING OF END-GAME METHODS

    /**
     * Flags all mine locations after a user has won the game.
     * @return  void
     */
    protected void revealGridWon(){
        int a,b;    //Indices
        int   x;    //Iterator

        //For every mine
        for(x = 0; x < 10; x++){
            //Get the coordinates of a mine
            a = mineLocations[x].getX();
            b = mineLocations[x].getY();

            //Flag it on the gameGrid
            gameGrid[a][b].setIcon(flag);
        }
    }
    //End of revealGridWon method

    /**
     * Reveals every mine on the grid if the user lost the game. If the mine is flagged, it remains flagged.
     * If the user placed a wrong flag, that flag is marked as wrong, while a question mark is left alone.
     * @return void
     */
    private void revealGridLost(){
        int a, b, c;    //Iterators

        //For the entire grid
        for(a = 0; a < 10; a++){
            for(b = 0; b < 10; b++) {
                //For every mine
                for (c = 0; c < 10; c++) {
                    //If the position on the grid is a mine
                    if ((mineLocations[c].getX() == a) && (mineLocations[c].getY() == b)){
                        //If the position is blank, or a question mark
                        if( (gameGrid[a][b].getIcon() == blankStart) || (gameGrid[a][b].getIcon() == questionMark)) {
                            //Reveal the mine on the grid
                            gameGrid[a][b].setIcon(mineRevealed);
                        }
                        //If the position is a flag, do nothing
                    }
                }

                //If the position is a flag
                if((gameGrid[a][b].getIcon() == flag)){
                    //If there is no mine
                    if(revealedGrid[a][b] != -1) {
                        //Reveal it as a wrong flag
                        gameGrid[a][b].setIcon(wrongFlag);
                    }
                }
                //If the position is a question mark, do nothing
            }
        }
    }
    //End of revealGridList method

    //END OF END-GAME METHODS





    //BEGINNING OF REVEAL METHODS

    /**
     * Sets (and reveals) the proper icon at a label based on the surrounding mine count.
     * Sister of all reveal methods
     * @param   JLabel      l           The label of the icon to be set
     * @param   int         count       The mine count which dictates the icon to be set
     */
    protected void setRevealedIcon(JLabel l, int count){
        //Change the icon of the current label based on the number of surrounding mines
        switch(count){
            case -1:
                l.setIcon(minePressed);
                return;
            case 0:
                l.setIcon(blankPressed);
                return;
            case 1:
                l.setIcon(mine1);
                return;
            case 2:
                l.setIcon(mine2);
                return;
            case 3:
                l.setIcon(mine3);
                return;
            case 4:
                l.setIcon(mine4);
                return;
            case 5:
                l.setIcon(mine5);
                return;
            case 6:
                l.setIcon(mine6);
                return;
            case 7:
                l.setIcon(mine7);
                return;
            case 8:
                l.setIcon(mine8);
                return;
            //Do nothing (should never get here)
            default:
                return;
        }
    }
    //End of setRevealedIcon method

    /**
     * Reveals a number icon on the gameGrid based on the number of surrounding mines (by use of setRevealedIcon),
     * if the coordinates are not out of bounds and the label is not a mine.
     * Helper of revealLabels, marks the label as iterated over.
     * @param   int         x                   x coordinate of a label
     * @param   int         y                   y coordinate of a label
     * @param   int[][]     iterationTracker    Keeps track of the iteration sequence, used in revealLabels
     * @return  void
     */
    private void revealNumber(int x, int y, int[][] iterationTracker){
        //If the coordinates are not out of bounds
        if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
            //If the label is not a mine
            if(revealedGrid[x][y] != -1){
                //Reveal the icon on the gameGrid based on the surrounding mine count
                setRevealedIcon(gameGrid[x][y], revealedGrid[x][y]);

                //If the label is not marked as iterated over
                if(iterationTracker[x][y] == 0) {
                    //Mark the label as iterated over
                    iterationTracker[x][y] = 1;

                }
            }
        }
    }
    //End of revealNumber method

    /**
     * Helper of revealLabels, marks a blank label as iterated over if not already marked.
     * Increases and returns the iteration count for use in revealLabels.
     * @param   int         x                   x coordinate of a label
     * @param   int         y                   y coordinate of a label
     * @param   int         count               Counts how many items are left to iterate over, used in revealLabels
     * @param   int[][]     iterationTracker    Keeps track of the iteration sequence, used in revealLabels
     * @return  int         count
     */
    private int revealBlank(int x, int y, int count, int[][] iterationTracker){
        //If the coordinates are not out of bounds
        if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
            //If the label is blank
            if (revealedGrid[x][y] == 0) {
                //If not already iterated over
                if(iterationTracker[x][y] == 0) {
                    //Mark it as an iteration candidate
                    iterationTracker[x][y] = 1;

                    //Increase iteration count
                    count++;
                }
            }
        }
        //Return the iteration count
        return count;
    }
    //End of revealBlank method

    /**
     * Helper of revealLabel, that takes a blank label's coordinates and checks surrounding labels iteratively.
     * If a surrounding label is blank, that label's surroundings are also checked and revealed.
     * The process is repeated if necessary on blank labels and boundary numbers.
     * The process ends once all options have been exhausted (for every number reached, the surrounding labels are checked).
     * @param   int     x       x coordinate of the blank label we need to check the surroundings of
     * @param   int     y       y coordinate of the blank label we need to check the surroundings of
     */
    private void revealLabels(int x, int y){
        int a,b;        //Indices
        int count = 1;  //Iterator
        int[][] iterationTracker = new int[10][10]; //Array that keeps track of labels we have yet to check

        //Iterate over at least the first element
        do{
            //If the coordinates are not out of bounds
            if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
                //If the label is blank
                if (revealedGrid[x][y] == 0) {
                    //If the icon is actually blank and not a flag or question mark
                    if (gameGrid[x][y].getIcon() == blankStart) {
                        //Mark it as already iterated over
                        iterationTracker[x][y] = -1;

                        //Set icon to blank pressed
                        gameGrid[x][y].setIcon(blankPressed);

                        //Decrease items left to iterate over
                        count--;
                    }
                    //If the icon has a flag or question mark
                    else{
                        //Mark it as already iterated over
                        iterationTracker[x][y] = -1;

                        //Decrease items left to iterate over
                        count--;
                    }

                }

            }

            //Check the top left neighbor of the original label
            x = x - 1;
            y = y - 1;
            //If it is a valid iteration candidate, mark it and increase count
            count = revealBlank(x,y,count,iterationTracker);

            //Check the top center neighbor of the original label
            y = y + 1;
            count = revealBlank(x,y,count,iterationTracker);

            //Check the top right neighbor of the original label
            y = y + 1;
            count = revealBlank(x,y,count,iterationTracker);

            //Check the center left neighbor of the original label
            x = x + 1;
            y = y - 2;
            count = revealBlank(x,y,count,iterationTracker);

            //Check the center right neighbor of the original label
            y = y + 2;
            count = revealBlank(x,y,count,iterationTracker);

            //Check the bottom right neighbor of the original label
            x = x + 1;
            y = y - 2;
            count = revealBlank(x,y,count,iterationTracker);

            //Check the bottom center neighbor of the original label
            y = y + 1;
            count = revealBlank(x,y,count,iterationTracker);

            //Check the bottom right neighbor of the original label
            y = y + 1;
            count = revealBlank(x,y,count,iterationTracker);

            //For the entire grid
            for(a = 0; a < 10; a++){
                for(b = 0; b < 10; b++){
                    //If the label is an iteration candidate
                    if(iterationTracker[a][b] == 1){
                        //Set it as the next point to iterate over
                        x = a;
                        y = b;
                    }
                }
            }
        }
        //While there are still elements that have not been iterated over, continue
        while(count != 0);

        //For every label
        for(a = 0; a < 10; a++){
            for (b = 0; b < 10; b++){
                //If the icon was iterated upon
                if(iterationTracker[a][b] == -1){
                    //Coordinates of the label at the current position in the grid
                    x = a;
                    y = b;

                    //Check the top left neighbor of the original label
                    x = x - 1;
                    y = y - 1;
                    //Since we have revealed all blank labels, the label must be a number,
                    //So reveal the number at that position if it is a valid candidate
                    revealNumber(x,y,iterationTracker);

                    //Check the top center neighbor of the original label
                    y = y + 1;
                    revealNumber(x,y,iterationTracker);

                    //Check the top right neighbor of the original label
                    y = y + 1;
                    revealNumber(x,y,iterationTracker);

                    //Check the center left neighbor of the original label
                    x = x + 1;
                    y = y - 2;
                    revealNumber(x,y,iterationTracker);

                    //Check the center right neighbor of the original label
                    y = y + 2;
                    revealNumber(x,y,iterationTracker);

                    //Check the bottom left neighbor of the original label
                    x = x + 1;
                    y = y - 2;
                    revealNumber(x,y,iterationTracker);

                    //Check the bottom center neighbor of the original label
                    y = y + 1;
                    revealNumber(x,y,iterationTracker);

                    //Check the bottom right neighbor of the original label
                    y = y + 2;
                    revealNumber(x,y,iterationTracker);

                }
            }
        }
    }
    //End of revealLabels method

    /**
     * Takes a previously unpressed blank label that a user pressed on the gameGrid, and attempts to reveal the label icon.
     * If the user pressed a mine, the label is revealed and an end game integer is returned.
     * If the user pressed a blank label, pass control to revealLabels to check for surrounding blanks and numbers.
     * If the user pressed a label with a number of surrounding mines, reveal the number.
     * @param   JLabel      l       An unpressed blank label that the user has now pressed
     * @return  int                 Returns -1 if the user pressed a bomb (end game), 1 otherwise (continue game)
     */
    private int revealLabel(JLabel l){
        int x, y;       //Indices

        //For every label in the grid
        for(x = 0; x < 10; x++){
            for(y = 0; y < 10; y++){
                //If the label was found
                if(gameGrid[x][y] == l){
                    //If the position is a mine
                    if(revealedGrid[x][y] == -1){
                        //The user pressed a mine, reveal a mine
                        setRevealedIcon(l, -1);

                        //Return a lost game
                        return -1;
                    }
                    //Else if there are no surrounding mines at the label
                    else if(revealedGrid[x][y] == 0){
                        //Reveal the blank label as well as any surrounding blank labels and number labels
                        revealLabels(x,y);

                        //Reset any flag locations that were accidentally revealed by revealLabels
                        setFlagLocations(l);

                        //Return a successful reveal
                        return 1;
                    }
                    //Else if there are a number of surrounding mines at the label
                    else{
                        //Reveal the number of mines at the label
                        setRevealedIcon(l, revealedGrid[x][y]);

                        //Return a successful reveal
                        return 1;
                    }
                }
            }
        }
        //Return success (Nothing done), should never get here
        return 1;

    }
    //End of revealLabel method

    //END OF REVEAL METHODS





    //BEGINNING OF CLICK METHODS

    /**
     * Attempts to start the game based on the first user label click.
     * If the user pressed a valid blank label, a mine grid is initialized (avoiding the label),
     * a grid enumerating surrounding bomb information for each label is created,
     * and the label(s) are revealed.
     * If the use pressed an already marked label, the game does not start/restart
     * @param   JLabel      l       The label the user pressed
     * @return  int                 Returns whether the game has started (1) or not(0)
     */
    protected int clickStartGame(JLabel l){
        //If the label is an unpressed blank
        if(l.getIcon() == blankStart){
            //Initialize the mine locations, avoiding the now pressed label
            initializeGridMines(l);

            //Create the grid of revealed mine counts
            createRevealedGrid();

            //Change the icon to pressed, showing the number of surrounding mines
            //Return whether the process was successful (1)
            return revealLabel(l);
        }

        //The icon was not blank, so no press registered
        return 0;

    }
    //End of clickStartGame method


    /**
     * Cycles through the right click options of the Grid.
     * If the label is blank, it is set to a flag and the number of mines left to flag is decremented.
     * If the label is a flag, it is set to a question mark and the number of mines left to flag is incremented.
     * If the label is a question mark, it is set to a blank label and the count remains the same since it is
     * handled in the previous cases.
     * @param   JLabel      l       The label to be changed
     */
    private void rightClickCycle(JLabel l){
        //If the label is blank
        if(l.getIcon() == blankStart){
            //Change icon to a flag
            l.setIcon(flag);

            //Enumerate on the flag location holder
            setFlagLocations(l);

            //Decrement number of flags left
            flagCount--;

            //Update the flag display
            board.updateFoundGame(flagCount);
        }
        //else if the label is a flag
        else if(l.getIcon() == flag){
            //Change label to a question mark
            l.setIcon(questionMark);

            //Enumerate on the flag location holder
            setFlagLocations(l);

            //Increment number of flags left
            flagCount++;

            //Update the flag display
            board.updateFoundGame(flagCount);
        }
        //else if the label is a question mark
        else if (l.getIcon() == questionMark){
            //Change icon to blank
            l.setIcon(blankStart);

            //Enumerate on the flag location holder
            setFlagLocations(l);
        }
    }
    //End of rightClickCycle method

    //END OF CLICK METHODS



    //BEGINNING OF MOUSE EVENT METHODS

    /**
     * Handles the commands for when a mouse button is released, both right and left.
     * If the left mouse button is released:
     *      the smiley icon is reset to normal, the game is started or ended based on conditions,
     *      or a label is revealed through revealLabel(s).
     * If the right mouse button is released, execution is handed over to rightClickCycle which processes
     * the right click functionality.
     * @param       MouseEvent  e       Stores the mouse released
     * @var         JLabel      l       Stores the label that was released
     * @var         int         check   Checks various game conditions
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        int check;  //Checks game conditions

        //Get the label that was targeted by the user
        JLabel l = (JLabel) e.getSource();

        //If the left mouse button was released
        if( e.getButton() == 1){
            //If the smiley icon is an O face
            if ( board.smiley.current == Smiley.Type.SMILE_O ){
                // Change the smiley to the start icon since the mouse was released
                board.smiley.change ( Smiley.Type.SMILE_START);
            }
            //If the game has not started yet
            if(gameStart == 0) {
                //Attempt to start the game and clear labels
                check = clickStartGame(l);

                //If the game was successfully started (The icon was originally blank)
                if(check == 1){
                    //Update the checker variable
                    gameStart = 1;

                    //Start the board's timer
                    board.startTimerGame();
                }
            }
            //If the game is in progress
            else if (gameStart == 1){
                //If the label is blank
                if(l.getIcon() == blankStart) {
                    //Calculate and reveal labels, returning whether or not a mine has exploded
                    check = revealLabel(l);

                    //If the user pressed a mine
                    if (check == -1){
                        //Update checker variable
                        gameStart = -1;

                        //Set number of mines left to 0
                        flagCount = 0;

                        //Update the flag display
                        board.updateFoundGame(flagCount);
                        //Reveal the grid

                        revealGridLost();

                        //Lose the game
                        board.loseGame();
                    }
                    //Else there is a potential win state
                    else{
                        //If the user successfully cleared 90 labels
                        if(countCleared() == 90) {
                            //Update checker variable
                            gameStart = -1;

                            //Set number of mines left to 0
                            flagCount = 0;

                            //Update the flag display
                            board.updateFoundGame(flagCount);

                            //Reveal the grid
                            revealGridWon();

                            //Win the game
                            board.winGame(board.timer.current);
                        }
                    }
                }
            }
            //If the game is lost and not yet reset
            else if(gameStart == -1){
                //Do nothing
            }
        }

        //If the right mouse button was released
        if (e.getButton() == 3){
            //Process the right click at the label
            rightClickCycle(l);
        }
    }
    //End of mouseReleased method

    /**
     * Handles the commands for when a mouse is pressed (pre-release).
     * When the left mouse button is pressed on a blank grid icon, the smiley face changes to a smiley O face.
     * @param   MouseEvent  e   Stores the mouse pressed
     * @var     JLabel      l   Stores the label that was pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        //Get the label that was targeted
        JLabel l = (JLabel) e.getSource();

        //If the current icon is not blank
        if(l.getIcon() != blankStart) {
            //Do nothing
            return;
        }
        //If the left mouse button is pressed
        if (e.getButton () == 1) {
            //If the smiley is not start icon
            if (board.smiley.current != Smiley.Type.SMILE_START) {
                //Do nothing
                return;
            }
            //Change the smiley to a smiley O icon
            board.smiley.change (Smiley.Type.SMILE_O);
        }
    }

    /**
     * Sister class based on MouseEvent, required for implementation but does not handle anything in this program.
     * @param   MouseEvent  e   Stores the mouse clicked
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Sister class based on MouseEvent, required for implementation but does not handle anything in this program.
     * @param   MouseEvent  e   Stores the mouse entered
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Sister class based on MouseEvent, required for implementation but does not handle anything in this program.
     * @param   MouseEvent  e   Stores the mouse exited
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    //END OF MOUSE EVENT METHODS
}
//END OF GRID CLASS
