
package chess;



import java.util.ArrayList;

public class Player {
    
    private String color;
    private ArrayList<Piece> inPlayPieces;
    private ArrayList<Piece> capturedPieces;
    
    public Player(String color){
        this.color = color;
        if(color.equals(DefaultModel.WHITE))
            this.initializeWhitePieces();
        else
            this.initializeBlackPieces();
    }
    private void initializeWhitePieces(){
        for(int i = DefaultModel.INIT_WHITE_ROW_MIN; i < DefaultModel.INIT_WHITE_ROW_MAX; ++i){
            for(int j = 0; j < DefaultModel.MAX_SQUARE; ++j){
                if(i == DefaultModel.INIT_WHITE_ROW_MIN)
                    inPlayPieces.add(new Pawn(this.color,i,j));
                else{
                    if((j == 0) || (j == 7))
                        inPlayPieces.add(new Rook(this.color,i,j));
                    else if((j == 1) || (j == 6))
                        inPlayPieces.add(new Bishop(this.color,i,j));
                    else if((j == 2) || (j == 5))
                        inPlayPieces.add(new Bishop(this.color,i,j));
                    else if(j == 3)
                        inPlayPieces.add(new King(this.color,i,j));
                    else if(j == 4)
                        inPlayPieces.add(new Queen(this.color,i,j));
                    
                }
            }
        }
    }
    private void initializeBlackPieces(){
        for(int i = DefaultModel.INIT_BLACK_ROW_MIN; i < DefaultModel.INIT_BLACK_ROW_MAX; ++i){
            for(int j = 0; j < DefaultModel.MAX_SQUARE; ++j){
                if(i == DefaultModel.INIT_BLACK_ROW_MIN)
                    inPlayPieces.add(new Pawn(this.color,i,j));
                else{
                    if((j == 0) || (j == 7))
                        inPlayPieces.add(new Rook(this.color,i,j));
                    else if((j == 1) || (j == 6))
                        inPlayPieces.add(new Bishop(this.color,i,j));
                    else if((j == 2) || (j == 5))
                        inPlayPieces.add(new Bishop(this.color,i,j));
                    else if(j == 3)
                        inPlayPieces.add(new King(this.color,i,j));
                    else if(j == 4)
                        inPlayPieces.add(new Queen(this.color,i,j));
                    
                }
            }
        }
    }
    
    public ArrayList<Piece> getInPlayPieces(){
        return this.inPlayPieces;
    }
}
