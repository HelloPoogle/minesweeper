import java.util.Random;

/**
 * Creates a coordinate point bound by the 10x10 grid
 * int x:   x coordinate
 * int y:   y coordinate
 */
public class Coordinate{
    private int x, y;

    //Creates a single coordinate on the 10x10 grid
    Coordinate(){
        Random rand = new Random();

        //Give two random coordinates between 0 and 9
        x = rand.nextInt(9);
        y = rand.nextInt(9);
    }

    //Provide x value
    public int getX(){
        return x;
    }

    //Provide y value
    public int getY() {
        return y;
    }
}
