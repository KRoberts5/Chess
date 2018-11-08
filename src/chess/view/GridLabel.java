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
import java.awt.image.BufferedImage;
import javax.swing.*;
import chess.model.Coordinate;

public class GridLabel extends JLabel{
    
    private final Coordinate COORDINATE;
    private static final int WIDTH = 64;
    private Color defaultColor;
    
    public GridLabel(AbstractView parent, int x, int y) {
        
        super("", SwingConstants.CENTER);
        
        
        this.COORDINATE = new Coordinate(x,y);
        this.defaultColor = Color.BLACK;
        
        /* Initialize JLabel Properties */
        
        this.setPreferredSize(new Dimension(WIDTH, WIDTH));
        this.setForeground(Color.WHITE);
        this.setOpaque(true);
        this.setVisible(true);
        
    }
    
    public void setDefaultColor(Color c){
        this.defaultColor = c;
    }
    public Color getDefaultColor(){
        return this.defaultColor;
    }
    public Coordinate getCoordinate(){
        return this.COORDINATE;
    }
    public int getX(){
        return this.COORDINATE.getX();
    }
    public int getY(){
        return this.COORDINATE.getY();
    }
}
