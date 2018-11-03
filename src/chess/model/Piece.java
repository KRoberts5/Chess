
package chess.model;


import java.util.ArrayList;
import java.util.HashMap;

public abstract class Piece {
    
    public static final String ROOK = "ROOK";
    public static final String KNIGHT = "KNIGHT";
    public static final String BISHOP ="BISHOP";
    public static final String KING = "KING";
    public static final String QUEEN = "QUEEN";
    public static final String PAWN = "PAWN";
    
    private String color;
    private Coordinate coordinate;
    private String name;
    private String type;
    private boolean unmoved;
    
    public Piece(String color, String name,String type, Coordinate c){
        this.color = color;
        this.name = name;
        this.coordinate = c;
        this.type = type;
        this.unmoved = true;
    }
    
    public Piece(String color,String name,String type, int x, int y){ 
        this(color,name,type,new Coordinate(x,y));  
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
    public String getType(){
        return type;
    }
    
    public boolean isUnmoved(){
        return unmoved;
    }
    public void move(){
        unmoved = false;
    }
    
    

        
    
    
}
