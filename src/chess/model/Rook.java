
package chess.model;


public class Rook extends Piece {
    
    
    public Rook(String color,String name, Coordinate c){
        super(color,name,c);
    }
    public Rook(String color, String name, int x, int y){
        this(color,name, new Coordinate(x,y));
    }
}
