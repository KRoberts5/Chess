
package chess.model;



import java.util.ArrayList;

public class Player {
    
    private String color;
    private ArrayList<Piece> inPlayPieces;
    private ArrayList<Piece> capturedPieces;
    
    public Player(String color){
        this.color = color;
        inPlayPieces = new ArrayList();
        capturedPieces = new ArrayList();
        this.initializePieces();
    }

    private void initializePieces(){

        int rowMin = 0;
        int rowMax = 0;
        if(this.color.equals(DefaultModel.WHITE)){
            rowMin = DefaultModel.INIT_BLACK_ROW_MIN;
            rowMax = DefaultModel.INIT_WHITE_ROW_MAX;
        }
        else{
            rowMin = DefaultModel.INIT_BLACK_ROW_MIN;
            rowMax = DefaultModel.INIT_BLACK_ROW_MAX;
        }
        
        for(int i = rowMin; i < rowMax; ++i){
            for(int j = 0; j < DefaultModel.MAX_SQUARE; ++j){
                if(i == rowMin)
                    inPlayPieces.add(new Pawn(this.color,(this.color + (j+1)),i,j));
                else{
                    String name = this.color + 2*(j+1);
                    if((j == 0) || (j == 7))
                        inPlayPieces.add(new Rook(this.color,name,i,j));
                    else if((j == 1) || (j == 6))
                        inPlayPieces.add(new Bishop(this.color,name,i,j));
                    else if((j == 2) || (j == 5))
                        inPlayPieces.add(new Bishop(this.color,name,i,j));
                    else if(j == 3)
                        inPlayPieces.add(new King(this.color,name,i,j));
                    else if(j == 4)
                        inPlayPieces.add(new Queen(this.color,name,i,j));
                }
            }
        }
    }
    
    public ArrayList<Piece> getInPlayPieces(){
        return this.inPlayPieces;
    }
    public void capturePiece(Piece p){
        inPlayPieces.remove(p);
        capturedPieces.add(p);
    }
}
