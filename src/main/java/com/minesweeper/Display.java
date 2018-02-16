package com.minesweeper;

import java.awt.Image;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * Display.java - This class creates a JPanel instance that renders to become the display for the
 * timer or the mine count.  It has a render function for quick and simple use.  It also initially
 * caches all the images to use, so we don't have to prolong the load time during runtime and
 * render time.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #02 - MineSweeper
 * @category    Display
 * @author      Rafael Grigorian
 * @author      Marek Rybakiewicz
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Display {

	/**
	 * This data member represents the current value on the display.
	 * @var     int             current         The current value on display
	 */
	protected int current;

	/**
	 * This data member holds the three labels held inside the JPanel.
	 * @var     JLabel []       labels          Three labels representing three digits
	 */
	protected JLabel [] labels;

	/**
	 * This data member holds image icon files, so we don't load each time on render.
	 * @var     ImageIcon []    digits          Cached images for each numerical digit
	 */
	private ImageIcon [] digits;

	/**
	 * This holds the instance of the JPanel that we create in the constructor.
	 * @var     JPanel          display         Instance of the returned JPanel object
	 */
	private JPanel display;


	/**
	 * This constructor initializes all the labels, and caches all the digits necessary for the
	 * display GUI.  It also renders the initial passed value onto the display.
	 * @param   int             initial         The initial value to render
	 * @throw   IOException
	 */
	public Display ( int initial ) {
		// Initialize JLabel array
		this.labels = new JLabel [ 3 ];
		// initialize image icon array
		this.digits = new ImageIcon [ 11 ];
		// Create instances of image icons
		try {
			// Loop through all possible digit images
			for ( int i = 0; i < 11; i++ ) {
				// Read the image and initialize digits array
				Image image = ImageIO.read (
					getClass ().getResource ( "/images/countdown" + ( i - 1 ) + ".gif" )
				);
				this.digits [ i ] = new ImageIcon ( image );
			}
		}
		// Try to catch the exception
		catch ( IOException exception ) {
			// Warn user and quit program
			System.out.println ("ERROR:\tCould not load image files!");
			System.exit ( 0 );
		}
		// Create three instances of JLabel
		for ( int i = 0; i < 3; i++ ) {
			this.labels [ i ] = new JLabel ( this.digits [ 0 ] );
		}
		// Initialize a new JPanel and save it
		this.display = new JPanel ( new GridBagLayout () );
		this.display.setBackground ( Color.lightGray );
		// Add each label to the display
		for ( int i = 0; i < 3; i++ ) {
			this.display.add ( this.labels [ i ] );
		}
		// Initially render the amount
		this.render ( initial );
	}

	/**
	 * This function simply returns the private JPanel instance that is stored internally.
	 * @return  JPanel                          Internally stored JPanel instance
	 */
	protected JPanel getPanel () {
		// Return the instance of the stored JPanel
		return this.display;
	}

	/**
	 * This function renders a passed value onto the display.  It also internally saves the current
	 * value internally.
	 * @param   int             value           The value to render
	 * @return  void
	 */
	protected void render ( int value ) {
		// Update current value
		this.current = value;
		// Get the digit for each numbers place
		int hundred = value / 100;
		int ten = value % 100 / 10;
		int one = value % 10;
		// If the current value is negative
		if ( this.current < 0 ) {
			// Display a minus sign
			hundred = 0;
			// Get the absolute value of the number
			value = Math.abs ( value );
			ten = value % 100 / 10;
			one = value % 10;
			// Update each label based on numbers place
			this.labels [ 0 ].setIcon ( this.digits [ hundred ] );
			this.labels [ 1 ].setIcon ( this.digits [ ten + 1 ] );
			this.labels [ 2 ].setIcon ( this.digits [ one + 1 ] );
		}
		// Otherwise the current value is positive
		else {
			// Update each label based on numbers place
			this.labels [ 0 ].setIcon ( this.digits [ hundred + 1 ] );
			this.labels [ 1 ].setIcon ( this.digits [ ten + 1 ] );
			this.labels [ 2 ].setIcon ( this.digits [ one + 1 ] );
		}

	}

}
