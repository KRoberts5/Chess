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
public class Rook extends Piece {
    
    
    public Rook(String color, Coordinate c){
        super(color,c);
    }
    public Rook(String color, int x, int y){
        this(color, new Coordinate(x,y));
    }
}
