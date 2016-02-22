import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;


public class Grid extends JPanel implements MouseListener {

    //Array of labels that creates the 10/10 game grid
    private JLabel[][] gridArray;

    //Array of mine locations
    private Coordinate[] mineArray;

    //Array of surrounding mine counts for each tile
    private int[][] revealedArray;

    //Array that keeps track of iteration
    private int[][] recursionTracker;

    /**
     * Array that holds flag and question mark locations on the grid
     * 0 = Blank icon
     * 1 = Flag icon
     * 2 = Question mark icon
     */
    private int[][] flagLocations;

    //Icons that will be used for the label display
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

    private ImageIcon minePressed;      //User pressed a mine
    private ImageIcon mineRevealed;     //Game end, reveals an unflagged and unpressed mine

    private ImageIcon flag;             //Flag, flags a mine location
    private ImageIcon questionStart;    //Question mark, not sure if a mine is there
    private ImageIcon questionPressed;  //Press a label with a question mark
    private ImageIcon wrongFlag;        //Game end, reveals if an incorrect flag was placed

    //Internal board instance for function calling
    protected Board board;

    //Checker to see if startTimerGame method has already been called
    //-1 = game lost and not yet reset, 0 = not started, 1 = started,
    private int gameStart;

    //Number of mines that have been flagged
    private int flagCount;

    

    //Constructs a 10x10 game grid
    Grid( Board board ){

        //Create a new 10x10 grid of labels that contains the game grid
        super(new GridLayout(10,10));
        gridArray = new JLabel[10][10];

        //Save board instance
        this.board = board;

        //Create icons
        initializeLabelIcons();

        //Fill the grid with blank icons
        initializeGridIcons();

        //Initialize the array of flags
        flagLocations = new int[10][10];

        //Initialize the iteration tracker
        recursionTracker = new int[10][10];

        //Game has not started and 10 mines need to be flagged;
        gameStart = 0;
        flagCount = 10;

    }

    //Creates all ImageIcons with their respective images
    private void initializeLabelIcons(){
        try {

            //Attempt to get the image
            Image img = ImageIO.read(getClass().getResource("Images/blankStart.gif"));
            //Scale the image properly
            Image newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            //Create the icon from the image
            blankStart = new ImageIcon(newimg);


            img = ImageIO.read(getClass().getResource("Images/blankPressed.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            blankPressed = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb1.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine1 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb2.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine2 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb3.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine3 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb4.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine4 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb5.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine5 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb6.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine6 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb7.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine7 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb8.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mine8 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bombPressed.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            minePressed = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bombRevealed.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            mineRevealed = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/flag.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            flag = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/questionStart.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            questionStart = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/questionPressed.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            questionPressed = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/wrongFlag.gif"));
            newimg = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
            wrongFlag = new ImageIcon(newimg);


        } catch (IOException ex){
            System.out.println("Could not find a file");
        }

    }

    //Fill the Grid with 100 blank labels as a start
    private void initializeGridIcons(){
            //Fill the grid
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    //Add a blankStart Icon
                    gridArray[i][j] = new JLabel(blankStart);

                    //No border
                    gridArray[i][j].setBorder(BorderFactory.createEmptyBorder());

                    //Add a mouse listener
                    gridArray[i][j].addMouseListener(this);

                    //Add to the grid
                    this.add(gridArray[i][j]);
                }
            }
    }

    /**
     * This method initializes the mineArray with 10 randomly generated mine coordinates.
     * @var     Coordinate  coordinate  An (X,Y) coordinate that acts as a mine location.
     * @param   JLabel      l           Initial clicked icon, where a mine should not be
     *                                  placed in order to ensure a game can be played.
     */
    private void initializeGridMines(JLabel l){
        //Create a new mine array
        mineArray = new Coordinate[10];

        //While 10 coordinates have not been added
        int i = 0;
        while(i  < 10){
            //Get a new coordinate
            Coordinate coordinate = new Coordinate();

            //Checker to see if the instance is a unique coordinate (not in the mineArray)
            //0 if unique, 1 if not unique
            int unique = 0;

            //If the coordinate is not unique, do not add nor increment
            int j = 0;
            while(j < i){
                if( (mineArray[j].getX() == coordinate.getX()) &&
                    (mineArray[j].getY() == coordinate.getY())    ){
                        //The coordinate is not unique
                        unique = 1;
                }
                j++;
            }

            //If the coordinate is the location of the first user-clicked label, do not add nor increment
            if(gridArray[coordinate.getX()][coordinate.getY()] == l){
                //The coordinate cannot be added
                unique = 1;
            }

            //If the coordinate is unique, add and increment
            if(unique != 1) {
                mineArray[i] = coordinate;
                i++;
            }
        }
    }

    private void setFlagLocations(JLabel l){
        int a, b;

        for(a = 0; a < 10; a++){
            for(b = 0; b < 10; b++){
                //If the icon is found
                if(l == gridArray[a][b]){
                    //Enumerate on the flag location holder
                    if(l.getIcon() == blankStart) {
                        flagLocations[a][b] = 0;
                    }
                    else if(l.getIcon() == flag) {
                        flagLocations[a][b] = 1;
                    }
                    else if(l.getIcon() == questionStart) {
                        flagLocations[a][b] = 2;
                    }
                }
            }
        }

        //Make sure flags are set on the grid array
        for(a = 0; a < 10; a++){
            for(b = 0; b < 10; b++){
                if(flagLocations[a][b] == 1) {
                    gridArray[a][b].setIcon(flag);
                }
                else if (flagLocations[a][b] == 2){
                    gridArray[a][b].setIcon(questionStart);
                }

            }
        }
    }

    private void rightReleasedCycle(JLabel l){
        //If the label is blank
        if(l.getIcon() == blankStart){
            //Change icon to a flag
            l.setIcon(flag);
            //Enumerate on the flag location holder
            setFlagLocations(l);
            //Increment number of mines flagged
            flagCount--;
            //Update the flag display
            board.updateFoundGame(flagCount);
        }
        //else if the label is a flag
        else if(l.getIcon() == flag){
            //Change icon to a question mark
            l.setIcon(questionStart);
            //Enumerate on the flag location holder
            setFlagLocations(l);
            //Decrement number of mines flagged (since a question mark must be the successor of a flag)
            flagCount++;
            //Update the flag display
            board.updateFoundGame(flagCount);
        }
        //else if the label is a question mark
        else if (l.getIcon() == questionStart){
            //Change icon to blank
            l.setIcon(blankStart);
            //Enumerate on the flag location holder
            setFlagLocations(l);
        }

    }


    private void createRevealedArray(){
        int x, y, z;                        //Indices
        int count = 0;                      //Count surrounding mines
        revealedArray = new int[10][10];    //Initialize array

        //For every tile
        for(x = 0; x < 10; x++) {
            for (y = 0; y < 10; y++) {
                //For every mine
                for (z = 0; z < 10; z++) {
                    //Get the coordinates of the mine
                    int mineX = mineArray[z].getX();
                    int mineY = mineArray[z].getY();

                    //If we are at a mine
                    if ((mineX == x) && (mineY == y)) {
                        //Inform that a mine is at that position
                        revealedArray[x][y] = -1;
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

                //We are not at a mine
                if(revealedArray[x][y] != -1){
                    //Provide the surrounding mine count at that position
                    revealedArray[x][y] = count;
                }

                count = 0;
            }
        }
    }


    private void setRevealedIcon(JLabel l, int count){
        //Change the icon of the current label based on the number of surrounding mines
        //Return 1 indicating a successful change
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

    private void revealIterative(int x, int y){
        int i = 0;
        int a,b;
        int count = 1;
        int[][] recursionTracker = new int[10][10];

        do{
            System.out.println("GOT HERE ITERATIVE 1");

            a = 0;
            b = 0;
            for (a = 0; a < 10; a++){
                System.out.println(revealedArray[a][b] + "," + revealedArray[a][b+1] + "," + revealedArray[a][b+2] + "," + revealedArray[a][b+3] + "," + revealedArray[a][b+4] + "," + revealedArray[a][b+5] + "," + revealedArray[a][b+6] + "," + revealedArray[a][b+7] + "," + revealedArray[a][b+8] + "," + revealedArray[a][b+9]);
            }
            a = 0;

            System.out.println();
            a = 0;
            b = 0;
            for (a = 0; a < 10; a++){
                System.out.println(recursionTracker[a][b] + "," + recursionTracker[a][b+1] + "," + recursionTracker[a][b+2] + "," + recursionTracker[a][b+3] + "," + recursionTracker[a][b+4] + "," + recursionTracker[a][b+5] + "," + recursionTracker[a][b+6] + "," + recursionTracker[a][b+7] + "," + recursionTracker[a][b+8] + "," + recursionTracker[a][b+9]);
            }
            a = 0;

           //If the coordinates are not out of bounds
           if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
               //If the space is blank
               if (revealedArray[x][y] == 0) {
                   //If the icon is blank
                   if (gridArray[x][y].getIcon() == blankStart) {
                       //Mark it as already iterated over
                       recursionTracker[x][y] = -1;
                       //Set icon to blank
                       gridArray[x][y].setIcon(blankPressed);

                       //Decrease iteration count
                       count--;
                   }
                   //If the icon has a flag or question mark
                   else{
                       //Mark it as already iterated over
                       recursionTracker[x][y] = -1;

                       //Decrease iteration count
                       count--;
                   }

               }

           }

           x = x - 1;
           y = y - 1;
            //If the coordinates are not out of bounds
            if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
                //If the space is blank
                if (revealedArray[x][y] == 0) {
                    if(recursionTracker[x][y] == 0) {
                        //Mark it as an iteration candidate
                        recursionTracker[x][y] = 1;
                        //Increase iteration count
                        count++;
                    }
                }
            }

           y = y + 1;
           //If the coordinates are not out of bounds
           if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
               //If the space is blank
               if (revealedArray[x][y] == 0) {
                   if(recursionTracker[x][y] == 0) {
                       //Mark it as an iteration candidate
                       recursionTracker[x][y] = 1;
                       //Increase iteration count
                       count++;
                   }
               }
           }

           y = y + 1;
           //If the coordinates are not out of bounds
           if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
               //If the space is blank
               if (revealedArray[x][y] == 0) {
                   if(recursionTracker[x][y] == 0) {
                       //Mark it as an iteration candidate
                       recursionTracker[x][y] = 1;
                       //Increase iteration count
                       count++;
                   }
               }
           }

           x = x + 1;
           y = y - 2;
           //If the coordinates are not out of bounds
           if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
               //If the space is blank
               if (revealedArray[x][y] == 0) {
                   if(recursionTracker[x][y] == 0) {
                       //Mark it as an iteration candidate
                       recursionTracker[x][y] = 1;
                       //Increase iteration count
                       count++;
                   }
               }
           }

           y = y + 2;
           //If the coordinates are not out of bounds
           if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
               //If the space is blank
               if (revealedArray[x][y] == 0) {
                   if(recursionTracker[x][y] == 0) {
                       //Mark it as an iteration candidate
                       recursionTracker[x][y] = 1;
                       //Increase iteration count
                       count++;
                   }
               }
           }

           x = x + 1;
           y = y - 2;
           //If the coordinates are not out of bounds
           if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
               //If the space is blank
               if (revealedArray[x][y] == 0) {
                   if(recursionTracker[x][y] == 0) {
                       //Mark it as an iteration candidate
                       recursionTracker[x][y] = 1;
                       //Increase iteration count
                       count++;
                   }
               }
           }

           y = y + 1;
           //If the coordinates are not out of bounds
           if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
               //If the space is blank
               if (revealedArray[x][y] == 0) {
                   if(recursionTracker[x][y] == 0) {
                       //Mark it as an iteration candidate
                       recursionTracker[x][y] = 1;
                       //Increase iteration count
                       count++;
                   }
               }
           }

           y = y + 1;
           //If the coordinates are not out of bounds
           if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
               //If the space is blank
               if (revealedArray[x][y] == 0) {
                   if(recursionTracker[x][y] == 0) {
                       //Mark it as an iteration candidate
                       recursionTracker[x][y] = 1;
                       //Increase iteration count
                       count++;
                   }
               }
           }



           //Check the tracker for a coordinate
           for(a = 0; a < 10; a++){
               for(b = 0; b < 10; b++){
                   //If it is an iteration candidate, go to it
                   if(recursionTracker[a][b] == 1){
                       x = a;
                       y = b;
                   }
               }
           }

            System.out.println("COUNT:" + count);

        }
        while(count != 0);


        for(a = 0; a < 10; a++){
            for (b = 0; b < 10; b++){

                System.out.println("GOT HERE ITERATIVE 2");
                //If the icon was iterated upon
                if(recursionTracker[a][b] == -1){
                    x = a;
                    y = b;

                    x = x - 1;
                    y = y - 1;
                    //If the coordinates are not out of bounds
                    if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
                        //If the icon is not a mine, reveal it
                        if(revealedArray[x][y] != -1){
                            setRevealedIcon(gridArray[x][y], revealedArray[x][y]);

                            //Set the number as iterated over
                            if(recursionTracker[x][y] == 0) {
                                recursionTracker[x][y] = 1;

                            }
                        }
                    }

                    y = y + 1;
                    //If the coordinates are not out of bounds
                    if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
                        //If the icon is not a mine, reveal it
                        if(revealedArray[x][y] != -1){
                            setRevealedIcon(gridArray[x][y], revealedArray[x][y]);

                            //Set the number as iterated over
                            if(recursionTracker[x][y] == 0) {
                                recursionTracker[x][y] = 1;

                            }
                        }
                    }

                    y = y + 1;
                    //If the coordinates are not out of bounds
                    if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
                        //If the icon is not a mine, reveal it
                        if(revealedArray[x][y] != -1){
                            setRevealedIcon(gridArray[x][y], revealedArray[x][y]);

                            //Set the number as iterated over
                            if(recursionTracker[x][y] == 0) {
                                recursionTracker[x][y] = 1;

                            }
                        }
                    }

                    x = x + 1;
                    y = y - 2;
                    //If the coordinates are not out of bounds
                    if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
                        //If the icon is not a mine, reveal it
                        if(revealedArray[x][y] != -1){
                            setRevealedIcon(gridArray[x][y], revealedArray[x][y]);

                            //Set the number as iterated over
                            if(recursionTracker[x][y] == 0) {
                                recursionTracker[x][y] = 1;

                            }
                        }
                    }

                    y = y + 2;
                    //If the coordinates are not out of bounds
                    if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
                        //If the icon is not a mine, reveal it
                        if(revealedArray[x][y] != -1){
                            setRevealedIcon(gridArray[x][y], revealedArray[x][y]);

                            //Set the number as iterated over
                            if(recursionTracker[x][y] == 0) {
                                recursionTracker[x][y] = 1;

                            }
                        }
                    }

                    x = x + 1;
                    y = y - 2;
                    //If the coordinates are not out of bounds
                    if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
                        //If the icon is not a mine, reveal it
                        if(revealedArray[x][y] != -1){
                            setRevealedIcon(gridArray[x][y], revealedArray[x][y]);

                            //Set the number as iterated over
                            if(recursionTracker[x][y] == 0) {
                                recursionTracker[x][y] = 1;

                            }
                        }
                    }

                    y = y + 1;
                    //If the coordinates are not out of bounds
                    if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
                        //If the icon is not a mine, reveal it
                        if(revealedArray[x][y] != -1){
                            setRevealedIcon(gridArray[x][y], revealedArray[x][y]);

                            //Set the number as iterated over
                            if(recursionTracker[x][y] == 0) {
                                recursionTracker[x][y] = 1;
                            }
                        }
                    }

                    y = y + 2;
                    //If the coordinates are not out of bounds
                    if ((x < 10) && (y < 10) && (x >= 0) && (y >= 0)) {
                        //If the icon is not a mine, reveal it
                        if(revealedArray[x][y] != -1){
                            setRevealedIcon(gridArray[x][y], revealedArray[x][y]);

                            //Set the number as iterated over
                            if(recursionTracker[x][y] == 0) {
                                recursionTracker[x][y] = 1;
                            }
                        }
                    }

                }
            }
        }


    }

    private void revealGridWon(){

        int a,b,x;

        for(x = 0; x < 10; x++){
            //Get the coordinates of a mine
            a = mineArray[x].getX();
            b = mineArray[x].getY();

            //Flag it on the display
            gridArray[a][b].setIcon(flag);
        }
    }


    private void revealGridLost(){
        int a, b, c;

        //For the entire grid
        for(a = 0; a < 10; a++){
            for(b = 0; b < 10; b++) {
                //For every mine
                for (c = 0; c < 10; c++) {
                    //If the position on the grid is a mine
                    if ((mineArray[c].getX() == a) && (mineArray[c].getY() == b)){
                        //If the position is blank, or a question mark
                        if( (gridArray[a][b].getIcon() == blankStart) || (gridArray[a][b].getIcon() == questionStart)) {
                            //Reveal the mine on the grid
                            gridArray[a][b].setIcon(mineRevealed);
                        }
                        //If the position is a flag, do nothing
                    }
                }

                //If the position is a flag
                if((gridArray[a][b].getIcon() == flag)){
                    //If there is no mine
                    if(revealedArray[a][b] != -1) {
                        //Reveal it as a wrong flag
                        gridArray[a][b].setIcon(wrongFlag);
                    }
                }
                //If the position is a question mark, do nothing


            }
        }
    }

    private int revealMineCount(JLabel l){
        int x, y;       //Indices
        int a, b;
        //Find the location of the label on the grid
        for(x = 0; x < 10; x++){
            for(y = 0; y < 10; y++){
                //If the label was found
                if(gridArray[x][y] == l){
                    //Mine was pressed
                    if(revealedArray[x][y] == -1){
                        setRevealedIcon(l, -1);
                        return -1;
                    }
                    //No surrounding mines (blank)
                    else if(revealedArray[x][y] == 0){
                        revealIterative(x,y);

                        //Reset any flag locations that were revealed
                        setFlagLocations(l);

                        return 1;
                    }
                    //Number of surrounding mines
                    else{
                        setRevealedIcon(l, revealedArray[x][y]);
                        System.out.println("GOT HERE NORMAL");
                        return 1;
                    }

                }

            }
        }

        return 1;

    }

    private int countCleared(){
        int a,b,count;
        count = 0;

        for(a = 0; a < 10; a++){
            for(b = 0; b < 10; b++){
                if( (gridArray[a][b].getIcon() != blankStart) && (gridArray[a][b].getIcon() != flag) && (gridArray[a][b].getIcon() != questionStart)) {
                    count++;
                }
            }
        }

        return count;
    }

    private int leftReleasedStart(JLabel l){
        //Only if the label is unpressed blank, initialize the minefield and change the icon
        if(l.getIcon() == blankStart){
            //Initialize the mine locations, avoiding the pressed label
            initializeGridMines(l);

            //Create the array of revealed mine counts
            createRevealedArray();

            //Change the icon to pressed, showing the number of surrounding mines, return if successful
            return revealMineCount(l);
        }

        //The icon was not blank, so no press registered
        return 0;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Get the label that was targeted
        JLabel l = (JLabel) e.getSource();

        //Used to check conditions
        int check;

        //If the left mouse button was released
        if( e.getButton() == 1){
            //If the smiley icon is an O face
            if ( board.smiley.current == Smiley.Type.SMILE_O ){
                // Change the smiley to the start icon since the mouse was released
                board.smiley.change ( Smiley.Type.SMILE_START);
            }

            //If the game has not started yet
            if(gameStart == 0) {
                //Attempt to start the game and initialize field
                check = leftReleasedStart(l);

                //If the game was successfully started (The icon was originally blank)
                if(check == 1){
                    //The game has started, update the checker variable
                    gameStart = 1;
                    //Start the board's timer
                    board.startTimerGame();
                }

            }
            //If the game is in progress
            else if (gameStart == 1){
                //If the icon is blank
                if(l.getIcon() == blankStart) {
                    //Calculate and reveal, check if a mine has exploded
                    check = revealMineCount(l);
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
                    //Potential win state
                    else{
                        //If the user successfully cleared 90 tiles
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
            rightReleasedCycle(l);
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Get the label that was targeted
        JLabel l = (JLabel) e.getSource();

        //If the current icon is blank
        if(l.getIcon() != blankStart) {
            return;
        }
        //If the left mouse button is pressed
        if (e.getButton () == 1) {
            //If the smiley is the start icon
            if (board.smiley.current != Smiley.Type.SMILE_START) {
                return;
            }
            //Change the smiley to a smiley O icon
            board.smiley.change (Smiley.Type.SMILE_O);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}

