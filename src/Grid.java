import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 * Created by mrybak834 on 2/13/2016.
 */
public class Grid extends JPanel implements ActionListener {
    Grid(){
        super(new GridLayout(1,1));

        JButton grid = new JButton("Grid");
        grid.setPreferredSize((new Dimension(300,300)));
        add(grid);
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
