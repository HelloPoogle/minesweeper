import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JFrame;

/**
 * HighScores.java - This class is in charge of loading, retrieving, and inserting high scores into
 * a file.  If the file doesn't exist it will create one.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #02 - MineSweeper
 * @category    HighScores
 * @author      Rafael Grigorian
 * @author      Marek Rybakiewicz
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class HighScores {

	/**
	 * This data member holds the file name of the data file that contains the high scores.
	 * @var     String      filePath        The path to the data file
	 */
	protected String filePath;

	/**
	 * This data member specifies the maximum amount of scores to keep track of
	 * @var     int         max             Max number of scores to keep
	 */
	protected int max;

	/**
	 * This data member holds an array of Score data structures.  It will be populated on
	 * initialization.
	 * @var     Score []    scores          High score data within a Score array
	 */
	protected Score [] scores;

	/**
	 * This constructor takes in a file path and starts to load the file, if the file does not
	 * exist, then it creates one.
	 * @param   String      filepath        The file path to the data file
	 * @param   int         max             The max amount of scores to keep
	 */
	protected HighScores ( String filepath, int max ) {
		// Save the file path internally
		this.filePath = filepath;
		// Save how many scores to keep
		this.max = max;
		// Initialize the scores array
		this.scores = new Score [ this.max ];
		// Attempt to load the file, if we can't load anything, then save the file and create it
		if ( !this.load () ) {
			// Save the high scores ( empty ) into the specified file
			this.save ();
		}
	}

	/**
	 * This function attempts to load the data file and populates the Score [] array.  If the file
	 * exists, then true is returned, otherwise we return false.
	 * @return  boolean                     If the file exists
	 * @throw   FileNotFoundException       When there are any problems, ignore
	 */
	protected boolean load () {
		// Initialize file and load file descriptor
		File file = new File ( this.filePath );
		// Check if the file exists and isn't a directory
		if ( file.exists () && !file.isDirectory () ) {
			// Try to scan the file
			try {
				// Create a scanner instance using the file descriptor
				Scanner scan = new Scanner ( file );
				// Initialize counter
				int counter = 0;
				// While the there is still another line
				while ( scan.hasNextLine () ) {
					// Create a new instance of a Score object
					Score score = new Score (
						scan.nextInt (),
						scan.nextLine ().trim ()
					);
					// Append this score to the array
					this.scores [ counter ] = score;
					// Increment counter
					counter++;
				}
				// Close the scanner
				scan.close ();
				// Return true, because we loaded the file
				return true;
			}
			// If we catch an exception we will return false
			catch ( FileNotFoundException exception ) {
				return false;
			}
		}
		// If file path is invalid, return false
		return false;
	}

	/**
	 * This function turns the internal Scores array into strings and saves them into the specified
	 * data file.  It may also throw an exception.  If the file doesn't exist, it makes one.
	 * @return  void
	 * @throw   FileNotFoundException
	 */
	protected void save () {
		// Attempt to save the file in a try catch method
		try {
			// Initialize writer
			PrintWriter writer = new PrintWriter ( this.filePath );
			// Loop through all the scores
			for ( int i = 0; i < this.scores.length; i++ ) {
				// Check that the object is not null
				if ( this.scores [ i ] != null ) {
					// Write in the current score as a string
					writer.println ( this.scores [ i ].stringify () );
				}
				// Otherwise, just break
				else {
					// Break out of this loop
					break;
				}
			}
			// Close the file
			writer.close ();
		}
		// Catch file exception, even though we don't need to in this case
		catch ( FileNotFoundException exception ) { }
	}

	/**
	 * This function returns a boolean that specifies whether the currently passed score is
	 * eligible to be inserted into the high scores board.
	 * @param   int         score           The score to evaluate
	 * @return  boolean                     If it is eligible to be inserted
	 */
	protected boolean eligible ( int score ) {
		// Loop through the scores array within the max bound
		for ( int i = 0; i < this.max; i++ ) {
			// Check to see if the object is null
			if ( this.scores [ i ] == null ) {
				// If it is then break and return false
				return true;
			}
			// Otherwise check to see if the score is equal or higher then current
			else if ( score >= this.scores [ i ].score ) {
				// If it is then return true
				return true;
			}
		}
		// by default return false
		return false;
	}

	/**
	 * This function inserts a new score into the high scores list, if eligible.  Once it's
	 * inserted, it will save it and return the new instance.  If it's not eligible, then it will
	 * return null.  The score will be inserted above a score if the score is equal.
	 * @param   int         score       The score to try to insert
	 * @param   String      name        The associated name with the score
	 * @return  Score                   The newly created Score, or null if not eligible
	 */
	protected Score insert ( int score, String name ) {
		// Check to see if the score is eligible to be inserted into high scores board
		if ( this.eligible ( score ) ) {
			// Create the new Score instance
			Score newScore = new Score ( score, name );
			// Initialize insertion position
			int position = 0;
			// Iterate through and find the position
			for ( int i = 0; i < this.max; i++ ) {
				// Check to see if index is null or if it is eligible
				if ( this.scores [ i ] == null || score >= this.scores [ i ].score ) {
					// Save position and break
					position = i;
					break;
				}
			}
			// Temp variable
			Score temp = null;
			Score temp2 = null;
			// Loop though again to copy
			for ( int i = 0; i < this.max; i++ ) {
				// If its the current position
				if ( i == position ) {
					temp = this.scores [ i ];
					this.scores [ i ] = newScore;
				}
				// If we already inserted
				else if ( i > position ) {
					temp2 = this.scores [ i ];
					this.scores [ i ] = temp;
					temp = temp2;
				}
			}
			// Save the data into the data file
			this.save ();
			// Return the new instance of Score
			return newScore;
		}
		// Otherwise if not eligible, then return null
		return null;
	}

	/**
	 * This function resets the scores and truncates the file.  This is done by internally
	 * replacing the scores array and saving the emptiness into the data file.
	 * @return  void
	 */
	protected void reset () {
		// Reset the scores array
		this.scores = new Score [ this.max ];
		// Save the file to truncate it
		this.save ();
	}

	/**
	 * This function creates a new window using the Window class and then populates it with high
	 * score information.
	 * @return  void
	 * @see     Window.java
	 */
	protected void display () {
		// Create a JFrame
		Window frame = new Window ( "High Scores", 300, 300, 12, 3 );
		// Create header labels
		frame.panel.add (
			frame.bold ( new JLabel ( "Score" ) ),
			frame.options ( 1, 0, 2.0, 1.0, GridBagConstraints.CENTER )
		);
		frame.panel.add (
			frame.bold ( new JLabel ( "Name" ) ),
			frame.options ( 2, 0, 2.0, 1.0, GridBagConstraints.CENTER )
		);
		// Loop through high scores and print out each record
		for ( int i = 0; i < this.max; i++ ) {
			// Initialize some temp variables
			String score = "N/A";
			String name = "<EMPTY>";
			// If its not empty, then change temp variables
			if ( this.scores [ i ] != null ) {
				score = this.scores [ i ].score + "";
				name = this.scores [ i ].name;
			}
			// Set the values for the row record
			frame.panel.add (
				frame.bold ( new JLabel ( i + 1 + ":" ) ),
				frame.options ( 0, i + 1, 1.0, 1.0, GridBagConstraints.CENTER )
			);
			frame.panel.add (
				new JLabel ( score ),
				frame.options ( 1, i + 1, 2.0, 1.0, GridBagConstraints.CENTER )
			);
			frame.panel.add (
				new JLabel ( name ),
				frame.options ( 2, i + 1, 2.0, 1.0, GridBagConstraints.CENTER )
			);
		}
		// Display the frame
		frame.display ();
	}

}