
package chess.model;


public class Rook extends Piece {
    
    
    public Rook(String color, Coordinate c){
        super(color,c);
    }
    public Rook(String color, int x, int y){
        this(color, new Coordinate(x,y));
    }
}
