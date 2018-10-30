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
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

import chess.controller.*;
import chess.model.*;

public class ViewTrackingGrid extends JPanel implements AbstractView {
    private final DefaultController controller;
    
    private static final String ICON_ROOT = "/resources/images/";
    
    private Color beige;
    private Color brown;
    private Color green;
    
    private HashMap<String,ImageIcon> images; // Make Keys like "WHITE_ROOK"
    
    private ImageIcon whiteRook;
    private ImageIcon whiteKnight;
    private ImageIcon whiteBishop;
    private ImageIcon whiteKing;
    private ImageIcon whiteQueen;
    private ImageIcon whitePawn;
    private ImageIcon blackRook;
    private ImageIcon blackKnight;
    private ImageIcon blackBishop;
    private ImageIcon blackKing;
    private ImageIcon blackQueen;
    private ImageIcon blackPawn;
    
    private GridLabel[][] grid;
    
    public ViewTrackingGrid(DefaultController controller, int playerId) {
        super();
        this.controller = controller;        
        
        beige = new Color(239,227,178);
        brown = new Color(150,94,62);
        green = new Color(122,217,149);
        
        initComponents();
        initImages();
    }
    private void initComponents(){
        grid = new GridLabel[DefaultModel.MAX_SQUARE][DefaultModel.MAX_SQUARE];
        
        for(int i = 0; i < DefaultModel.MAX_SQUARE; ++i){
            for(int j = 0; j < DefaultModel.MAX_SQUARE; ++j){
                grid[i][j] = new GridLabel(this,i,j);
                if((i%2)==(j%2)){
                    grid[i][j].setBackground(brown);
                }
                else{
                    grid[i][j].setBackground(beige);
                }
            }
        }
        
    }
    private void initImages(){
        images = new HashMap();
    }
    
    private void spaceClicked(){
        
    }
    public void modelPropertyChange(final PropertyChangeEvent e){
        
        if(e.getPropertyName().equals(DefaultController.OCCUPY_SPACE)){
            int x = ((BoardSpace)e.getNewValue()).getCoordinate().getX();
            int y = ((BoardSpace)e.getNewValue()).getCoordinate().getY();
            String pieceType = ((BoardSpace)e.getNewValue()).getPiece().getType().toUpperCase();
            String color = ((BoardSpace)e.getNewValue()).getPiece().getColor().toUpperCase();
            
            String imageType = color + "_" + pieceType;
            ImageIcon image = images.get(imageType);
            grid[x][y].setIcon(image);
        }
        
    }
}
