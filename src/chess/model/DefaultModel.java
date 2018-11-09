
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
    private boolean spaceSelected;
    
    public static final String PVP = "PVP";
    
    
    private BoardSpace[][] board;
    private Player whitePlayer;
    private Player blackPlayer;
    
    private HashMap<String,Piece> whitePieces;
    private HashMap<String,ArrayList<Coordinate>> whitePossibleMoves;
    private HashMap<String,Piece> blackPieces;
    private HashMap<String,ArrayList<Coordinate>> blackPossibleMoves;
    
    private Piece selectedPiece;
    
    public DefaultModel(){
        gameOver = false;
        stalemate = false;
        whitePlayerWon = false;
        blackPlayerWon = false;
        whitePlayerTurn = true;
        pvp = false;
        spaceSelected = false;
        
        this.whitePlayer = new Player(WHITE);
        this.blackPlayer = new Player(BLACK);
        
        whitePieces = whitePlayer.getPieces();
        blackPieces = blackPlayer.getPieces();
        
        selectedPiece = null;

    }
    
    public void setGameStart(String gameType){
        
        
        this.initDefaults();
        this.setGameType(gameType);
        firePropertyChange(DefaultController.GAME_START,null,null);
    }
    
    private void initDefaults(){
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
        
        for(HashMap.Entry<String,Piece> e: whitePieces.entrySet()){
            Piece p = e.getValue();
            int x = p.getCoordinate().getX();
            int y = p.getCoordinate().getY();
            board[y][x].occupy(p);
            firePropertyChange(DefaultController.OCCUPY_SPACE,null,board[y][x]);
        }
        for(HashMap.Entry<String,Piece> e: blackPieces.entrySet()){
            Piece p = e.getValue();
            int x = p.getCoordinate().getX();
            int y = p.getCoordinate().getY();
            board[y][x].occupy(p);
            firePropertyChange(DefaultController.OCCUPY_SPACE,null,board[y][x]);
        }
    }
    private void initStartingPossibleMoves(){
        
        whitePossibleMoves = new HashMap();
        for(HashMap.Entry<String,Piece> e: whitePieces.entrySet()){
            Piece p = e.getValue();
            String name = p.getName();
            ArrayList<Coordinate> moves = Logic.possibleMoves(this.board,p);
            whitePossibleMoves.put(name, moves);
        }
        
        blackPossibleMoves = new HashMap();
        for(HashMap.Entry<String,Piece> e: blackPieces.entrySet()){
            Piece p = e.getValue();
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
    public void squareSelected(Coordinate c){
        int x = c.getX();
        int y = c.getY();
        
        if(spaceSelected){
            if(this.possibleMoveOfSelectedPiece(c)){
                
                setMoveChosen(c);
                spaceSelected = false;
                selectedPiece = null;
                firePropertyChange(DefaultController.DESELECT_PIECE,null,null);
            }
            else if(board[y][x].isOccupied()){
                
                selectedPiece = null;
                spaceSelected = false;
                firePropertyChange(DefaultController.DESELECT_PIECE,null,null);
                
                Piece p = board[y][x].getPiece();
                String name = p.getName();
                String pieceColor = p.getColor();
                String playerColor = "";
                
                if(whitePlayerTurn)
                    playerColor = WHITE;
                else
                    playerColor = BLACK;
                
                if(pieceColor.equals(playerColor)){
                    selectedPiece = p;
                    spaceSelected = true;
                    firePropertyChange(DefaultController.SELECT_PIECE,null,c);
                    
                    if(whitePlayerTurn){
                        ArrayList<Coordinate> moves = whitePossibleMoves.get(name);
                        firePropertyChange(DefaultController.SHOW_POSSIBLE_MOVES,null, moves);
                    }
                    else{
                        ArrayList<Coordinate> moves = blackPossibleMoves.get(name);
                        firePropertyChange(DefaultController.SHOW_POSSIBLE_MOVES,null, moves);
                    }
                }
                
            }
            else{
                selectedPiece = null;
                spaceSelected = false;
                firePropertyChange(DefaultController.DESELECT_PIECE,null,null);
            }
        }
        else{
            if(board[y][x].isOccupied()){
                Piece p = board[y][x].getPiece();
                String name = p.getName();
                String pieceColor = p.getColor();
                String playerColor = "";
                
                if(whitePlayerTurn)
                    playerColor = WHITE;
                else
                    playerColor = BLACK;
                
                if(pieceColor.equals(playerColor)){
                    selectedPiece = p;
                    spaceSelected = true;
                    firePropertyChange(DefaultController.SELECT_PIECE,null,c);
                    
                    if(whitePlayerTurn){
                        ArrayList<Coordinate> moves = whitePossibleMoves.get(name);
                        firePropertyChange(DefaultController.SHOW_POSSIBLE_MOVES,null, moves);
                    }
                    else{
                        ArrayList<Coordinate> moves = blackPossibleMoves.get(name);
                        firePropertyChange(DefaultController.SHOW_POSSIBLE_MOVES,null, moves);
                    }
                }
            }
        }
    }
    private boolean possibleMoveOfSelectedPiece(Coordinate c){
        
        boolean possibleMove = false;
        
        String name = selectedPiece.getName();
        String color = selectedPiece.getColor();
        ArrayList<Coordinate> moves = new ArrayList();
        
        if(color.equals(WHITE))
            moves = whitePossibleMoves.get(name);
        else
            moves = blackPossibleMoves.get(name);
        
        for(Coordinate move : moves){
            
            if(move.equals(c)){
                possibleMove = true;
            }
        }
        
        return possibleMove;
    }
    
    public void setMoveChosen( Coordinate newSpace){
        
        Coordinate oldSpace = selectedPiece.getCoordinate();
        
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
            
            this.computerTurn();
        }
    }
    
    private void computerTurn(){
        HashMap<String,Object> decision = ArtificialIntelligence.getDecision(board, whitePlayer, blackPossibleMoves, whitePossibleMoves);
        
        String name = (String)decision.get(ArtificialIntelligence.NAME);
        Coordinate move = (Coordinate)decision.get(name);
        
        System.out.println(name);
        
        Piece p = blackPieces.get(name);
        
        selectedPiece = p;
        spaceSelected = true;
        firePropertyChange(DefaultController.SELECT_PIECE,null,p.getCoordinate());
        
        System.out.println("here");
        
        setMoveChosen(move);
        
        selectedPiece = null;
        spaceSelected = false;
        firePropertyChange(DefaultController.DESELECT_PIECE,null,null);
    }
    
    public void movePiece(Piece p, Coordinate c){
        int x = c.getX();
        int y = c.getY();
        
        this.movePiece(p, x, y);
    }
    
    public void movePiece(Piece p, int x, int y){
        
        
        
        Coordinate oldCoord = p.getCoordinate();
        
        boolean castling = false;
        Coordinate previousSpace = p.getCoordinate();
        Coordinate target = new Coordinate(x,y);
        
        int prevX = p.getCoordinate().getX();
        int prevY = p.getCoordinate().getY();
        
        board[prevY][prevX].unoccupy();
        
        if(board[y][x].isOccupied()){
            Piece capturedPiece = board[y][x].getPiece();
            this.capturePiece(capturedPiece);  
        }
        p.setCoordinate(x, y);
        board[y][x].occupy(p);
        
        for(HashMap.Entry<String,Piece> e: whitePieces.entrySet())
            this.updatePossibleMoves(e.getValue());
        for(HashMap.Entry<String,Piece> e: blackPieces.entrySet())
            this.updatePossibleMoves(e.getValue());
        
        
        
        firePropertyChange(DefaultController.MOVE_CHOSEN,oldCoord, board[y][x]);
        
        
        /*if(p.getType().equals(Piece.KING)){
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
        }*/
        
        if(p.isUnmoved()){
            p.move();
        }
    }
    
    public void capturePiece(Piece p){
        String name = p.getName();
        String color = p.getColor();
        
        if(color.equals(WHITE)){
            whitePieces.remove(name);
            whitePossibleMoves.remove(name);
            firePropertyChange(DefaultController.CAPTURE_WHITE_PIECE,null,p);
        }
        else{
            blackPieces.remove(name);
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

        for(HashMap.Entry<String,Piece> e: whitePieces.entrySet()){
            if(e.getValue() instanceof King)
                whiteKing = true;
        }
        for(HashMap.Entry<String,Piece> e: blackPieces.entrySet()){
            if(e.getValue() instanceof King)
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
        
        for(HashMap.Entry<String,Piece> e: whitePieces.entrySet()){
            Piece p = e.getValue();
            ArrayList<Coordinate> possibleMoves = getPossibleMoves(p);
            
            if(possibleMoves.size() > 0)
                movesLeft = true;
            if(movesLeft)
                break;
        }
        for(HashMap.Entry<String,Piece> e: blackPieces.entrySet()){
            Piece p = e.getValue();
            ArrayList<Coordinate> possibleMoves = getPossibleMoves(p);
            
            if(possibleMoves.size() > 0)
                movesLeft = true;
            if(movesLeft)
                break;
        }
        
        
        return movesLeft;
    }
    
    public static boolean isCheck(Coordinate coord, HashMap<String,ArrayList<Coordinate>> opponentMoves){
        boolean check = false;
        
        /*for(HashMap.Entry<String, ArrayList<Coordinate>> e: opponentMoves.entrySet()){
            ArrayList<Coordinate> moves = e.getValue();
            
            for(Coordinate c : moves){
                if(c.equals(coord))
                    check = true;
            }
        }*/
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
