import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Smiley.java - This class extends a JLabel and acts as the smiley face icon in the game.  It
 * caches all the available images on start time and also contains an enum class that is static
 * and available throughout the program.  There are also functions that help convert the enum to an
 * integer and to the filepath.  There is also a function that is used to change the icon of the
 * smiley face.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #02 - MineSweeper
 * @category    Smiley
 * @author      Rafael Grigorian
 * @author      Marek Rybakiewicz
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Smiley extends JLabel {

	/**
	 * This data member holds an array of images associated with the smiley face.  These images are
	 * cached to improve render time.
	 * @var     ImageIcon []    icons           An array of caches images
	 */
	protected ImageIcon [] icons;

	/**
	 * This data member saves the enum representation of the current state.
	 * @var 	Type 			current
	 */
	protected Type current;

	/**
	 * This enum is static and describes the current state of the smiley label in the main program.
	 * @enum    Type                            Describes a smiley state
	 */
	public static enum Type {
		SMILE_START,
		SMILE_DEAD,
		SMILE_GLASSES,
		SMILE_O,
		SMILE_PRESSED;
	}

	/**
	 * This constructor takes in an enum that describes the initial state of the smiley, and sets
	 * that icon appropriately.  Before that though, this function initializes the image array and
	 * caches all the possible smiley icons internally.
	 * @param   Type            initial         The initial state of the smiley label
	 */
	Smiley ( Type initial ) {
		// Call the super constructor for JLabel
		super ( "", SwingConstants.CENTER );
		// Initialize image icon array
		this.icons = new ImageIcon [ 5 ];
		// Create instances of image icons
		try {
			// Loop through all possible digit images
			for ( int i = 0; i < 5; i++ ) {
				// Read the image and initialize digits array
				Image image = ImageIO.read (
					getClass ().getResource ( Smiley.filepath ( i ) )
				);
				// Set image to index of icon array
				this.icons [ i ] = new ImageIcon ( image );
			}
		}
		// Try to catch the exception
		catch ( IOException exception ) {
			// Warn user and quit program
			System.out.println ("ERROR:\tCould not load image files!");
			System.exit ( 0 );
		}
		// Set the current icon
		setIcon ( this.icons [ Smiley.index ( initial ) ] );
		this.current = initial;
	}

	/**
	 * This function is static and converts a passed Type enum to the corresponding integer value
	 * that is associated with the index at which it can be found in the Type enum class.
	 * @param   Type        target              The Type enum to evaluate
	 * @return  int                             The corresponding index in the Type enum
	 */
	protected static int index ( Type target ) {
		// Use a switch table to decide value
		switch ( target ) {
			case SMILE_START:
				return 0;
			case SMILE_DEAD:
				return 1;
			case SMILE_GLASSES:
				return 2;
			case SMILE_O:
				return 3;
			case SMILE_PRESSED:
				return 4;
			// By default, we will return the start state
			default:
				return 0;
		}
	}

	/**
	 * This function is static and converts a corresponding integer index to the file path of the
	 * states associated icon image.
	 * @param   int         index           The index to evaluate
	 * @return  String                      The file path associated with indexed Type enum state
	 */
	protected static String filepath ( int index ) {
		// Use a switch table to decide value
		switch ( index ) {
			case 0:
				return "Images/headSmileStart.gif";
			case 1:
				return "Images/headDead.gif";
			case 2:
				return "Images/headGlasses.gif";
			case 3:
				return "Images/headO.gif";
			case 4:
				return "Images/headSmilePressed.gif";
			// by default we will return null as raise exception in parent
			default:
				return null;
		}
	}

	/**
	 * This function takes in a target Type enum, and changes the label's icon to the corresponding
	 * target icon.
	 * @param   Type        target          The type of state to change to
	 * @return  void
	 */
	protected void change ( Type target ) {
		// Set the icon to be whatever was specified by the target enum
		setIcon ( this.icons [ Smiley.index ( target ) ] );
		// Change the type to current
		this.current = target;
	}

}