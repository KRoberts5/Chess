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
    
    public static final String MENU = "Menu";
    public static final String GRID = "Grid";
    
    DefaultController controller;
    ViewTrackingGrid grid;
    ViewStartMenu menu;
    
    ViewContainer c1, c2;
    
    JPanel container, cards;

    
    public ViewMainWindow(DefaultController controller,ViewTrackingGrid grid, ViewStartMenu menu){
        super("Chess");
        
        this.controller = controller;
        this.grid = grid;
        this.menu = menu;
        initComponents();
        
        
        showCard(MENU);
    }
    
    private void initComponents(){

        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(Color.BLACK);
        
        c1 = new ViewContainer(menu);
        
        c2 = new ViewContainer(grid);
        
        cards = new JPanel();
        cards.setLayout(new CardLayout());
        cards.setBackground(Color.BLUE);
        
        JPanel card1 = new JPanel();
        card1.setLayout(new BorderLayout());
        card1.add(c1,BorderLayout.CENTER);
        card1.setVisible(false);
        
        JPanel card2 = new JPanel();
        card2.setLayout(new BorderLayout());
        card2.add(c2,BorderLayout.CENTER);
        card2.setVisible(false);
        
        cards.add(card1,MENU);
        cards.add(card2,GRID);
        
        container.add(cards,BorderLayout.CENTER);
        
        this.getContentPane().add(container);
        //this.getContentPane().add(grid);
        
    }
    
    public void startGame(){
        showCard(GRID);
    }
    
    private void showCard(String name){
        CardLayout c1 = (CardLayout)(cards.getLayout());
        c1.show(cards,name);
    }
    
    public void modelPropertyChange(PropertyChangeEvent e){
        
        
        if(e.getPropertyName().equals(DefaultController.GAME_START)){
            showCard(GRID);
        }
        
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
