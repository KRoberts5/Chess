
package chess;
import chess.model.*;
import chess.controller.*;
import chess.view.*;
import java.awt.EventQueue;


public class Chess {

    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        DefaultModel model = new DefaultModel();
        DefaultController c = new DefaultController();
        ViewTrackingGrid grid = new ViewTrackingGrid(c);
        
        c.addModel(model);
        c.addView(grid);
        
        EventQueue.invokeLater(() -> {
        
            ViewMainWindow window = new ViewMainWindow(c, grid);
            c.addView(window);
            
            /* Set JFrame Properties */
            
            window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.pack();
            window.setVisible(true);
            
            /* Initialize Model */
            
            model.initDefaults();
            
        });
        
        
        
        //System.out.println(model);
        
        
    }
    
}
