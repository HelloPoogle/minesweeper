import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * JunitRunner.java - Runs through all of the tests in the JunitTest class,
 * and prints any failures to the command line.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #02 - MineSweeper
 * @category    Tests
 * @author      Rafael Grigorian
 * @author      Marek Rybakiewicz
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class JunitRunner {

    public static void main(String[] args) {

            Result result = JUnitCore.runClasses(JunitTest.class);
            for (Failure fail : result.getFailures()) {
                System.out.println(fail.toString());
            }
            if (result.wasSuccessful()) {
                System.out.println("All tests finished successfully...");
            }

    }

}