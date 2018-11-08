/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import java.applet.*;
import chess.controller.*;
public class ViewMainWindow extends JFrame implements AbstractView{
    
    DefaultController controller;
    ViewTrackingGrid grid;

    
    public ViewMainWindow(DefaultController controller,ViewTrackingGrid grid){
        super("Chess");
        
        this.controller = controller;
        this.grid = grid;
        initComponents();
        
    }
    
    private void initComponents(){
        this.setLayout(new FlowLayout());
        this.getContentPane().add(grid);
    }
    
    public void modelPropertyChange(PropertyChangeEvent e){
        if(e.getPropertyName().equals(DefaultController.GAME_OVER_WHITE_WON) ||  e.getPropertyName().equals(DefaultController.GAME_OVER_BLACK_WON)){
            String playerColor = "";
            if(e.getPropertyName().equals(DefaultController.GAME_OVER_WHITE_WON))
                playerColor = "White";
            else
                playerColor = "Black";
            
            JOptionPane.showMessageDialog(this, "Congratulations " + playerColor + " Player "  + "!\n\nYou Won the Game!");
            this.dispose();
        }
    }
}
