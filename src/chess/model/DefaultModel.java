
package chess.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;
public class DefaultModel extends AbstractModel{
    
    public static final int MAX_SQUARE = 8;
    public static final int MIN_SQUARE = 0;
    public static final int INIT_WHITE_ROW_MIN = 6;
    public static final int INIT_WHITE_ROW_MAX = 8;
    public static final int INIT_BLACK_ROW_MIN = 0;
    public static final int INIT_BLACK_ROW_MAX = 2;
    public static final String WHITE = "WHITE";
    public static final String BLACK = "BLACK";
    
    
    private boolean stalemate;
    private boolean whitePlayerWon;
    private boolean blackPlayerWon;
    private boolean whitePlayerTurn;
    private boolean pvp;
    
    private String PVP = "PVP";
    
    
    private BoardSpace[][] board;
    private Player whitePlayer;
    private Player blackPlayer;
    
    private ArrayList<Piece> whitePiecesIn;
    private ArrayList<Piece> whitePiecesOut;
    private HashMap<String,ArrayList<Coordinate>> whitePossibleMoves;
    private ArrayList<Piece> blackPiecesIn;
    private ArrayList<Piece> blackPiecesOut;
    private HashMap<String,ArrayList<Coordinate>> blackPossibleMoves;
    
    public DefaultModel(){
        stalemate = false;
        whitePlayerWon = false;
        blackPlayerWon = false;
        whitePlayerTurn = true;
        pvp = true;
        
        this.whitePlayer = new Player(WHITE);
        this.blackPlayer = new Player(BLACK);
        
        whitePiecesIn =(ArrayList<Piece>) whitePlayer.getInPlayPieces().clone();
        whitePiecesOut = new ArrayList();
        blackPiecesIn = (ArrayList<Piece>) blackPlayer.getInPlayPieces().clone();
        blackPiecesOut = new ArrayList();
        
        this.initBoard();
        this.initStartingPossibleMoves();
        
    }
    
    private void initBoard(){
        board = new BoardSpace[MAX_SQUARE][MAX_SQUARE];
        
        for(int i = 0; i < MAX_SQUARE; ++i){
            for(int j = 0; j < MAX_SQUARE; ++j){
                board[i][j] = new BoardSpace(i,j);
            }
        }
        
        for(Piece p: whitePiecesIn){
            Coordinate pieceCoord = p.getCoordinate();
            
            for(int i = INIT_WHITE_ROW_MIN; i < INIT_WHITE_ROW_MAX; ++i){
                for(int j = 0; j < MAX_SQUARE; ++j){
                    Coordinate boardCoord = board[i][j].getCoordinate();
                    
                    if(boardCoord.equals(pieceCoord))
                        board[i][j].occupy(p);
                }
            }
        }
        for(Piece p : blackPiecesIn){
            Coordinate pieceCoord = p.getCoordinate();
            for(int i = INIT_BLACK_ROW_MIN; i < INIT_BLACK_ROW_MAX; ++i){
                for(int j = 0; j < MAX_SQUARE; ++j){
                    Coordinate boardCoord = board[i][j].getCoordinate();
                    
                    if(boardCoord.equals(pieceCoord))
                        board[i][j].occupy(p);
                }
            }
        }
    }
    private void initStartingPossibleMoves(){
        whitePossibleMoves = new HashMap();
        for(Piece p: whitePiecesIn){
            String name = p.getName();
            ArrayList<Coordinate> moves = Logic.possibleMoves(this.board,p);
            whitePossibleMoves.put(name, moves);
        }
        
        blackPossibleMoves = new HashMap();
        for(Piece p : blackPiecesIn){
            String name = p.getName();
            ArrayList<Coordinate> moves = Logic.possibleMoves(this.board,p);
            blackPossibleMoves.put(name, moves);
        }
    }
    private void updatePossibleMoves(Piece p){
        ArrayList<Coordinate> moves = new ArrayList();
        
        String name = p.getName();
        String color = p.getColor();
        if(color.equals(WHITE)){
            whitePossibleMoves.remove(name);
            moves = Logic.possibleMoves(this.board,p);
            whitePossibleMoves.put(name, moves);
        }
        else{
            blackPossibleMoves.remove(name);
            moves = Logic.possibleMoves(this.board,p);
            blackPossibleMoves.put(name, moves);
        }
        
    }
    public ArrayList<Coordinate> getPossibleMoves(Piece p){
        ArrayList<Coordinate> moves = new ArrayList();
        
        String name = p.getName();
        String color = p.getColor();
        
        if(color.equals(WHITE))
            moves = (ArrayList<Coordinate>)whitePossibleMoves.get(name).clone();
        else
            moves = (ArrayList<Coordinate>)blackPossibleMoves.get(name).clone();
        
        return moves;
    }
    public ArrayList<Coordinate> getPossibleMoves(Coordinate c){
        int x = c.getX();
        int y = c.getY();
        
        return this.getPossibleMoves(board[x][y].getPiece());
    }
    public void setGameType(String gameType){
        if(gameType.equals(pvp))
            pvp = true;
        else
            pvp = false;
    }
    
    public void moveChosen(Coordinate oldSpace, Coordinate newSpace){
        int oldX = oldSpace.getX();
        int oldY = oldSpace.getY();
        
        Piece p = board[oldX][oldY].getPiece();
        this.movePiece(p, newSpace);
    }
    
    public void movePiece(Piece p, Coordinate c){
        int x = c.getX();
        int y = c.getY();
        
        this.movePiece(p, x, y);
    }
    
    public void movePiece(Piece p, int x, int y){
        int prevX = p.getCoordinate().getX();
        int prevY = p.getCoordinate().getY();
        
        board[prevX][prevY].unoccupy();
        if(board[x][y].isOccupied()){
            Piece capturedPiece = board[x][y].getPiece();
            this.capturePiece(capturedPiece);  
        }
        p.setCoordinate(x, y);
        board[x][y].occupy(p);
        this.updatePossibleMoves(p);
    }
    
    public void capturePiece(Piece p){
        String name = p.getName();
        String color = p.getColor();
        p.capture();
        
        if(color.equals(WHITE)){
            whitePiecesIn.remove(p);
            whitePossibleMoves.remove(name);
            whitePiecesOut.add(p);
            whitePlayer.capturePiece(p);
        }
        else{
            blackPiecesIn.remove(p);
            blackPossibleMoves.remove(name);
            blackPiecesOut.add(p);
            blackPlayer.capturePiece(p);
        }
    }
    public static boolean validSpace(int x, int y){
        boolean valid = false;
        
        if((x >= DefaultModel.MIN_SQUARE) && (x < DefaultModel.MAX_SQUARE)){
            if((y >= DefaultModel.MIN_SQUARE) && (y < DefaultModel.MAX_SQUARE))
                valid = true;
        }
        
        return valid;
    }
    public boolean isGameOver(){
        
        this.checkStalemate();
        this.checkPlayersWon();
        
        boolean gameOver = this.stalemate || this.whitePlayerWon || this.blackPlayerWon;
        
        return gameOver;
    }
   
    public boolean checkPlayersWon(){

        for(Piece p : whitePiecesOut){
            if(p instanceof King)
                this.blackPlayerWon = true;
        }
        for(Piece p: blackPiecesOut){
            if(p instanceof King)
                this.whitePlayerWon = true;
        }
        
        return whitePlayerWon || blackPlayerWon;
    }
     public boolean checkStalemate(){
        this.stalemate = !this.movesLeft();
        return this.stalemate;
    }

    public boolean movesLeft(){
        boolean movesLeft = false;
        int whiteCount = 0;
        int blackCount = 0;
        
        int whitePieces = whitePiecesIn.size();
        int blackPieces = blackPiecesIn.size();
        
        while(((whiteCount < whitePieces) && (blackCount < blackPieces)) && !movesLeft){
            Piece blackPiece = blackPiecesIn.get(blackCount);
            
            if(whiteCount < whitePieces){
                Piece p = whitePiecesIn.get(whiteCount);
                ArrayList<Coordinate> possibleMoves = getPossibleMoves(p);
            
                if(possibleMoves.size() > 0)
                    movesLeft = true;
            }
            if(blackCount < blackPieces){
                Piece p = blackPiecesIn.get(whiteCount);
                ArrayList<Coordinate> possibleMoves = getPossibleMoves(p);
            
                if(possibleMoves.size() > 0)
                    movesLeft = true;
            }
            
            ++whiteCount;
            ++blackCount;
        }
        
        return movesLeft;
    }
    
    
    
}
