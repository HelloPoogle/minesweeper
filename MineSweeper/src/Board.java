import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Board extends JFrame implements ActionListener {
    private Container board;

    /**
     * Constructor used to set up the GUI elements
     */
    Board(){
        //Set title of the frame and exit on closure
        super("Minesweeper");
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        // Initialize the content pane
        board = getContentPane();
        board.setLayout(new BorderLayout());

        /**
         * Creates the menus at the top of the UI
         * gameMenu : holds game commands (Reset, Top Ten, and Exit)
         * helpMenu : holds help information (Help, About)
         */
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("Help");

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        /**
         * Create 2 panels
         * pTop : holds the reset button, as well as the time and mine labels
         * pBottom : holds the game Grid
         */
        JPanel pTop = new JPanel(new GridLayout(1,3));
        JPanel pBottom = new Grid();


        /**
         * Create items for pTop
         * mineLabel : displays number of mines left (that the user has not tagged)
         * resetButton : resets the game
         * timeLabel : displays the time user has spent playing the game
         */
        JLabel mineLabel = new JLabel("Mines", SwingConstants.CENTER);
        JButton resetButton = new JButton("Smiley");
        JLabel timeLabel = new JLabel("Time", SwingConstants.CENTER);


        //Add the labels and button to the top panel
        pTop.add(mineLabel, BorderLayout.WEST);
        pTop.add(resetButton, BorderLayout.CENTER);
        pTop.add(timeLabel, BorderLayout.EAST);

        //Set the border of the top and bottom panels
        pTop.setBorder(BorderFactory.createLoweredBevelBorder());
        pBottom.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5),Color.lightGray));

        //Add the panels and menu to the board
        board.add(menuBar, BorderLayout.PAGE_START);
        board.add(pTop, BorderLayout.CENTER);
        board.add(pBottom, BorderLayout.SOUTH);

        //Set size and visibility of board
        setSize(300, 420);
        setVisible( true );

    }
    // end of Board constructor

    public void actionPerformed( ActionEvent event )
    {

    }
}
