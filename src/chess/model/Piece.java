
package chess.model;


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
        this(color,new Coordinate(x,y));  
    }
    
    
    public Coordinate getCoordinate(){
        return this.coordinate;
    }
    public String getColor(){
        return this.color;
    }
    
    public void setCoordinate(int x, int y){
        this.coordinate.setCoordinate(x, y);
    }
    
    

        
    
    
}
