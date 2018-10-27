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
public class GridSpace {
    
    private boolean occupied;
    private Coordinate coordinate;
    private Piece piece;
    
    public GridSpace(Coordinate c){
        this.coordinate = c;
        this.occupied = false;
        this.piece = null;
    }
    
    public GridSpace(int x, int y){
        this(new Coordinate(x,y));
    }
    
    public boolean isOccupied(){
        return this.occupied;
    }
    
    public void occupy(){
        occupied = true;
    }
    public void unoccupy(){
        occupied = false;
    }
    public Coordinate getCoordinate(){
        return this.coordinate;
    }
    
    public Piece getPiece(){
        return this.piece;
    }
    public void setPiece(Piece p){
        this.piece = p;
    }
    
}
