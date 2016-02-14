import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Created by mrybak834 on 2/13/2016.
 */
public class Grid extends JPanel implements ActionListener {

    //Array of labels that creates the 10/10 game grid
    private JLabel[][] gridArray;

    //Constructs a 10x10 game grid
    Grid(){

        //Create a new 10x10 grid of labels that contains the game grid
        super(new GridLayout(10,10));
        gridArray = new JLabel[10][10];

        try {
            Image img = ImageIO.read(getClass().getResource("Images/button_normal.gif"));

            Image newimg = img.getScaledInstance(26,26, Image.SCALE_DEFAULT);
            ImageIcon imageIcon = new ImageIcon(newimg);
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){

                    gridArray[i][j] = new JLabel(imageIcon);
                    gridArray[i][j].setBorder(BorderFactory.createEmptyBorder());
                    this.add(gridArray[i][j]);
                }
            }
        } catch (IOException ex){}

    }

//    ///WILL DO IN GRID, SHOULD BE DELETED
//    //pBottom will be the Grid, blank for now
//    JButton gridLabel = new JButton("Grid");
//    gridLabel.setPreferredSize(new Dimension(300,300));
//    //Add Grid to the bottom panel
//    pBottom.add(gridLabel);
//    ///


    public void actionPerformed( ActionEvent event )
    {

    }
}
