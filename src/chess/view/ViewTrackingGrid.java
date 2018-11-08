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
    
    private boolean whitePlayerTurn;
    private boolean squareSelected;
    private boolean gameOver;
    
    private static final String ICON_ROOT = "/resources/images/";
    private static final String PNG = ".png";
    private static final String WHITE_PAWN = "white_pawn";
    private static final String WHITE_ROOK = "white_rook";
    private static final String WHITE_BISHOP = "white_bishop";
    private static final String WHITE_KNIGHT = "white_knight";
    private static final String WHITE_QUEEN = "white_queen";
    private static final String WHITE_KING = "white_king";
    private static final String BLACK_PAWN = "black_pawn";
    private static final String BLACK_ROOK = "black_rook";
    private static final String BLACK_BISHOP = "black_bishop";
    private static final String BLACK_KNIGHT = "black_knight";
    private static final String BLACK_QUEEN = "black_queen";
    private static final String BLACK_KING = "black_king";
    
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
    
    //private GridLabel[][] grid;
    private ArrayList<ArrayList<GridLabel>> labels;
    private GridLabel selectedSpace;
    
    public ViewTrackingGrid(DefaultController controller) {
        super();
        this.controller = controller;     
        this.whitePlayerTurn = true;
        this.squareSelected = false;
        this.gameOver = false;
        
        selectedSpace = null;
        
        //beige = new Color(239,227,178);
        beige = new Color(200,150,80);
       // brown = new Color(150,94,62);
        brown = new Color(150,94,62);
        green = new Color(122,217,149);
        
        initComponents();
        initImages();
    }
    private void initComponents(){
        
        this.setLayout(new GridLayout(DefaultModel.MAX_SQUARE,DefaultModel.MAX_SQUARE));
        this.setBackground(Color.BLACK);
        //grid = new GridLabel[DefaultModel.MAX_SQUARE][DefaultModel.MAX_SQUARE];
        labels = new ArrayList();
        ArrayList<GridLabel> row;
        GridLabel label;
        JPanel panel;
        
        for(int i = 0; i < DefaultModel.MAX_SQUARE; ++i){
            row = new ArrayList();
            for(int j = 0; j < DefaultModel.MAX_SQUARE; ++j){
                panel = new JPanel();
                label = new GridLabel(this,j,i);
                if((i%2)==(j%2)){
                    label.setBackground(brown);
                    label.setDefaultColor(brown);
                }
                else{
                    label.setBackground(beige);
                    label.setDefaultColor(beige);
                }
                
                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        selectSquare((GridLabel)(e.getSource()));
                    }
                    
                });
                panel.add(label);
                this.add(panel);
                row.add(label);
                
            }
            labels.add(row);
        }
        
        
        
    }
    private void initImages(){
        images = new HashMap();
        
        whitePawn = new ImageIcon(getClass().getResource(ICON_ROOT + WHITE_PAWN + PNG));
        whiteRook = new ImageIcon(getClass().getResource(ICON_ROOT + WHITE_ROOK + PNG));
        whiteKnight = new ImageIcon(getClass().getResource(ICON_ROOT + WHITE_KNIGHT + PNG));
        whiteBishop = new ImageIcon(getClass().getResource(ICON_ROOT + WHITE_BISHOP + PNG));
        whiteQueen = new ImageIcon(getClass().getResource(ICON_ROOT + WHITE_QUEEN + PNG));
        whiteKing = new ImageIcon(getClass().getResource(ICON_ROOT + WHITE_KING + PNG));
        
        images.put(WHITE_PAWN, whitePawn);
        images.put(WHITE_ROOK, whiteRook);
        images.put(WHITE_KNIGHT,whiteKnight);
        images.put(WHITE_BISHOP, whiteBishop);
        images.put(WHITE_QUEEN, whiteQueen);
        images.put(WHITE_KING, whiteKing);
        
        blackPawn = new ImageIcon(getClass().getResource(ICON_ROOT + BLACK_PAWN + PNG));
        blackRook = new ImageIcon(getClass().getResource(ICON_ROOT + BLACK_ROOK + PNG));
        blackKnight = new ImageIcon(getClass().getResource(ICON_ROOT + BLACK_ROOK + PNG));
        blackBishop = new ImageIcon(getClass().getResource(ICON_ROOT + BLACK_BISHOP + PNG));
        blackQueen = new ImageIcon(getClass().getResource(ICON_ROOT + BLACK_QUEEN + PNG));
        blackKing = new ImageIcon(getClass().getResource(ICON_ROOT + BLACK_KING + PNG));
        
        images.put(BLACK_PAWN, blackPawn);
        images.put(BLACK_ROOK, blackRook);
        images.put(BLACK_KNIGHT, blackKnight);
        images.put(BLACK_BISHOP, blackBishop);
        images.put(BLACK_QUEEN, blackQueen);
        images.put(BLACK_KING, blackKing);
        
    }
    
    private void selectSquare(GridLabel label){
        Coordinate c = label.getCoordinate();
        selectedSpace = label;
        if(squareSelected){
            
            if(label.getBackground().equals(green)){
                revertColor();
                squareSelected = false;
                
                controller.moveChosen(c);
            }
            else{
                revertColor();
                controller.squareSelected(c);
            }
        }
        else{
            squareSelected = true;
            controller.squareSelected(c);
        }
        
        
        
    }
    private void revertColor(){
        for(int i = 0; i < labels.size();++i){
            ArrayList<GridLabel> row = labels.get(i);
            
            for(int j = 0; j < row.size(); ++j){
                GridLabel label = row.get(j);
                
                if(label.getBackground().equals(green)){
                    label.setBackground(label.getDefaultColor());
                }
            }
        }
    }

    public void modelPropertyChange(final PropertyChangeEvent e){
        
        if(e.getPropertyName().equals(DefaultController.OCCUPY_SPACE)){
            int x = ((BoardSpace)e.getNewValue()).getCoordinate().getX();
            int y = ((BoardSpace)e.getNewValue()).getCoordinate().getY();
            String pieceType = ((BoardSpace)e.getNewValue()).getPiece().getType().toUpperCase();
            String color = ((BoardSpace)e.getNewValue()).getPiece().getColor().toUpperCase();
            
            String imageType = color.toLowerCase() + "_" + pieceType.toLowerCase();
            ImageIcon image = images.get(imageType);
            labels.get(y).get(x).setIcon(image);
            
            
        }
        
        if(e.getPropertyName().equals(DefaultController.MOVE_CHOSEN)){
            
            squareSelected = false;
            whitePlayerTurn = !whitePlayerTurn;
            
            int oldX = ((Coordinate)e.getOldValue()).getX(); 
            int oldY = ((Coordinate)e.getOldValue()).getY();
            int newX = ((Coordinate)e.getNewValue()).getX();
            int newY = ((Coordinate)e.getNewValue()).getY();
            
            ImageIcon piece = (ImageIcon)labels.get(oldY).get(oldX).getIcon();
            //labels.get(oldY).get(oldX).setIcon(null);
            
            labels.get(newY).get(newX).setIcon(piece);
            
            //labels.get(y).get(x).setIcon(null);
        }
        
        if(e.getPropertyName().equals(DefaultController.VALID_SQUARE_CHOSEN)){
            ArrayList<Coordinate> possibleMoves = (ArrayList<Coordinate>)e.getNewValue();
            
            for(int i = 0; i < possibleMoves.size(); ++i){
                Coordinate move = possibleMoves.get(i);
                int x = move.getX();
                int y = move.getY();
                
                GridLabel label = labels.get(y).get(x);
                label.setBackground(green);
            }
        }
        
        
    }
}
