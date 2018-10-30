
package chess.model;


import java.util.ArrayList;
import java.util.HashMap;

public abstract class Piece {
    
    private String color;
    private Coordinate coordinate;
    private String name;
    
    public Piece(String color, String name, Coordinate c){
        this.color = color;
        this.name = name;
        this.coordinate = c;
    }
    
    public Piece(String color,String name, int x, int y){ 
        this(color,name,new Coordinate(x,y));  
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
    public String getName(){
        return this.name;
    }
    
    

        
    
    
}
