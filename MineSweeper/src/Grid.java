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


    private void rightReleasedCycle(JLabel l){
        //If the label is blank
        if(l.getIcon() == blankStart){
            //Change icon to a flag
            l.setIcon(flag);
            //Increment number of mines flagged
            flagCount--;
            //Update the flag display
            board.updateFoundGame(flagCount);
        }
        //else if the label is a flag
        else if(l.getIcon() == flag){
            //Change icon to a question mark
            l.setIcon(questionStart);
            //Decrement number of mines flagged (since a question mark must be the successor of a flag)
            flagCount++;
            //Update the flag display
            board.updateFoundGame(flagCount);
        }
        //else if the label is a question mark
        else if (l.getIcon() == questionStart){
            //Change icon to blank
            l.setIcon(blankStart);
        }

        //Check the flagCount
        System.out.println("Flag Count: " + flagCount);

    }




    private void surroundingBlankTiles(int x, int y){
        //Check if a surrounding tile is blank

        //EITHER
        // START THE GAME WITH AN ARRAY OF NUMBERS DICTATING HOW MANY SURROUNDING BOMBS EACH TILE HAS,
        // CALCULATE FOR ALL 8 SURROUNDING TILES WITHOUT REVEALING THE TILES (copied and modified setSurroundingMineCount)
        //
        // probably the second option, just us the block of if statements and if any of them execute, it's not blank and you return an error value and move on to trying the next tile
        // make sure if a surrounding tile is blank, to call this method again for that tile (quasi-recursively)
    }

    private int setSurroundingMineCount(JLabel l){
        int a, b;       //Indices
        int x = -1;     //Not found
        int y = -1;     //Not found
        int count = 0;  //Count surrounding mines

        //Find the location of the label on the grid
        for(a = 0; a < 10; a++){
            for(b = 0; b < 10; b++){
                //If the label was found
                if(gridArray[a][b] == l){
                    //Set the (x,y) coordinates of the label
                    x = a;
                    y = b;
                }

            }
        }

        //For every mine
        for(a = 0; a < 10; a++) {
            //Get the coordinates of the mine
            int mineX = mineArray[a].getX();
            int mineY = mineArray[a].getY();

            //If a mine was pressed
            if ((mineX == x) && (mineY == y)) {
                //Change the icon to a detonated mine
                l.setIcon(minePressed);

                //Return game over
                return 0;
            }


//            System.out.println("(" + x + "," + y + ")");
//            System.out.println("(" + mineX + "," + mineY + ")");
//            System.out.println();


            //If the current mine's coordinates are adjacent, increment surrounding mine count
            //Above
            if (mineX == (x - 1)) {
                //Left
                if (y == (mineY - 1))
                    count++;
                //Middle
                if (y == (mineY))
                    count++;
                //Right
                if (y == (mineY + 1))
                    count++;
            }
            //Same row
            if (mineX == x) {
                //Left
                if (y == (mineY - 1))
                    count++;
                //Right
                if (y == (mineY + 1))
                    count++;
            }
            //Under
            if (mineX == (x + 1)) {
                //Left
                if (y == (mineY - 1))
                    count++;
                //Middle
                if (y == (mineY))
                    count++;
                //Right
                if (y == (mineY + 1))
                    count++;
            }
        }


        //Change the icon of the current label based on the number of surrounding mines
        //Return 1 indicating a successful change
        switch(count){
            //No surrounding mines
            case 0:
                l.setIcon(blankPressed);

                //Uncover as many blank surrounding tiles as possible
                surroundingBlankTiles(x,y);
                return 1;
            case 1:
                l.setIcon(mine1);
                return 1;
            case 2:
                l.setIcon(mine2);
                return 1;
            case 3:
                l.setIcon(mine3);
                return 1;
            case 4:
                l.setIcon(mine4);
                return 1;
            case 5:
                l.setIcon(mine5);
                return 1;
            case 6:
                l.setIcon(mine6);
                return 1;
            case 7:
                l.setIcon(mine7);
                return 1;
            case 8:
                l.setIcon(mine8);
                return 1;
            //Do nothing (should never get here)
            default:
                return 1;
        }


    }

    private int leftReleasedStart(JLabel l){
        //Only if the label is unpressed blank, initialize the minefield and change the icon
        if(l.getIcon() == blankStart){
            System.out.println("GOT HERE");
            //Initialize the mine locations, avoiding the pressed label
            initializeGridMines(l);

            //Change the icon to pressed, showing the number of surrounding mines, return if successful
            return setSurroundingMineCount(l);
        }

        //The icon was not blank, so no press registered
        return 0;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Get the label that was targeted
        JLabel l = (JLabel) e.getSource();

        System.out.println(l.getIcon());

        //If the left mouse button was released
        if( e.getButton() == 1){
            //If the game has not started yet,
            if(gameStart == 0) {
                //Attempt to start the game and initialize field
                int check = leftReleasedStart(l);

                //If the game was successfully started (The icon was originally blank)
                if(check == 1){
                    //The game has started, update the checker variable and start the board's timer
                    gameStart = 1;
                    board.startTimerGame();
                }

            }
            //If the game is in progress
            else if (gameStart == 1){
                //If the icon is blank
                if(l.getIcon() == blankStart) {
                    //Calculate and reveal, check if a mine has exploded

                    //If the user pressed a mine
                    if (setSurroundingMineCount(l) == 0){
                        //Update checker variable and lose game
                        gameStart = -1;
                        board.loseGame();
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
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }


    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}

