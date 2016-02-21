import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.ImageIcon;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Board.java - This class basically holds all the GUI panels as well as all the other classes that
 * are needed such as the HighScores class which manages the games leader board.  This class
 * basically is a wrapper that holds everything together including the main JFrame instance for the
 * window that the game uses.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #02 - MineSweeper
 * @category    Board
 * @author      Rafael Grigorian
 * @author      Marek Rybakiewicz
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Board extends JFrame implements MouseListener {

	/**
	 * This data member holds the instance of the container class for the main window frame.
	 * @var     Container       container           Instance of container for main frame
	 */
	private Container container;

	/**
	 * This data member contains the instance of the panel that holds the smiley, mine count, and
	 * timer sub panels.
	 * @var     JPanel          display             The instance of the top section of the display
	 */
	private JPanel display;

	/**
	 * This data member contains the grid class which extends from the JPanel class.  This instance
	 * takes care of all the game logic.
	 * @var     Grid            grid                Instance of the grid saved internally
	 */
	private Grid grid;

	/**
	 * This data member is a Smiley instance which extends from the JLabel class.
	 * @var     Smiley          smiley              Instance of the Smiley class
	 */
	protected Smiley smiley;

	/**
	 * This data member is used to display the number of mines left to mark on the board.
	 * @var     Display         mines               Instance to the JPanel class
	 */
	private Display mines;

	/**
	 * This data member keeps track of the time ongoing after thee first move is made.
	 * @var     Timer           timer               The instance of Timer which extends from JPanel
	 */
	protected Timer timer;

	/**
	 * This data member holds the instance of HighScores which manages everything to do with the
	 * leader board.
	 * @var     HighScores      highScores          The instance of the HighScores class
	 */
	private HighScores highScores;

	/**
	 * This data member holds a reference to the frame of the window created when prompted for a
	 * name for the leader board.  This is done in order to control it more easily through the
	 * mouse event handler.
	 * @var     Window          frame               The instance a JFrame class
	 */
	protected Window frame;

	/**
	 * This data member holds a reference to the input box in the window created when prompted for
	 * a name for the leader board.  This is done in order to control it more easily through the
	 * mouse event handler.
	 * @var     JTextField      inputName           This holds the name used for a score submission
	 */
	protected JTextField inputName;

	/**
	 * This data member holds a reference to the submit button in the window created when prompted
	 * for a name for the leader board.  This is done in order to control it more easily through
	 * the mouse event handler.
	 * @var     JButton         submit              The submit button instance
	 */
	protected JButton submit;

	/**
	 * This data member stores the users score.  It is used in the mouse event handler when a score
	 * submission is prompted.
	 * @var     int             score               The users score
	 */
	protected int score;

	/**
	 * This constructor initializes the main frame and initializes all the other classes that are
	 * necessary for this program to function.  It initializes all the additional panels that are
	 * needed as well as the high score object.
	 */
	protected Board () {
		// Pass title to JFrame constructor
		super ( "MineSweeper" );
		// Make the close button terminate the game
		this.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
		// Initialize the high score instance
		this.highScores = new HighScores ( "data.txt", 10 );
		// Initialize the display components
		this.container = getContentPane ();
		this.display = new JPanel ( new GridLayout ( 1, 3 ) );
		this.mines = new Display ( 10 );
		this.smiley = new Smiley ( Smiley.Type.SMILE_START );
		this.timer = new Timer ( 0 );
		this.grid = new Grid ( this );
		JMenuBar menu = createMenuBar ();
		// Apply the container layout onto the programs window
		this.container.setLayout ( new BorderLayout () );
		// Set the size of both of the sections
		this.display.setPreferredSize ( new Dimension ( 35, 160 ) );
		this.grid.setPreferredSize ( new Dimension ( 160, 175 ) );
		// Add labels to the display panel
		this.display.add ( this.mines.getPanel () );
		this.display.add ( this.smiley );
		this.display.add ( this.timer.getPanel () );
		// Bind a mouse listener to the smiley class
		this.smiley.addMouseListener ( this );
		// Set the borders for both the grid and the display
		this.display.setBorder ( BorderFactory.createLoweredBevelBorder () );
		this.grid.setBorder (
			BorderFactory.createStrokeBorder ( new BasicStroke ( 5 ), Color.lightGray )
		);
		// Add both panels and menu to the container
		this.container.add ( this.display, BorderLayout.CENTER );
		this.container.add ( this.grid, BorderLayout.SOUTH );
		this.container.add ( menu, BorderLayout.PAGE_START );
		// Set the window size accordingly
		setSize ( 175, 250 );
		// Disable resizing of window
		setResizable ( false );
		// Change display options
		this.display.setBackground ( Color.lightGray );
		this.display.setBorder ( BorderFactory.createMatteBorder ( 0, 0, 0, 0, Color.lightGray ) );
		// Center window to be in the middle of the screen on initialization
		Dimension dim = Toolkit.getDefaultToolkit ().getScreenSize ();
		setLocation (
			dim.width / 2 - this.getSize ().width / 2,
			dim.height / 2 - this.getSize ().height / 2
		);
		// Set the frame to visible
		setVisible ( true );
	}

	/**
	 * This function returns the menu bar instance as defined in Java's swing class.  It constructs
	 * all sub-menus and finally returns the JMenuBar reference back.
	 * @return  JMenuBar                            A populated JMenuBar instance
	 */
	protected JMenuBar createMenuBar () {
		// Initialize the menu bar within the program's menu
		JMenuBar menu = new JMenuBar ();
		// Initialize main menus
		JMenu dropDownGame = new JMenu ( "Game" );
		JMenu dropDownHelp = new JMenu ( "Help" );
		// Initialize sub menu, high scores, bind tool tip, and callback function
		JMenuItem highScores = new JMenuItem ( "Leaderboard" );
		highScores.setToolTipText ( "Display Top-Ten Scores" );
		highScores.addActionListener ( new ActionListener () {
			@Override
			public void actionPerformed ( ActionEvent event ) {
				actionHighScores ();
			}
		});
		// Initialize sub menu, reset game, bind tool tip, and callback function
		JMenuItem resetGame = new JMenuItem ( "New Game" );
		resetGame.setToolTipText ( "Reset Current Game" );
		resetGame.addActionListener ( new ActionListener () {
			@Override
			public void actionPerformed ( ActionEvent event ) {
				actionResetGame ();
			}
		});
		// Initialize sub menu, reset high scores, bind tool tip, and callback function
		JMenuItem resetScores = new JMenuItem ( "Truncate Scores" );
		resetScores.setToolTipText ( "Reset High Scores Leader Board" );
		resetScores.addActionListener ( new ActionListener () {
			@Override
			public void actionPerformed ( ActionEvent event ) {
				actionResetHighScores ();
			}
		});
		// Initialize sub menu, exit, bind tool tip, and callback function
		JMenuItem exitGame = new JMenuItem ( "Exit" );
		exitGame.setToolTipText ( "Exit Game" );
		exitGame.addActionListener ( new ActionListener () {
			@Override
			public void actionPerformed ( ActionEvent event ) {
				System.exit ( 0 );
			}
		});
		// Initialize sub menu, how to play, bind tool tip, and callback function
		JMenuItem howToPlayHelp = new JMenuItem ( "Rules" );
		howToPlayHelp.setToolTipText ( "Instructions on how to play the game" );
		howToPlayHelp.addActionListener ( new ActionListener () {
			@Override
			public void actionPerformed ( ActionEvent event ) {
				actionHowToPlayHelp ();
			}
		});
		// Initialize sub menu, about, bind tool tip, and callback function
		JMenuItem aboutHelp = new JMenuItem ( "About" );
		aboutHelp.setToolTipText ( "Displays credits" );
		aboutHelp.addActionListener ( new ActionListener () {
			@Override
			public void actionPerformed ( ActionEvent event ) {
				actionAboutHelp ();
			}
		});
		// Add all of the sub menus to the game menu
		dropDownGame.add ( highScores );
		dropDownGame.add ( resetGame );
		dropDownGame.add ( resetScores );
		dropDownGame.add ( exitGame );
		// Add all of the sub menus to the help menu
		dropDownHelp.add ( howToPlayHelp );
		dropDownHelp.add ( aboutHelp );
		// Add both drop-downs to the menu object
		menu.add ( dropDownGame );
		menu.add ( dropDownHelp );
		// Bind to keys
		dropDownGame.setMnemonic ( KeyEvent.VK_G );
		dropDownHelp.setMnemonic ( KeyEvent.VK_H );
		highScores.setMnemonic ( KeyEvent.VK_L );
		resetGame.setMnemonic ( KeyEvent.VK_N );
		resetScores.setMnemonic ( KeyEvent.VK_T );
		exitGame.setMnemonic ( KeyEvent.VK_X );
		howToPlayHelp.setMnemonic ( KeyEvent.VK_R );
		aboutHelp.setMnemonic ( KeyEvent.VK_A );
		// Return the menu object
		return menu;
	}

	/**
	 * This function is triggered by the menu bar, and it accesses the helper function inside the
	 * HighScores instance.  It displays the high scores within a JFrame in a GUI fashion.
	 * @return  void
	 */
	protected void actionHighScores () {
		// Display the high scores using the HighScores instance
		this.highScores.display ();
	}

	/**
	 * This function is triggered by the menu bar and it resets the current game.  This is a
	 * wrapper function that simply calls the internal function that does all the work.
	 * @return  void
	 */
	protected void actionResetGame () {
		// Call the reset game function internally
		this.resetGame ();
	}

	/**
	 * This function is triggered by the menu bar and it resets the high scores by calling the
	 * helper functions supplied by the HighScores class.
	 * @return  void
	 */
	protected void actionResetHighScores () {
		// Reset the high scores using the high scores class
		this.highScores.reset ();
	}

	/**
	 * This function renders a new window that details how to play this game.  It uses the Window
	 * class to simplify the operations.
	 * @return  void
	 * @see     Window.java
	 */
	protected void actionHowToPlayHelp () {
		// Initialize Window instance
		Window frame = new Window ( "How to Play", 150, 600, 1, 5 );
		// Populate lines with data
		frame.panel.add (
			new JLabel (
				"Find the empty squares while avoiding the mines."
			),
			frame.options ( 0, 0, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
				new JLabel (
						"The faster you clear the board, the better your score."
				),
				frame.options ( 0, 1, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			new JLabel (
				"The rules in Minesweeper are simple: Uncover a mine and the game ends,"
			),
			frame.options ( 0, 2, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			new JLabel (
				"uncover an empty square and you keep playing, uncover a number and it tells you"
			),
			frame.options ( 0, 3, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			new JLabel (
				"how many mines lay hidden in the eight surrounding squares - information you use to"
			),
			frame.options ( 0, 4, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			new JLabel ( "deduce which nearby squares are safe to click." ),
			frame.options ( 0, 5, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
				new JLabel ( "Right click to flag a potential mine or set a question mark. Press the smiley to reset." ),
				frame.options ( 0, 6, 1.0, 1.0, GridBagConstraints.WEST )
		);
		// Render out the frame
		frame.display ();
	}

	/**
	 * This function renders the about menu, and uses the Window class to simplify the operations
	 * within this function.
	 * @return  void
	 * @see     Window.java
	 */
	protected void actionAboutHelp () {
		// Initialize Window instance
		Window frame = new Window ( "About", 300, 300, 1, 10 );
		// Insert information line, by line
		frame.panel.add (
			frame.bold ( new JLabel ( "" ) ),
			frame.options ( 0, 0, 3.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			frame.bold ( new JLabel ( "          Authors:" ) ),
			frame.options ( 0, 1, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			new JLabel ( "          rgrigo3 - Rafael Grigorian" ),
			frame.options ( 0, 2, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			new JLabel ( "          mrybak3 - Marek Rybakiewicz" ),
			frame.options ( 0, 3, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			frame.bold ( new JLabel ( "" ) ),
			frame.options ( 0, 4, 6.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			frame.bold ( new JLabel ( "          Additional Information:" ) ),
			frame.options ( 0, 5, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			new JLabel ( "          Project #02 - MineSweeper" ),
			frame.options ( 0, 6, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			new JLabel ( "          CS342 - Software Design" ),
			frame.options ( 0, 7, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			new JLabel ( "          University of Illinois at Chicago" ),
			frame.options ( 0, 8, 1.0, 1.0, GridBagConstraints.WEST )
		);
		frame.panel.add (
			frame.bold ( new JLabel ( "" ) ),
			frame.options ( 0, 9, 3.0, 1.0, GridBagConstraints.WEST )
		);
		// Render out the frame
		frame.display ();
	}

	/**
	 * This function resets the current game by setting a flag for the timer not to increment,
	 * resetting the timer and reinitializing the Mines panel.  It also resets the smiley icon
	 * ;).
	 * @return  void
	 */
	protected void resetGame () {
		// Reset the grid
		this.container.remove ( this.grid );
		// Reset counter and remove Timer instance
		Timer.time = 0;
		Timer.run = false;
		this.timer.render ( 0 );
		this.display.remove ( this.timer.getPanel () );
		// Create a new grid instance and reset size and border
		this.grid = new Grid ( this );
		this.display.setPreferredSize ( new Dimension ( 35, 160 ) );
		this.grid.setPreferredSize ( new Dimension ( 160, 175 ) );
		this.grid.setBorder (
			BorderFactory.createStrokeBorder ( new BasicStroke ( 5 ), Color.lightGray )
		);
		this.container.add ( this.grid, BorderLayout.SOUTH );
		this.display.add ( this.timer.getPanel () );
		// Re-render the frame
		this.invalidate ();
		this.validate ();
		// Reset the mines
		this.mines.render ( 10 );
		// Reset smiley face
		this.smiley.change ( Smiley.Type.SMILE_START );
	}

	/**
	 * This function runs when the game is won.  It stops the timer, set the smiley icon, and
	 * prompts user for name is a high score is met.
	 * @param   int             score               The users score
	 * @return  void
	 */
	protected void winGame ( int score ) {
		// Save score internally
		this.score = score;
		// Stop the timer
		this.timer.stop ();
		// Change the icon of the smiley
		this.smiley.change ( Smiley.Type.SMILE_GLASSES );
		// Check if the player beat a high score
		if ( this.highScores.eligible ( score ) ) {
			// Create a new JFrame that prompts user for name
			this.frame = new Window ( "High Score Entry", 100, 300, 2, 1 );
			// Add a label stating purpose
			this.frame.panel.add (
				new JLabel ( " Please enter your name:" ),
				this.frame.options ( 0, 0, 4.0, 1.0, GridBagConstraints.WEST )
			);
			// Create a text field and add it to panel
			this.inputName = new JTextField ( 15 );
			this.frame.panel.add (
				this.inputName,
				this.frame.options ( 0, 1, 2.0, 1.0, GridBagConstraints.WEST )
			);
			// Create a button for submitting and add it to panel
			this.submit = new JButton ( "Submit" );
			// Save necessary values for event handler
			this.submit.addMouseListener ( this );
			this.frame.panel.add (
				this.submit,
				this.frame.options ( 1, 1, 2.0, 1.0, GridBagConstraints.CENTER )
			);
			// Display the frame to user
			this.frame.display ();
		}
	}

	/**
	 * This function runs when the game is lost.  It stops the timer and changes the smiley face
	 * icon.
	 * @return  void
	 */
	protected void loseGame () {
		// Stop the timer
		this.timer.stop ();
		// Change the icon of the smiley
		this.smiley.change ( Smiley.Type.SMILE_DEAD );
	}

	/**
	 * This function gets run from the Grid class whenever the number of flags changes.  That value
	 * gets updated in the mines panel display by using the Display class helper function.
	 * @param   int             found               The number of mines left to find
	 * @return  void
	 * @see     Grid.java
	 * @see     Display.java
	 */
	protected void updateFoundGame ( int found ) {
		// Render it out into the mines Display instance
		this.mines.render ( found );
	}

	/**
	 * This function gets called from the Grid class and it starts the timer once the first move is
	 * completed within the Grid class.  It also has to be in a try catch loop in case a thread
	 * exception is thrown.
	 * @return  void
	 * @throw   IllegalThreadStateException
	 * @see     Grid.java
	 * @see     Timer.java
	 */
	protected void startTimerGame () {
		// Attempt to start the thread
		try {
			// Call the timer instance and trigger child thread to start
			this.timer.start ();
		}
		// If it is already started and we are trying to restart, catch
		catch ( IllegalThreadStateException exception ) {
			// Set flag to true
			Timer.run = true;
		}
	}

	/**
	 * This does not get run, but its sister functions do.  It is required for mouse listeners to
	 * have definitions for there functions in the same scope as the sister functions.
	 * @param   MouseEvent          e               The event object passed by the mouse listener
	 * @return  void
	 */
	@Override
	public void mouseExited ( MouseEvent e ) {}

	/**
	 * This does not get run, but its sister functions do.  It is required for mouse listeners to
	 * have definitions for there functions in the same scope as the sister functions.
	 * @param   MouseEvent          e               The event object passed by the mouse listener
	 * @return  void
	 */
	@Override
	public void mouseEntered ( MouseEvent e ) {}

	/**
	 * This does not get run, but its sister functions do.  It is required for mouse listeners to
	 * have definitions for there functions in the same scope as the sister functions.
	 * @param   MouseEvent          e               The event object passed by the mouse listener
	 * @return  void
	 */
	@Override
	public void mouseClicked ( MouseEvent e ) {

	}

	/**
	 * This function gets triggered by the mouse event listener. This function only triggers when there
	 * is a mouse down event, and it manages the smiley face icon.
	 * @param   MouseEvent          e               The event object passed by the mouse listener
	 * @return  void
	 */
	@Override
	public void mousePressed ( MouseEvent e ) {
		// Get the class type
		String name = e.getComponent ().getClass ().getName ();
		// Check if the left button click was triggered
		if ( e.getButton () == MouseEvent.BUTTON1 && name.equals ( "Smiley" ) ) {
			// Check if we can change it
			//if ( this.smiley.current != Smiley.Type.SMILE_START ) {
			//	return;
			//}
			// Change the smiley to the pressed icon
			this.smiley.change ( Smiley.Type.SMILE_PRESSED );
		}
		// Check if the right button click was triggered
		else if ( e.getButton () == MouseEvent.BUTTON3 && name.equals ( "Smiley" ) ) {
			// Check if we can change it
			if ( this.smiley.current != Smiley.Type.SMILE_START ) {
				return;
			}
			// Change the smiley to the "O" mouth icon
			this.smiley.change ( Smiley.Type.SMILE_O );
		}
	}

	/**
	 * This function gets triggered by the mouse event listener. Whenever there is a mouse release
	 * and if it deals with the Smiley class or the JButton that is used in the High Score form,
	 * then something happens.  This basically manages the smiley label icons and score submission.
	 * @param   MouseEvent          e               The event object passed by the mouse listener
	 * @return  void
	 */
	@Override
	public void mouseReleased ( MouseEvent e ) {
		// Get the class type
		String name = e.getComponent ().getClass ().getName ();
		// Check if the left button click was triggered
		if ( e.getButton () == MouseEvent.BUTTON1 && name.equals ( "Smiley" ) ) {
			// Check if we can change it
			if ( this.smiley.current != Smiley.Type.SMILE_PRESSED ) {
				return;
			}
			// Reset the smiley face to the default icon image
			this.smiley.change ( Smiley.Type.SMILE_START );
			// Restart the game by calling the internal restart function
			this.resetGame ();
		}
		// Check if the right button click was triggered
		else if ( e.getButton () == MouseEvent.BUTTON3 && name.equals ( "Smiley" ) && this.smiley.current == Smiley.Type.SMILE_O ) {
			// Check if we can change it
			if ( this.smiley.current != Smiley.Type.SMILE_O ) {
				return;
			}
			// Change smiley back to the default icon
			this.smiley.change ( Smiley.Type.SMILE_START );
		}

		//Check if left button was triggered on any button
		if ( e.getButton () == MouseEvent.BUTTON1 && name.equals ( "javax.swing.JButton" ) ) {
			JButton label = ( JButton ) e.getSource ();
			// Check to see that there is something in text area
			if ( this.inputName.getText ().trim ().equals ("") ) {
				return;
			}
			// Insert high score into data file
			this.highScores.insert ( this.score, this.inputName.getText () );
			// Close the currently open window
			this.frame.dispatchEvent ( new WindowEvent ( this.frame, WindowEvent.WINDOW_CLOSING ) );
			// Display the high scores leader board
			this.highScores.display ();
		}
	}

}