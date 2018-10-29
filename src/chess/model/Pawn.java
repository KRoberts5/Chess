
package chess.model;



import java.util.ArrayList;
public class Pawn extends Piece{
    
    private boolean unmoved;
    
    public Pawn(String color,String name, Coordinate c){
        super(color,name,c);
        unmoved = true;
    }
    public Pawn(String color,String name, int x, int y){
        this(color,name,new Coordinate(x,y));
    }
    
    public boolean unmoved(){
        return this.unmoved;
    }
    
    
}
