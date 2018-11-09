
package chess;
import chess.model.*;
import chess.controller.*;
import chess.view.*;
import java.awt.EventQueue;
import java.awt.Dimension;


public class Chess {

    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        DefaultModel model = new DefaultModel();
        DefaultController c = new DefaultController();
        ViewTrackingGrid grid = new ViewTrackingGrid(c);
        ViewStartMenu menu = new ViewStartMenu(c);
        
        c.addModel(model);
        c.addView(grid);
        c.addView(menu);
        
        EventQueue.invokeLater(() -> {
        
            ViewMainWindow window = new ViewMainWindow(c, grid,menu);
            c.addView(window);
            
            /* Set JFrame Properties */
            
            window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            window.setPreferredSize(new Dimension(512,512));
            
            window.setResizable(false);
            window.pack();
            window.setVisible(true);
        });
        
        
        
        //System.out.println(model);
        
        
    }
    
}
