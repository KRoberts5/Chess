
package chess.model;



import java.util.ArrayList;
public class Pawn extends Piece{
    
    
    public Pawn(String color,String name, Coordinate c){
        super(color,name,Piece.PAWN,c);
    }
    public Pawn(String color,String name, int x, int y){
        this(color,name,new Coordinate(x,y));
    }
    
    
}
