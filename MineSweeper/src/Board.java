import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.event.WindowEvent;

public class Board extends JFrame implements MouseListener {

	private Container container;
	private JPanel display;
	private Grid grid;
	private Smiley smiley;
	private Display mines;
	private Timer timer;
	private HighScores highScores;

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
		this.display.setPreferredSize ( new Dimension ( 36, 200 ) );
		this.grid.setPreferredSize ( new Dimension ( 260, 260 ) );
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
		setSize ( 280, 342 );
		// Disable resizing of window
		setResizable ( false );
		// Change display options
		this.display.setBackground ( Color.lightGray );
		this.display.setBorder ( BorderFactory.createMatteBorder ( 0, 0, 0, 0, Color.lightGray ) );
		Dimension dim = Toolkit.getDefaultToolkit ().getScreenSize ();
		setLocation (
			dim.width / 2 - this.getSize ().width / 2,
			dim.height / 2 - this.getSize ().height / 2
		);
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
		// Display the high scores using the HighScores instance
		this.highScores.display ();
	}

	protected void actionResetGame () {
		// Call the reset game function internally
		this.resetGame ();
	}

	protected void actionHowToPlayHelp () {
		System.out.println ( "How to play the game" );
	}

	protected void actionAboutHelp () {
		JFrame frame = new JFrame("About");
		frame.setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE );
		JLabel header = new JLabel ( "Development Team:" );
		frame.add ( header );
		frame.setSize(300, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}

	protected void resetGame () {
		System.out.println ( "Restarting Game!" );
	}

	protected void winGame ( int score ) {
		// Stop the timer
		this.timer.stop ();
		// Change the icon of the smiley
		this.smiley.change ( Smiley.Type.SMILE_GLASSES );
		// Check if the player beat a high score
		if ( this.highScores.eligible ( score ) ) {
			// Create a new JFrame that prompts user for name
			Window frame = new Window ( "High Score Entry", 100, 300, 2, 1 );
			// Add a label stating purpose
			frame.panel.add (
				new JLabel ( " Please enter your name:" ),
				frame.options ( 0, 0, 4.0, 1.0, GridBagConstraints.WEST )
			);
			// Create a text field and add it to panel
			JTextField name = new JTextField ( 10 );
			frame.panel.add (
				name,
				frame.options ( 0, 1, 2.0, 1.0, GridBagConstraints.WEST )
			);
			// Create a button for submitting and add it to panel
			JButton submit = new JButton ( "Submit" );
			// Save necessary values for event handler
			submit.putClientProperty ( "frame", frame );
			submit.putClientProperty ( "name", name );
			submit.putClientProperty ( "score", score );
			submit.addMouseListener ( this );
			frame.panel.add (
				submit,
				frame.options ( 1, 1, 2.0, 1.0, GridBagConstraints.CENTER )
			);
			// Display the frame to user
			frame.display ();
		}
	}

	protected void loseGame () {
		// Stop the timer
		this.timer.stop ();
		// Change the icon of the smiley
		this.smiley.change ( Smiley.Type.SMILE_DEAD );
	}

	protected void updateFoundGame ( int found ) {
		// Render it out into the mines Display instance
		this.mines.render ( found );
	}

	protected void startTimerGame () {
		// Call the timer instance and trigger child thread to start
		this.timer.start ();
	}

	@Override
	public void mouseExited ( MouseEvent e ) {}

	@Override
	public void mouseEntered ( MouseEvent e ) {}

	@Override
	public void mouseClicked ( MouseEvent e ) {
		// Get the class type
		String name = e.getComponent ().getClass ().getName ();
		// Check if the left button click was triggered
		if ( e.getButton () == MouseEvent.BUTTON1 && name.equals ( "javax.swing.JButton" ) ) {
			JButton label = ( JButton ) e.getSource ();
   			JFrame frame = ( JFrame ) label.getClientProperty ("frame");
   			JTextField text = ( JTextField ) label.getClientProperty ("text");
   			int score = ( int ) label.getClientProperty ("score");
			frame.dispatchEvent ( new WindowEvent ( frame, WindowEvent.WINDOW_CLOSING ) );
			if ( text.getText () == null ) {
				System.out.println ( "IT WAS NULL.." );
				return;
			}
			this.highScores.insert ( score, text.getText () );
		}
	}

	@Override
	public void mousePressed ( MouseEvent e ) {
		// Get the class type
		String name = e.getComponent ().getClass ().getName ();
		// Check if the left button click was triggered
		if ( e.getButton () == MouseEvent.BUTTON1 && name.equals ( "Smiley" ) ) {
			// Check if we can change it
			if ( this.smiley.current != Smiley.Type.SMILE_START ) {
				return;
			}
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
			//this.winGame ( 400 );
		}
	}

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
	}

}