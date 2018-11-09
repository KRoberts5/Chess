
package chess.model;


public class Coordinate {
    
    private int x;
    private int y;
    
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setCoordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    
    
    public boolean equals(Coordinate c){
        
        int otherX = c.getX();
        int otherY = c.getY();
        
        return (x == otherX) && (y == otherY);
        
    }
    public String toString(){
        String output = "";
        output += "X: " + x;
        output += ", Y: " + y;
        
        return output;
    }
    
}
