import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Grid extends JPanel implements MouseListener {

    //Array of labels that creates the 10/10 game grid
    private JLabel[][] gridArray;

    //Array of mine locations
    private Coordinate[] mineArray;

    //Icons that will be used for the label display
    private ImageIcon blankStart;       //Initial label
    private ImageIcon blankPressed;     //Pressed label, no surrounding bombs

    private ImageIcon bomb1;            //Surrounding number of bombs
    private ImageIcon bomb2;
    private ImageIcon bomb3;
    private ImageIcon bomb4;
    private ImageIcon bomb5;
    private ImageIcon bomb6;
    private ImageIcon bomb7;
    private ImageIcon bomb8;

    private ImageIcon bombPressed;      //User pressed a bomb
    private ImageIcon bombRevealed;     //Game end, reveals an unflagged and unpressed bomb

    private ImageIcon flag;             //Flag, flags a bomb location
    private ImageIcon questionStart;    //Question mark, not sure if a bomb is there
    private ImageIcon questionPressed;  //Press a label with a question mark
    private ImageIcon wrongFlag;        //Game end, reveals if an incorrect flag was placed


    //Indicator of whether or not the game has started (User has LEFT clicked)
    //0 = not in progress, 1 = in progress
    public int gameStart;



    //Constructs a 10x10 game grid
    Grid(){
        //Create a new 10x10 grid of labels that contains the game grid
        super(new GridLayout(10,10));
        gridArray = new JLabel[10][10];

        //User has not started the game
        gameStart = 0;

        //Create icons
        initializeLabelIcons();

        //Fill the grid with blank icons
        initializeGridIcons();

        //Create the mines
        initializeGridMines();

    }

    //Creates all ImageIcons with their respective images
    private void initializeLabelIcons(){
        try {

            //Attempt to get the image
            Image img = ImageIO.read(getClass().getResource("Images/blankStart.gif"));
            //Scale the image properly
            Image newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            //Create the icon from the image
            blankStart = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/blankPressed.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            blankPressed = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb1.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            bomb1 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb2.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            bomb2 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb3.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            bomb3 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb4.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            bomb4 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb5.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            bomb5 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb6.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            bomb6 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb7.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            bomb7 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bomb8.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            bomb8 = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bombPressed.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            bombPressed = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/bombRevealed.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            bombRevealed = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/flag.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            flag = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/questionStart.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            questionStart = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/questionPressed.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            questionPressed = new ImageIcon(newimg);

            img = ImageIO.read(getClass().getResource("Images/wrongFlag.gif"));
            newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
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

    //Create 10 mines in random places
    private void initializeGridMines(){
        //Create a new mine array
        mineArray = new Coordinate[10];

        int i = 0;
        while(i  < 10){
            //Get a new coordinate
            Coordinate coordinate = new Coordinate();

            //The instance is a unique coordinate
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

            //If the coordinate is unique, add and increment
            if(unique != 1) {
                mineArray[i] = coordinate;
                i++;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Get the label that was targeted
        JLabel l = (JLabel) e.getSource();


        System.out.println(l.getIcon());
        System.out.println(blankStart);

//
//        l.setIcon(blankPressed);

        //If the left mouse button was released
        if( e.getButton() == 1){
            //Start the game
            gameStart = 1;

            //NO CHANGE if the button is marked already
            //If no bomb underneath, calculate surrounding bombs and show number or blank tile
            //else bombPressed, show board
        }


        //If the right mouse button was released
        if (e.getButton() == 3){

            //Alternate between the three options

        }


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}

