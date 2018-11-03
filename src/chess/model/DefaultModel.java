
package chess.model;


import chess.controller.DefaultController;
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
    
    private boolean gameOver;
    private boolean stalemate;
    private boolean whitePlayerWon;
    private boolean blackPlayerWon;
    private boolean whitePlayerTurn;
    private boolean pvp;
    
    private String PVP = "PVP";
    
    
    private BoardSpace[][] board;
    private Player whitePlayer;
    private Player blackPlayer;
    
    private ArrayList<Piece> whitePieces;
    private HashMap<String,ArrayList<Coordinate>> whitePossibleMoves;
    private ArrayList<Piece> blackPieces;
    private HashMap<String,ArrayList<Coordinate>> blackPossibleMoves;
    
    public DefaultModel(){
        gameOver = false;
        stalemate = false;
        whitePlayerWon = false;
        blackPlayerWon = false;
        whitePlayerTurn = true;
        pvp = true;
        
        this.whitePlayer = new Player(WHITE);
        this.blackPlayer = new Player(BLACK);
        
        whitePieces = whitePlayer.getPieces();
        blackPieces = blackPlayer.getPieces();

    }
    
    public void initDefautls(){
        this.initBoard();
        this.initStartingPossibleMoves();
    }
    
    private void initBoard(){
        board = new BoardSpace[MAX_SQUARE][MAX_SQUARE];
        
        for(int i = 0; i < MAX_SQUARE; ++i){
            for(int j = 0; j < MAX_SQUARE; ++j){
                board[i][j] = new BoardSpace(j,i);
                
            }
        }
        
        for(Piece p: whitePieces){
            int x = p.getCoordinate().getX();
            int y = p.getCoordinate().getY();
            board[y][x].occupy(p);
            firePropertyChange(DefaultController.OCCUPY_SPACE,null,board[y][x]);
        }
        for(Piece p : blackPieces){
            int x = p.getCoordinate().getX();
            int y = p.getCoordinate().getY();
            board[y][x].occupy(p);
            firePropertyChange(DefaultController.OCCUPY_SPACE,null,board[y][x]);
        }
    }
    private void initStartingPossibleMoves(){
        whitePossibleMoves = new HashMap();
        for(Piece p: whitePieces){
            String name = p.getName();
            ArrayList<Coordinate> moves = Logic.possibleMoves(this.board,p);
            whitePossibleMoves.put(name, moves);
        }
        
        blackPossibleMoves = new HashMap();
        for(Piece p : blackPieces){
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
            HashMap<String,ArrayList<Coordinate>> blackMoves =  blackPossibleMoves;
            moves = Logic.possibleMoves(this.board,p,blackMoves);
            whitePossibleMoves.put(name, moves);
        }
        else{
            HashMap<String,ArrayList<Coordinate>> whiteMoves =  whitePossibleMoves;
            moves = Logic.possibleMoves(this.board,p,whiteMoves);
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
        
        return this.getPossibleMoves(board[y][x].getPiece());
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
        
        Piece p = board[oldY][oldX].getPiece();
        this.movePiece(p, newSpace);
        
        this.gameOver = this.isGameOver();
        
        if(gameOver){
            if(this.whitePlayerWon)
                firePropertyChange(DefaultController.GAME_OVER_WHITE_WON,null,null);
            else if(this.blackPlayerWon)
                firePropertyChange(DefaultController.GAME_OVER_BLACK_WON,null,null);
            else if(this.stalemate)
                firePropertyChange(DefaultController.GAME_OVER_STALEMATE,null,null);
        }
        this.whitePlayerTurn = !whitePlayerTurn;
        
        if(!pvp && !whitePlayerTurn){
            //Get AI decision
        }
    }
    
    public void movePiece(Piece p, Coordinate c){
        int x = c.getX();
        int y = c.getY();
        
        this.movePiece(p, x, y);
    }
    
    public void movePiece(Piece p, int x, int y){
        
        boolean castling = false;
        Coordinate previousSpace = p.getCoordinate();
        Coordinate target = new Coordinate(x,y);
        
        int prevX = p.getCoordinate().getX();
        int prevY = p.getCoordinate().getY();
        
        board[prevY][prevX].unoccupy();
        
        firePropertyChange(DefaultController.UNOCCUPY_SPACE,null,new Coordinate(prevX,prevY));
        
        if(board[y][x].isOccupied()){
            Piece capturedPiece = board[y][x].getPiece();
            this.capturePiece(capturedPiece);  
        }
        p.setCoordinate(x, y);
        board[y][x].occupy(p);
        
        for(Piece whitePiece : whitePieces)
            this.updatePossibleMoves(whitePiece);
        for(Piece blackPiece : blackPieces)
            this.updatePossibleMoves(blackPiece);
        
        firePropertyChange(DefaultController.OCCUPY_SPACE,null,board[y][x]);
        
        if(p.getType().equals(Piece.KING)){
            if(Logic.wasWestCastle(previousSpace,target)){
                if(board[7][0].isOccupied()){
                    Piece castlePiece = board[7][0].getPiece();
                    if(castlePiece.getType().equals(Piece.ROOK))
                        this.movePiece(castlePiece, 3,7);
                }
            }
            else if(Logic.wasEastCastle(previousSpace, target)){
                //finish code
            }
        }
        
        if(p.isUnmoved()){
            p.move();
        }
    }
    
    public void capturePiece(Piece p){
        String name = p.getName();
        String color = p.getColor();
        
        if(color.equals(WHITE)){
            whitePieces.remove(p);
            whitePossibleMoves.remove(name);
            firePropertyChange(DefaultController.CAPTURE_WHITE_PIECE,null,p);
        }
        else{
            blackPieces.remove(p);
            blackPossibleMoves.remove(name);
            firePropertyChange(DefaultController.CAPTURE_BLACK_PIECE,null,p);
        }
        
        if(p.getType().equals(Piece.KING)){
            if(p.getColor().equals(WHITE))
                blackPlayerWon = true;
            else
                whitePlayerWon = true;
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
        
        if(!whitePlayerWon && !blackPlayerWon)
            this.checkStalemate();
        //this.checkPlayersWon();
        
        boolean gameOver = this.stalemate || this.whitePlayerWon || this.blackPlayerWon;
        
        return gameOver;
    }
    public boolean checkPlayersWon(){
        
        boolean whiteKing = false;
        boolean blackKing = false;

        for(Piece p : whitePieces){
            if(p instanceof King)
                whiteKing = true;
        }
        for(Piece p: blackPieces){
            if(p instanceof King)
                blackKing = true;
        }
        
        if(!whiteKing)
            this.blackPlayerWon = true;
        if(!blackKing)
            this.whitePlayerWon = true;
        
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
        
        int whitePieceCount = whitePieces.size();
        int blackPieceCount = blackPieces.size();
        
        while(((whiteCount < whitePieceCount) && (blackCount < blackPieceCount)) && !movesLeft){
            
            if(whiteCount < whitePieceCount){
                Piece p = whitePieces.get(whiteCount);
                ArrayList<Coordinate> possibleMoves = getPossibleMoves(p);
            
                if(possibleMoves.size() > 0)
                    movesLeft = true;
            }
            if(blackCount < blackPieceCount){
                Piece p = blackPieces.get(whiteCount);
                ArrayList<Coordinate> possibleMoves = getPossibleMoves(p);
            
                if(possibleMoves.size() > 0)
                    movesLeft = true;
            }
            
            ++whiteCount;
            ++blackCount;
        }
        
        return movesLeft;
    }
    
    public static boolean isCheck(Coordinate coord, HashMap<String,ArrayList<Coordinate>> opponentMoves){
        boolean check = false;
        
        for(HashMap.Entry<String, ArrayList<Coordinate>> e: opponentMoves.entrySet()){
            ArrayList<Coordinate> moves = e.getValue();
            
            for(Coordinate c : moves){
                if(c.equals(coord))
                    check = true;
            }
        }
        return check;
    }
    
    public String toString(){
        String output = "";
        
        for(int i = 0; i < MAX_SQUARE; ++i){
            for(int j = 0; j < MAX_SQUARE; ++j){
                if(board[i][j].isOccupied()){
                    Piece p = board[i][j].getPiece();
                    String type = p.getType().toUpperCase();
                    
                    output += " " + type.charAt(0) + " ";
                }
                else
                    output += " - ";
                
                    
            }
            output+= "\n";
        }
        return output;
    }
    
}
