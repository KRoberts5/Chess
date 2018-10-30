
package chess.model;



import java.util.ArrayList;

public class Player {
    
    private String color;
    private ArrayList<Piece> pieces;
    
    public Player(String color){
        this.color = color;
        pieces = new ArrayList();
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
                    pieces.add(new Pawn(this.color,(this.color + (j+1)),i,j));
                else{
                    String name = this.color + (j+1) + DefaultModel.MAX_SQUARE;
                    if((j == 0) || (j == 7))
                        pieces.add(new Rook(this.color,name,i,j));
                    else if((j == 1) || (j == 6))
                        pieces.add(new Bishop(this.color,name,i,j));
                    else if((j == 2) || (j == 5))
                        pieces.add(new Bishop(this.color,name,i,j));
                    else if(j == 3)
                        pieces.add(new King(this.color,name,i,j));
                    else if(j == 4)
                        pieces.add(new Queen(this.color,name,i,j));
                }
            }
        }
    }
    
    public ArrayList<Piece> getPieces(){
        return this.pieces;
    }
}
