/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author Brendan
 */

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Piece {
    
    private String color;
    private boolean inPlay;
    private Coordinate coordinate;
    
    public Piece(String color, Coordinate c){
        this.color = color;
        this.coordinate = c;
        this.inPlay = true;
    }
    
    public Piece(String color, int x, int y){
        this.color = color;
        this.coordinate = new Coordinate(x,y);
        
    }
    
    public abstract ArrayList<Coordinate> getPossibleMoves();
        
    
    
}
