/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.model;

import static chess.model.DefaultModel.BLACK;
import static chess.model.DefaultModel.WHITE;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Brendan
 */
public class Logic {
    
    public static final int CASTLE_DISTANCE = 2;
    
    public static ArrayList<Coordinate> possibleMoves(BoardSpace[][] b,Piece p){
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();
        
        if(p.getType().equals(Piece.PAWN))
            possibleMoves = possibleMoves(b,(Pawn) p);
        if(p.getType().equals(Piece.ROOK))
            possibleMoves = possibleMoves(b,(Rook) p);
        if(p.getType().equals(Piece.BISHOP))
            possibleMoves = possibleMoves(b,(Bishop) p);
        if(p.getType().equals(Piece.KNIGHT))
            possibleMoves = possibleMoves(b,(Knight) p);
        if(p.getType().equals(Piece.KING))
            possibleMoves = possibleMoves(b,(King) p);
        if(p.getType().equals(Piece.QUEEN))
            possibleMoves = possibleMoves(b,(Queen) p);
        
        return possibleMoves;
    }
    public static ArrayList<Coordinate> possibleMoves(BoardSpace[][] b, Piece p, HashMap<String,ArrayList<Coordinate>> opponentMoves){
        ArrayList<Coordinate> possibleMoves = new ArrayList();
        
        if(p.getType().equals(Piece.PAWN))
            possibleMoves = possibleMoves(b,(Pawn) p);
        if(p.getType().equals(Piece.ROOK))
            possibleMoves = possibleMoves(b,(Rook) p);
        if(p.getType().equals(Piece.BISHOP))
            possibleMoves = possibleMoves(b,(Bishop) p);
        if(p.getType().equals(Piece.KNIGHT))
            possibleMoves = possibleMoves(b,(Knight) p);
        if(p.getType().equals(Piece.KING))
            possibleMoves = possibleMoves(b,(King) p, opponentMoves);
        if(p.getType().equals(Piece.QUEEN))
            possibleMoves = possibleMoves(b,(Queen) p);
        
        return possibleMoves;
    }
    public static ArrayList<Coordinate> possibleMoves(BoardSpace[][] board,Pawn p){
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();
        
        String color = p.getColor();
        int currentX = p.getCoordinate().getX();
        int currentY = p.getCoordinate().getY();

        if(color.equals(DefaultModel.WHITE)){
            
            if(DefaultModel.validSpace(currentX, currentY - 1)){
                if(board[currentY - 1][currentX].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY - 1);
                    possibleMoves.add(c);
                }
            }
            if(DefaultModel.validSpace(currentX - 1, currentY - 1)){
                if(board[currentY - 1][currentX - 1].isOccupied()){
                    if(board[currentY - 1][currentX - 1].getPiece().getColor().equals(BLACK)){
                        Coordinate c = new Coordinate(currentX - 1, currentY - 1);
                        possibleMoves.add(c);
                    }
                }
            }
            if(DefaultModel.validSpace(currentX + 1, currentY - 1)){
                if(board[currentY - 1][currentX + 1].isOccupied()){
                    if(board[currentY - 1][currentX + 1].getPiece().getColor().equals(BLACK)){
                        Coordinate c = new Coordinate(currentX + 1, currentY - 1);
                        possibleMoves.add(c);
                    }
                }
            }
            
            if(p.isUnmoved()){
                if((DefaultModel.validSpace(currentX, currentY - 1)) && (DefaultModel.validSpace(currentX, currentY - 2))){
                    if((board[currentY - 1][currentX].isNotOccupied()) && (board[currentY - 2][currentX].isNotOccupied())){
                        Coordinate c = new Coordinate(currentX, currentY - 2);
                        possibleMoves.add(c);
                    }
                }
            }
            
        }
        else{
            if(DefaultModel.validSpace(currentX, currentY + 1)){
                if(board[currentY + 1][currentX].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY + 1);
                    possibleMoves.add(c);
                }
            }
            if(DefaultModel.validSpace(currentX - 1, currentY + 1)){
                if(board[currentY + 1][currentX - 1].isOccupied()){
                    if(board[currentY + 1][currentX - 1].getPiece().getColor().equals(WHITE)){
                        Coordinate c = new Coordinate(currentX - 1, currentY + 1);
                        possibleMoves.add(c);
                    }
                }
            }
            if(DefaultModel.validSpace(currentX + 1, currentY + 1)){
                if(board[currentY + 1][currentX +1].isOccupied()){
                    if(board[currentY + 1][currentX +1].getPiece().getColor().equals(WHITE)){
                        Coordinate c = new Coordinate(currentX + 1, currentY + 1);
                        possibleMoves.add(c);
                    }
                }
            }
            
            if(p.isUnmoved()){
                if((DefaultModel.validSpace(currentX, currentY + 1)) && (DefaultModel.validSpace(currentX, currentY + 2))){
                    if((board[currentY + 1][currentX].isNotOccupied()) && (board[currentY + 2][currentX].isNotOccupied())){
                        Coordinate c = new Coordinate(currentX, currentY + 2);
                        possibleMoves.add(c);
                    }
                }
            }
        }
        
        return possibleMoves;
    }
    public static ArrayList<Coordinate> possibleMoves(BoardSpace[][] board,Rook r){
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

            if(DefaultModel.validSpace(currentX, currentY + yMod)){
                if(board[currentY + yMod][currentX].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX].getPiece().getColor().equals(color)){
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

            if(DefaultModel.validSpace(currentX, currentY + yMod)){
                if(board[currentY + yMod][currentX].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX].getPiece().getColor().equals(color)){
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

            if(DefaultModel.validSpace(currentX + xMod, currentY)){
                if(board[currentY][currentX + xMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY][currentX + xMod].getPiece().getColor().equals(color)){
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

            if(DefaultModel.validSpace(currentX + xMod, currentY)){
                if(board[currentY][currentX + xMod].isNotOccupied()){
                     Coordinate c = new Coordinate(currentX + xMod, currentY);
                     possibleMoves.add(c);
                }
                else{
                    if(!board[currentY][currentX + xMod].getPiece().getColor().equals(color)){
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
    public static ArrayList<Coordinate> possibleMoves(BoardSpace[][] board,Bishop b){
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
            
            if(DefaultModel.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentY + yMod][currentX + xMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX + xMod].getPiece().getColor().equals(color)){
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
            
            if(DefaultModel.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentY + yMod][currentX + xMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX + xMod].getPiece().getColor().equals(color)){
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
            
            if(DefaultModel.validSpace(currentX +xMod, currentY + yMod)){
                if(board[currentY + yMod][currentX + xMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX +xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX + xMod].getPiece().getColor().equals(color)){
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
            
            if(DefaultModel.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentY + yMod][currentX + xMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX + xMod].getPiece().getColor().equals(color)){
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
    public static ArrayList<Coordinate> possibleMoves(BoardSpace[][] board,Knight k){
        ArrayList<Coordinate> possibleMoves = new ArrayList();
        
        String color = k.getColor();
        
        int x = k.getCoordinate().getX();
        int y = k.getCoordinate().getY();
        
        //Check Positions to the West
        if(DefaultModel.validSpace(x - 2, y - 1)){
            BoardSpace space = board[y-1][x-2];
            Coordinate coord = new Coordinate(x - 2, y - 1);
            if(space.isNotOccupied()){
                possibleMoves.add(coord);
            }
            else{
                if(!space.getPiece().getColor().equals(color)){
                    possibleMoves.add(coord);
                }
            }
        }
        if(DefaultModel.validSpace(x - 2, y + 1)){
            BoardSpace space = board[y+1][x-2];
            Coordinate coord = new Coordinate(x - 2, y + 1);
            if(space.isNotOccupied()){
                possibleMoves.add(coord);
            }
            else{
                if(!space.getPiece().getColor().equals(color)){
                    possibleMoves.add(coord);
                }
            }
        }
        
        //Check Positions to the East
        if(DefaultModel.validSpace(x + 2, y - 1)){
            BoardSpace space = board[y-1][x+2];
            Coordinate coord = new Coordinate(x + 2, y - 1);
            if(space.isNotOccupied()){
                possibleMoves.add(coord);
            }
            else{
                if(!space.getPiece().getColor().equals(color)){
                    possibleMoves.add(coord);
                }
            }
        }
        if(DefaultModel.validSpace(x + 2, y + 1)){
            BoardSpace space = board[y+1][x+2];
            Coordinate coord = new Coordinate(x + 2, y + 1);
            if(space.isNotOccupied()){
                possibleMoves.add(coord);
            }
            else{
                if(!space.getPiece().getColor().equals(color)){
                    possibleMoves.add(coord);
                }
            }
        }
        
        //Check Positions to the North
        if(DefaultModel.validSpace(x - 1, y - 2)){
            BoardSpace space = board[y-2][x-1];
            Coordinate coord = new Coordinate(x - 1, y - 2);
            if(space.isNotOccupied()){
                possibleMoves.add(coord);
            }
            else{
                if(!space.getPiece().getColor().equals(color)){
                    possibleMoves.add(coord);
                }
            }
        }
        if(DefaultModel.validSpace(x + 1, y - 2)){
            BoardSpace space = board[y-2][x+1];
            Coordinate coord = new Coordinate(x + 1, y - 2);
            if(space.isNotOccupied()){
                possibleMoves.add(coord);
            }
            else{
                if(!space.getPiece().getColor().equals(color)){
                    possibleMoves.add(coord);
                }
            }
        }
        //Check Positions to the South
        if(DefaultModel.validSpace(x - 1, y + 2)){
            BoardSpace space = board[y+2][x-1];
            Coordinate coord = new Coordinate(x - 1, y + 2);
            if(space.isNotOccupied()){
                possibleMoves.add(coord);
            }
            else{
                if(!space.getPiece().getColor().equals(color)){
                    possibleMoves.add(coord);
                }
            }
        }
        if(DefaultModel.validSpace(x + 1, y + 2)){
            BoardSpace space = board[y+2][x+1];
            Coordinate coord = new Coordinate(x + 1, y + 2);
            if(space.isNotOccupied()){
                possibleMoves.add(coord);
            }
            else{
                if(!space.getPiece().getColor().equals(color)){
                    possibleMoves.add(coord);
                }
            }
        }
        
        
        
        
        return possibleMoves;
    }
    
    public static ArrayList<Coordinate> possibleMoves(BoardSpace[][] board, King k){
        ArrayList<Coordinate> possibleMoves = new ArrayList();
        
        return possibleMoves;
    }
    
    public static ArrayList<Coordinate> possibleMoves(BoardSpace[][] board,King k, HashMap<String,ArrayList<Coordinate>> opponentMoves){
        ArrayList<Coordinate> possibleMoves = new ArrayList();
        
        String color = k.getColor();
        
        int x = k.getCoordinate().getX();
        int y = k.getCoordinate().getY();
        
        
        
        
        //North Search
        if(DefaultModel.validSpace(x, y - 1)){
            Coordinate northCoord = new Coordinate(x, y - 1);
            if(board[y - 1][x].isNotOccupied()){
                if(!DefaultModel.isCheck(northCoord, opponentMoves))
                    possibleMoves.add(northCoord);
            }
            else{
                if(!board[y - 1][x].getPiece().getColor().equals(color)){
                    if(!DefaultModel.isCheck(northCoord, opponentMoves))
                        possibleMoves.add(northCoord);
                }
            }
        }
        //South Search
        if(DefaultModel.validSpace(x, y +1)){
            Coordinate southCoord = new Coordinate(x, y +1);
            if(board[y+1][x].isNotOccupied()){
                if(!DefaultModel.isCheck(southCoord, opponentMoves))
                    possibleMoves.add(southCoord);
            }
            else{
                if(!board[y+1][x].getPiece().getColor().equals(color)){
                    if(!DefaultModel.isCheck(southCoord, opponentMoves))
                        possibleMoves.add(southCoord);
                }
            }
        }
        //West Search
        if(DefaultModel.validSpace(x - 1, y)){
            Coordinate westCoord = new Coordinate(x-1,y);
            if(board[y][x-1].isNotOccupied()){
                if(!DefaultModel.isCheck(westCoord, opponentMoves))
                    possibleMoves.add(westCoord);
            }
            else{
                if(!board[y][x-1].getPiece().getColor().equals(color)){
                    if(!DefaultModel.isCheck(westCoord, opponentMoves))
                        possibleMoves.add(westCoord);
                }
            }
        }
        //East Search
        if(DefaultModel.validSpace( x + 1, y)){
            Coordinate eastCoord = new Coordinate(x + 1, y);
            if(board[y][x+1].isNotOccupied()){
                if(!DefaultModel.isCheck(eastCoord, opponentMoves))
                    possibleMoves.add(eastCoord);
            }
            else{
                if(!board[y][x+1].getPiece().getColor().equals(color)){
                    if(!DefaultModel.isCheck(eastCoord, opponentMoves))
                        possibleMoves.add(eastCoord);
                }
            }
        }
        //North West Search
        if(DefaultModel.validSpace(x - 1, y - 1)){
            Coordinate northWestCoord = new Coordinate(x - 1, y - 1);
            if(board[y - 1][x-1].isNotOccupied()){
                if(!DefaultModel.isCheck(northWestCoord, opponentMoves))
                        possibleMoves.add(northWestCoord);
            }
            else{
                if(!board[y-1][x-1].getPiece().getColor().equals(color)){
                    if(!DefaultModel.isCheck(northWestCoord, opponentMoves))
                        possibleMoves.add(northWestCoord);
                }
            }
        }
        //North East Search
        if(DefaultModel.validSpace(x + 1, y - 1)){
            Coordinate northEastCoord = new Coordinate(x + 1, y - 1);
            if(board[y - 1][x + 1].isNotOccupied()){
                if(!DefaultModel.isCheck(northEastCoord, opponentMoves))
                    possibleMoves.add(northEastCoord);
            }
            else{
                if(!board[y - 1][x + 1].getPiece().getColor().equals(color)){
                    if(!DefaultModel.isCheck(northEastCoord, opponentMoves))
                        possibleMoves.add(northEastCoord);
                }
            }
        }
        //South West Search
        if(DefaultModel.validSpace(x - 1, y + 1)){
            Coordinate southWestCoord = new Coordinate(x - 1, y + 1);
            if(board[y + 1][x - 1].isNotOccupied()){
                if(!DefaultModel.isCheck(southWestCoord, opponentMoves))
                    possibleMoves.add(southWestCoord);
            }
            else{
                if(!board[y + 1][x-1].getPiece().getColor().equals(color)){
                    if(!DefaultModel.isCheck(southWestCoord, opponentMoves))
                        possibleMoves.add(southWestCoord);
                }
            }
        }
        //South East Coordinate
        if(DefaultModel.validSpace(x + 1, y + 1)){
            Coordinate southEastCoord = new Coordinate(x + 1, y + 1);
            if(board[y+1][x+1].isNotOccupied()){
                if(!DefaultModel.isCheck(southEastCoord, opponentMoves))
                    possibleMoves.add(southEastCoord);
            }
            else{
                if(!board[y+1][x+1].getPiece().getColor().equals(color)){
                    if(!DefaultModel.isCheck(southEastCoord, opponentMoves))
                        possibleMoves.add(southEastCoord);
                }
            }
        }
        
        /*if(westCastleAvailable(board,k,opponentMoves)){
            Coordinate castling = new Coordinate(2,7);
            possibleMoves.add(castling);
        }*/
        
        return possibleMoves;
    }
    public static ArrayList<Coordinate> possibleMoves(BoardSpace[][] board,Queen q){
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

            if(DefaultModel.validSpace(currentX, currentY + yMod)){
                if(board[currentY + yMod][currentX].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX].getPiece().getColor().equals(color)){
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

            if(DefaultModel.validSpace(currentX, currentY + yMod)){
                if(board[currentY + yMod][currentX].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX].getPiece().getColor().equals(color)){
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

            if(DefaultModel.validSpace(currentX + xMod, currentY)){
                if(board[currentY][currentX + xMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY][currentX + xMod].getPiece().getColor().equals(color)){
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

            if(DefaultModel.validSpace(currentX + xMod, currentY)){
                if(board[currentY][currentX + xMod].isNotOccupied()){
                     Coordinate c = new Coordinate(currentX + xMod, currentY);
                     possibleMoves.add(c);
                }
                else{
                    if(!board[currentY][currentX + xMod].getPiece().getColor().equals(color)){
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
            
            if(DefaultModel.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentY + yMod][currentX + xMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX + xMod].getPiece().getColor().equals(color)){
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
            
            if(DefaultModel.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentY + yMod][currentX + xMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX + xMod].getPiece().getColor().equals(color)){
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
            
            if(DefaultModel.validSpace(currentX +xMod, currentY + yMod)){
                if(board[currentY + yMod][currentX + xMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX +xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX + xMod].getPiece().getColor().equals(color)){
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
            
            if(DefaultModel.validSpace(currentX + xMod, currentY + yMod)){
                if(board[currentY + yMod][currentX + xMod].isNotOccupied()){
                    Coordinate c = new Coordinate(currentX + xMod, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board[currentY + yMod][currentX + xMod].getPiece().getColor().equals(color)){
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
    public static boolean westCastleAvailable(BoardSpace[][] board, King k, HashMap<String,ArrayList<Coordinate>> opponentMoves){
        boolean available = k.isUnmoved();
        
        if(k.isUnmoved()){
            
            int startingY = 7;
            
            int rookX = 0;
            int knightX = 1;
            int bishopX = 2;
            int queenX = 3;
            
            if(board[startingY][knightX].isOccupied())
                available = false;
            if(board[startingY][bishopX].isOccupied())
                available = false;
            if(board[startingY][queenX].isOccupied())
                available = false;
            
            if(board[startingY][rookX].isNotOccupied())
                available = false;
            else{
                Piece p = board[startingY][rookX].getPiece();
                
                if(p.getType().equals(Piece.ROOK)){
                    if(!p.isUnmoved())
                        available = false;
                }
                else
                    available = false;
            }
            
            if(available){
                Coordinate adjacent = new Coordinate(queenX,startingY);
                Coordinate target = new Coordinate(bishopX,startingY);
            
                boolean adjacentChecked = DefaultModel.isCheck(adjacent, opponentMoves);
                boolean targetChecked = DefaultModel.isCheck(target, opponentMoves);
                
                if(adjacentChecked || targetChecked)
                    available = false;
                
            }   
        }
 
        return available;
    }
    public static boolean wasWestCastle(Coordinate previous, Coordinate target){
        boolean wasCastle = false;
        
        int distance = (int)getDistance(previous,target);
        
        
        if((distance == CASTLE_DISTANCE) && AWestOfB(target,previous))
            wasCastle = true;
        
        return wasCastle;
    }
    
    public static boolean wasEastCastle(Coordinate previous, Coordinate target){
        boolean wasCastle = false;
        
        int distance = (int)getDistance(previous,target);
        
        
        if((distance == CASTLE_DISTANCE) && AEastOfB(target,previous))
            wasCastle = true;
        
        return wasCastle;
    }
    
    public static boolean AWestOfB(Coordinate a, Coordinate b){
        
        boolean westOf = false;
        
        int xOfA = a.getX();
        int xOfB = b.getX();
        
        if(xOfA < xOfB)
            westOf = true;
        
        return westOf;
    }
    public static boolean AEastOfB(Coordinate a, Coordinate b){
        boolean eastOf = false;
        
        int xOfA = a.getX();
        int xOfB = b.getX();
        
        if(xOfA > xOfB)
            eastOf = true;
        
        return eastOf;
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
