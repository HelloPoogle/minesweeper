/**
 * Timer.java - This class extends from the Display class and has the same functionality.  It
 * returns a JPanel instance and is able to render the timer onto the screen.  This class is
 * unique in the fact that it contains static variables that keep track of the time and also spins
 * off a child thread that enables the functionality of sleeping for a second, incrementing the
 * time, and rendering it in the GUI until we stop the clock.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #02 - MineSweeper
 * @category    Timer
 * @author      Rafael Grigorian
 * @author      Marek Rybakiewicz
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Timer extends Display {

	/**
	 * This data member saves the instance of the thread object in order to start it in a separate
	 * function.
	 * @var     Thread          thread          Instance of thread used for counting
	 */
	protected Thread thread;

	/**
	 * This data member keeps track of the time in seconds and is static so we can access it within
	 * the child thread.
	 * @var     int             time            Time elapsed from initial in seconds
	 */
	protected static int time;

	/**
	 * This data member is a flag that the child thread uses to determine whether or not to loop
	 * every second and increment the timer.
	 * @var     boolean         run             A flag to break the time loop in child thread
	 */
	protected static boolean run;

	/**
	 * This constructor first runs the parent constructor and then initializes the flag, timer to
	 * the initial value, and finally sets up the child thread.
	 * @param   int             initial         The initial value for the timer
	 */
	public Timer ( int initial ) {
		// Run parent constructor and pass initial value
		super ( initial );
		// Initialize the time to initial
		this.time = initial;
		// Initialize run flag to true initially
		this.run = true;
		// Initialize the thread and runnable function
		initialize ();
	}

	/**
	 * This function initializes the runnable function that the child thread runs.  This function
	 * basically loops until the flag is set to false, and sleep, increments, and renders the timer
	 * every iteration.
	 * @return  void
	 */
	protected void initialize () {
		// Initialize a runnable instance function
		Runnable runnable = new Runnable () {
			@Override
			public void run () {
				// Loop forever until we stop the thread
				while ( true ) {
					// Try to put the thread to sleep
					try {
						// Sleep for a second
						Thread.sleep ( 1000 );
					}
					// Catch exception if it arises
					catch ( InterruptedException exception ) {
						// Print out the stack trace on failure
						exception.printStackTrace ();
					}
					// Check again if the flag is true after sleep
					if ( Timer.run ) {
						// Increment the timer and render it in the GUI
						render ( Timer.time++ );
					}
				}
			}
		};
		// Create a thread and bind it to the runnable function
		this.thread = new Thread ( runnable );
	}

	/**
	 * This function starts the timer and spins off the child thread to start incrementing the
	 * clock.
	 * @return  void
	 */
	protected void start () {
		// Start the thread and start timer
		this.thread.start ();
	}

	/**
	 * Set the flag to false and make the child thread stop looping.
	 * @return  void
	 */
	protected void stop () {
		// Stop the thread and stop timer
		Timer.run = false;
	}

}