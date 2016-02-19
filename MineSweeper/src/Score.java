/**
 * Score.java - This class exists to hold information about a score.  It contains a string for the
 * name and and integer for the score.  This is strictly a data structure that holds information
 * about a players score.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #02 - MineSweeper
 * @category    HighScores
 * @author      Rafael Grigorian
 * @author      Marek Rybakiewicz
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Score {

	/**
	 * This data member holds the name of the player associated with the score.
	 * @var     String      name        The name associated with a given score
	 */
	protected String name;

	/**
	 * This data member holds the players score as an integer.
	 * @var     int         score       The players score
	 */
	protected int score;

	/**
	 * This constructor takes in a name and a score and sets in the information internally.
	 * @param   int         score       The player's score
	 * @param   String      name        The name of the player
	 */
	public Score ( int score, String name ) {
		// Save the data members internally
		this.name = name;
		this.score = score;
	}

	/**
	 * This function returns the score as a string, being constructed using the internal data
	 * members.
	 * @return  String                  Score as found in the data file
	 */
	protected String stringify () {
		// Construct the string and return it
		return this.score + "\t\t" + this.name;
	}

}