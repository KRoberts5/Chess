/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.view;

/**
 *
 * @author Brendan
 */
import java.awt.*;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import chess.controller.*;
import chess.model.*;
public class ViewStartMenu extends JPanel implements AbstractView{
    
    private JPanel choices;
    private JPanel start;
    
    private String gameType;
    
    private DefaultController controller;
    
    
    public ViewStartMenu(DefaultController controller){
        
        this.controller = controller;
        this.gameType = DefaultModel.PVP;
        
        this.initComponents();
    }
    
    private void initComponents(){
        
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        
        this.start = new JPanel();
        
        JButton button = new JButton("Start");
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                startGame();
            }
        });
        start.add(button);
        
        this.add(start,BorderLayout.SOUTH);
        
    }
    
    private void startGame(){
        controller.startGame(gameType);
    }
    
    public void modelPropertyChange(PropertyChangeEvent e){
        
    }
}
