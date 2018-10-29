
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
            ArrayList<Coordinate> moves = this.getPossibleMoves(p);
            whitePossibleMoves.put(name, moves);
        }
        
        blackPossibleMoves = new HashMap();
        for(Piece p : blackPiecesIn){
            String name = p.getName();
            ArrayList<Coordinate> moves = this.getPossibleMoves(p);
            blackPossibleMoves.put(name, moves);
        }
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
            capturedPiece.capture();
            if(capturedPiece.getColor().equals(WHITE)){
                whitePiecesIn.remove(capturedPiece);
                whitePiecesOut.add(capturedPiece);            
            }
                
            else{
                blackPiecesIn.remove(capturedPiece);
                blackPiecesOut.add(capturedPiece);
            }
            p.setCoordinate(x, y);
            board[x][y].occupy(p);
        }
    }
    
    public ArrayList<Coordinate> getPossibleMoves(Piece p){
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();
        
        if(p instanceof Pawn)
            possibleMoves = this.getPossibleMoves((Pawn) p);
        if(p instanceof Rook)
            possibleMoves = this.getPossibleMoves((Rook) p);
        if(p instanceof Bishop)
            possibleMoves = this.getPossibleMoves((Bishop) p);
        if(p instanceof Knight)
            possibleMoves = this.getPossibleMoves((Knight) p);
        if(p instanceof King)
            possibleMoves = this.getPossibleMoves((King) p);
        if(p instanceof Queen)
            possibleMoves = this.getPossibleMoves((Queen) p);
        
        return possibleMoves;
    }
    
    public ArrayList<Coordinate> getPossibleMoves(Pawn p){
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();
        
        String color = p.getColor();
        int currentX = p.getCoordinate().getX();
        int currentY = p.getCoordinate().getY();

        if(color.equals(DefaultModel.WHITE)){
            
            if(this.validSpace(currentX, currentY - 1)){
                if(board[currentX][currentY - 1].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY - 1);
                    possibleMoves.add(c);
                }
            }
            if(this.validSpace(currentX - 1, currentY - 1)){
                if(board[currentX - 1][currentY - 1].isOccupied()){
                    if(board[currentX - 1][currentY - 1].getPiece().getColor().equals(BLACK)){
                        Coordinate c = new Coordinate(currentX - 1, currentY - 1);
                        possibleMoves.add(c);
                    }
                }
            }
            if(this.validSpace(currentX + 1, currentY - 1)){
                if(board[currentX + 1][currentY - 1].isOccupied()){
                    if(board[currentX + 1][currentY - 1].getPiece().getColor().equals(BLACK)){
                        Coordinate c = new Coordinate(currentX + 1, currentY - 1);
                        possibleMoves.add(c);
                    }
                }
            }
            
            if(p.unmoved()){
                if((this.validSpace(currentX, currentY - 1)) && (this.validSpace(currentX, currentY - 2))){
                    if((board[currentX][currentY - 1].isNotOccupied()) && (board[currentX][currentY - 2].isNotOccupied())){
                        Coordinate c = new Coordinate(currentX, currentY - 2);
                        possibleMoves.add(c);
                    }
                }
            }
            
        }
        else{
            if(this.validSpace(currentX, currentY + 1)){
                if(board[currentX][currentY + 1].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY + 1);
                    possibleMoves.add(c);
                }
            }
            if(this.validSpace(currentX - 1, currentY + 1)){
                if(board[currentX - 1][currentY + 1].isOccupied()){
                    if(board[currentX - 1][currentY + 1].getPiece().getColor().equals(WHITE)){
                        Coordinate c = new Coordinate(currentX - 1, currentY + 1);
                        possibleMoves.add(c);
                    }
                }
            }
            if(this.validSpace(currentX + 1, currentY + 1)){
                if(board[currentX +1][currentY + 1].isOccupied()){
                    if(board[currentX +1][currentY + 1].getPiece().getColor().equals(WHITE)){
                        Coordinate c = new Coordinate(currentX + 1, currentY + 1);
                        possibleMoves.add(c);
                    }
                }
            }
            
            if(p.unmoved()){
                if((this.validSpace(currentX, currentY + 1)) && (this.validSpace(currentX, currentY + 2))){
                    if((board[currentX][currentY + 1].isNotOccupied()) && (board[currentX][currentY + 2].isNotOccupied())){
                        Coordinate c = new Coordinate(currentX, currentY + 2);
                        possibleMoves.add(c);
                    }
                }
            }
        }
        
        return possibleMoves;
    }
    
    public ArrayList<Coordinate> getPossibleMoves(Rook r){
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();
        
        String color = r.getColor();
        int currentX = r.getCoordinate().getX();
        int currentY = r.getCoordinate().getY();
        int xMod = 0;
        int yMod = 0;
        
        boolean northSearch = true;
        boolean southSearch = true;
        boolean westSearch = true;
        boolean eastSearch = true;
        
        int northCount = 1;
        int southCount = 1;
        int westCount = 1;
        int eastCount = 1;
        
           
        while(northSearch){
            yMod = -1*northCount;

            if(this.validSpace(currentX, currentY + yMod)){
                if(board[currentX][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    northSearch = false;
                }
            }
            else
                northSearch = false;

             ++northCount;
        }
        while(southSearch){
            yMod = 1*southCount;

            if(this.validSpace(currentX, currentY + yMod)){
                if(board[currentX][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    southSearch = false;
                }
            }
            else
                southSearch = false;

            ++southCount;
        }
        while(westSearch){
            xMod = -1*westCount;

            if(this.validSpace(currentX + xMod, currentY)){
                if(board[currentX + xMod][currentY].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX + xMod, currentY);
                        possibleMoves.add(c);
                    }
                    westSearch = false;
                }
            }
            else
                westSearch = false;

            ++westCount;
        }
        while(eastSearch){
            xMod = 1*eastCount;

            if(this.validSpace(currentX + xMod, currentY)){
                if(board[currentX + xMod][currentY].isNotOccupied()){
                     Coordinate c = new Coordinate(currentX + xMod, currentY);
                     possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX + xMod, currentY);
                        possibleMoves.add(c);
                    }
                    eastSearch = false;
                }
            }
            else
                eastSearch = false;

            ++eastCount;
        }
        
        return possibleMoves;
    }
    
    public ArrayList<Coordinate> getPossibleMoves(Bishop b){
        ArrayList<Coordinate> possibleMoves = new ArrayList();
        
        String color = b.getColor();
        int currentX = b.getCoordinate().getX();
        int currentY = b.getCoordinate().getY();
        int xMod = 1;
        int yMod = 1;
        
        boolean northWestSearch = true;
        boolean northEastSearch = true;
        boolean southWestSearch = true;
        boolean southEastSearch = true;
        
        int northWestCount = 1;
        int northEastCount = 1;
        int southWestCount = 1;
        int southEastCount = 1;
        
        while(northWestSearch){
            xMod = -1*northWestCount;
            yMod = -1*northWestCount;
            
            if(this.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentX + xMod][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    northWestSearch = false;
                }
            }
            else
                northWestSearch = false;
            
            ++northWestCount;
        }
        
        while(northEastSearch){
            xMod = northEastCount;
            yMod = -1*northEastCount;
            
            if(this.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentX + xMod][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    northEastSearch = false;
                }
            }
            else
                northEastSearch = false;
            ++northEastCount;
        }
        
        while(southWestSearch){
            xMod = -1*southWestCount;
            yMod = southWestCount;
            
            if(this.validSpace(currentX +xMod, currentY + yMod)){
                if(board[currentX + xMod][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX +xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX +xMod, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    southWestSearch = false;
                }
            }
            else
                southWestSearch = false;
            
            ++southWestCount;
        }
        while(southEastSearch){
            xMod = southEastCount;
            yMod = southEastCount;
            
            if(this.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentX + xMod][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    southEastSearch = false;
                }
            }
            else
                southEastSearch = false;
            
            ++southEastCount;
        }
        
        return possibleMoves;
    } 
    public ArrayList<Coordinate> getPossibleMoves(Knight k){
        ArrayList<Coordinate> possibleMoves = new ArrayList();
        
        return possibleMoves;
    }
    public ArrayList<Coordinate> getPossibleMoves(King k){
        ArrayList<Coordinate> possibleMoves = new ArrayList();
        
        return possibleMoves;
    }
    public ArrayList<Coordinate> getPossibleMoves(Queen q){
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();
        
        String color = q.getColor();
        int currentX = q.getCoordinate().getX();
        int currentY = q.getCoordinate().getY();
        int xMod = 0;
        int yMod = 0;
        
        boolean northSearch = true;
        boolean southSearch = true;
        boolean westSearch = true;
        boolean eastSearch = true;
        
        boolean northWestSearch = true;
        boolean northEastSearch = true;
        boolean southWestSearch = true;
        boolean southEastSearch = true;
        
        int northCount = 1;
        int southCount = 1;
        int westCount = 1;
        int eastCount = 1;
        
        int northWestCount = 1;
        int northEastCount = 1;
        int southWestCount = 1;
        int southEastCount = 1;
        
        while(northSearch){
            yMod = -1*northCount;

            if(this.validSpace(currentX, currentY + yMod)){
                if(board[currentX][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    northSearch = false;
                }
            }
            else
                northSearch = false;

             ++northCount;
        }
        while(southSearch){
            yMod = 1*southCount;

            if(this.validSpace(currentX, currentY + yMod)){
                if(board[currentX][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    southSearch = false;
                }
            }
            else
                southSearch = false;

            ++southCount;
        }
        while(westSearch){
            xMod = -1*westCount;

            if(this.validSpace(currentX + xMod, currentY)){
                if(board[currentX + xMod][currentY].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX + xMod, currentY);
                        possibleMoves.add(c);
                    }
                    westSearch = false;
                }
            }
            else
                westSearch = false;

            ++westCount;
        }
        while(eastSearch){
            xMod = 1*eastCount;

            if(this.validSpace(currentX + xMod, currentY)){
                if(board[currentX + xMod][currentY].isNotOccupied()){
                     Coordinate c = new Coordinate(currentX + xMod, currentY);
                     possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX + xMod, currentY);
                        possibleMoves.add(c);
                    }
                    eastSearch = false;
                }
            }
            else
                eastSearch = false;

            ++eastCount;
        }
        
        while(northWestSearch){
            xMod = -1*northWestCount;
            yMod = -1*northWestCount;
            
            if(this.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentX + xMod][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    northWestSearch = false;
                }
            }
            else
                northWestSearch = false;
            
            ++northWestCount;
        }
        
        while(northEastSearch){
            xMod = northEastCount;
            yMod = -1*northEastCount;
            
            if(this.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentX + xMod][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    northEastSearch = false;
                }
            }
            else
                northEastSearch = false;
            ++northEastCount;
        }
        
        while(southWestSearch){
            xMod = -1*southWestCount;
            yMod = southWestCount;
            
            if(this.validSpace(currentX +xMod, currentY + yMod)){
                if(board[currentX + xMod][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX +xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX +xMod, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    southWestSearch = false;
                }
            }
            else
                southWestSearch = false;
            
            ++southWestCount;
        }
        while(southEastSearch){
            xMod = southEastCount;
            yMod = southEastCount;
            
            if(this.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentX + xMod][currentY + yMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentX + xMod][currentY + yMod].getPiece().getColor().equals(color)){
                        Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                        possibleMoves.add(c);
                    }
                    southEastSearch = false;
                }
            }
            else
                southEastSearch = false;
            
            ++southEastCount;
        }
        
        return possibleMoves;
    }
    
    public boolean validSpace(int x, int y){
        boolean valid = false;
        
        if((x >= DefaultModel.MIN_SQUARE) && (x < DefaultModel.MAX_SQUARE)){
            if((y >= DefaultModel.MIN_SQUARE) && (y < DefaultModel.MAX_SQUARE))
                valid = true;
        }
        
        return valid;
    }
    
    public boolean checkStalemate(){
        this.stalemate = !this.movesLeft();
        return this.stalemate;
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
    
    public boolean isGameOver(){
        
        this.checkStalemate();
        this.checkPlayersWon();
        
        boolean gameOver = this.stalemate || this.whitePlayerWon || this.blackPlayerWon;
        
        return gameOver;
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
    
    public static double getDistance(Coordinate p1, Coordinate p2){
        double distance = 0;
        int squared = 2;
        double squareRoot = .5;
        
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();
        
        double xDiff = Math.pow(x1 - x2, squared);
        double yDiff = Math.pow(y1 - y2, squared);
        
        distance = Math.pow(xDiff + yDiff, squareRoot); 
        
        return distance;
    }
    
    
}
