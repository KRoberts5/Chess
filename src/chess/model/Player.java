
package chess.model;



import java.util.HashMap;

public class Player {
    
    private String color;
    private HashMap<String,Piece> pieces;
    private King king;
    
    public Player(String color){
        this.color = color;
        pieces = new HashMap();
        this.initializePieces();
    }

    private void initializePieces(){

        int rowMin = 0;
        int rowMax = 0;
        int pawnRow = 0;
        if(this.color.equals(DefaultModel.WHITE)){
            rowMin = DefaultModel.INIT_WHITE_ROW_MIN;
            rowMax = DefaultModel.INIT_WHITE_ROW_MAX;
            pawnRow = rowMin;
        }
        else{
            rowMin = DefaultModel.INIT_BLACK_ROW_MIN;
            rowMax = DefaultModel.INIT_BLACK_ROW_MAX;
            pawnRow = rowMax - 1;
        }
        
        for(int i = rowMin; i < rowMax; ++i){
            for(int j = 0; j < DefaultModel.MAX_SQUARE; ++j){
                
                    if(i == pawnRow){
                        String name = (this.color + (j+1));
                        pieces.put(name,new Pawn(this.color,name,j,i));
                    }
                    else{
                        String name = this.color + (j + 1 + DefaultModel.MAX_SQUARE);


                        if((j == 0) || (j == 7))
                            pieces.put(name,new Rook(this.color,name,j,i));
                        else if((j == 1) || (j == 6))
                            pieces.put(name,new Knight(this.color,name,j,i));
                        else if((j == 2) || (j == 5))
                            pieces.put(name,new Bishop(this.color,name,j,i));
                        else if(j == 3)
                            pieces.put(name,new Queen(this.color,name,j,i));
                        else if(j == 4){
                            king = new King(this.color,name,j,i);
                            pieces.put(name,king);
                        }
                            
                    }
                    
                }
                
        }
    }
    
    public HashMap<String,Piece> getPieces(){
        return this.pieces;
    }
    public King getKing(){
        return king;
    }
}
