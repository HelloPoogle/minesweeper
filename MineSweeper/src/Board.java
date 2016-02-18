import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JFrame {

	private Container container;
	private JPanel display;
	private Grid grid;

	protected Board () {
		// Pass title to JFrame constructor
		super ( "MineSweeper" );
		// Make the close button terminate the game
		setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
		// Create a container for the window
		this.container = getContentPane ();
		// Apply the container layout onto the programs window
		this.container.setLayout ( new BorderLayout () );
		// Construct new menu object
		JMenuBar menu = createMenuBar ();
		// Break up main body into two parts, the grid and the display
		this.display = new JPanel ( new GridLayout ( 1, 3 ) );
		this.grid = new Grid ( this );
		// Set the size of both of the 
		this.display.setPreferredSize ( new Dimension ( 100, 200 ) );
		this.grid.setPreferredSize ( new Dimension ( 260, 260 ) );
		// Define the main sections of the display portion
		JLabel minesDisplay = new JLabel ( "Mines", SwingConstants.CENTER );
		JButton smileyDisplay = new JButton ( "Smiley" );
		JLabel timeDisplay = new JLabel ( "Time", SwingConstants.CENTER );
		// Add these elements to the top display section
		this.display.add ( minesDisplay, BorderLayout.WEST );
		this.display.add ( smileyDisplay, BorderLayout.CENTER );
		this.display.add ( timeDisplay, BorderLayout.EAST );
		// Set the borders for both the grid and the display
		this.display.setBorder ( BorderFactory.createLoweredBevelBorder () );
		this.grid.setBorder (
			BorderFactory.createStrokeBorder ( new BasicStroke ( 5 ), Color.lightGray )
		);
		// Add both panels to the container
		this.container.add ( this.display, BorderLayout.CENTER );
		this.container.add ( this.grid, BorderLayout.SOUTH );
		// Add the menu into our window package
		this.container.add ( menu, BorderLayout.PAGE_START );
		// Set the window size accordingly
		setSize ( 280, 380 );
		// Show the window to render onto screen
		setVisible ( true );
	}

	protected JMenuBar createMenuBar () {
		// Initialize the menu bar within the program's menu
		JMenuBar menu = new JMenuBar ();
		// Initialize main menus
		JMenu dropDownGame = new JMenu ( "Game" );
		JMenu dropDownHelp = new JMenu ( "Help" );
		// Initialize sub menu, high scores, bind tool tip, and callback function
		JMenuItem highScores = new JMenuItem ( "High Scores" );
		highScores.setToolTipText ( "Display Top-Ten Scores" );
		highScores.addActionListener ( new ActionListener () {
			@Override
			public void actionPerformed ( ActionEvent event ) {
				actionHighScores ();
			}
		});
		// Initialize sub menu, reset game, bind tool tip, and callback function
		JMenuItem resetGame = new JMenuItem ( "Reset Game" );
		resetGame.setToolTipText ( "Reset Current Game" );
		resetGame.addActionListener ( new ActionListener () {
			@Override
			public void actionPerformed ( ActionEvent event ) {
				actionResetGame ();
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
		JMenuItem howToPlayHelp = new JMenuItem ( "How To Play" );
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
		dropDownGame.add ( exitGame );
		// Add all of the sub menus to the help menu
		dropDownHelp.add ( howToPlayHelp );
		dropDownHelp.add ( aboutHelp );
		// Add both drop-downs to the menu object
		menu.add ( dropDownGame );
		menu.add ( dropDownHelp );
		// Return the menu object
		return menu;
	}

	protected void actionHighScores () {
		System.out.println ( "Displaying High Scores!" );
	}

	protected void actionResetGame () {
		System.out.println ( "Resetting Game!" );
	}

	protected void actionHowToPlayHelp () {
		System.out.println ( "How to play the game" );
	}

	protected void actionAboutHelp () {
		System.out.println ( "About page" );
	}

	protected void winGame ( int score ) {

	}

	protected void loseGame () {

	}

	protected void updateFoundGame ( int found ) {
		
	}

	protected void startTimerGame () {

	}

}