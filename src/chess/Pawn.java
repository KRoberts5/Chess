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
public class Pawn extends Piece{
    
    private boolean unmoved;
    
    public Pawn(String color, Coordinate c){
        super(color,c);
        unmoved = true;
    }
    public Pawn(String color, int x, int y){
        this(color,new Coordinate(x,y));
    }
    
    public boolean unmoved(){
        return this.unmoved;
    }
    
    
}
