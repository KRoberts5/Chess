/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author Brendan
 */

import java.util.ArrayList;
public class GameLogic {
    
    public static final int MAX_SQUARE = 8;
    public static final int MIN_SQUARE = 0;
    public static final String WHITE = "WHITE";
    public static final String BLACK = "BLACK";
    
    
    private boolean staleMate;
    
    private Board board;
    
    public ArrayList<Coordinate> getPossibleMoves(Piece p){
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();
        
        if(p instanceof Pawn)
            possibleMoves = this.getPossibleMoves((Pawn) p);
        if(p instanceof Rook)
            possibleMoves = this.getPossibleMoves((Rook) p);
        
        return possibleMoves;
    }
    
    public ArrayList<Coordinate> getPossibleMoves(Pawn p){
        ArrayList<Coordinate> possibleMoves = new ArrayList<>();
        
        String color = p.getColor();
        int currentX = p.getCoordinate().getX();
        int currentY = p.getCoordinate().getY();

        if(color.equals(GameLogic.WHITE)){
            
            if(board.validSpace(currentX, currentY - 1)){
                if(board.spaceNotOccupied(currentX, currentY - 1)){
                    Coordinate c = new Coordinate(currentX, currentY - 1);
                    possibleMoves.add(c);
                }
            }
            if(board.validSpace(currentX - 1, currentY - 1)){
                if(board.spaceOccupied(currentX - 1, currentY - 1)){
                    if(board.getSpace(currentX - 1, currentY - 1).getPiece().getColor().equals(BLACK)){
                        Coordinate c = new Coordinate(currentX - 1, currentY - 1);
                        possibleMoves.add(c);
                    }
                }
            }
            if(board.validSpace(currentX + 1, currentY - 1)){
                if(board.spaceOccupied(currentX + 1, currentY - 1)){
                    if(board.getSpace(currentX + 1, currentY - 1).getPiece().getColor().equals(BLACK)){
                        Coordinate c = new Coordinate(currentX + 1, currentY - 1);
                        possibleMoves.add(c);
                    }
                }
            }
            
            if(p.unmoved()){
                if((board.validSpace(currentX, currentY - 1)) && (board.validSpace(currentX, currentY - 2))){
                    if((board.spaceNotOccupied(currentX, currentY - 1)) && (board.spaceNotOccupied(currentX, currentY - 2))){
                        Coordinate c = new Coordinate(currentX, currentY - 2);
                        possibleMoves.add(c);
                    }
                }
            }
            
        }
        else{
            if(board.validSpace(currentX, currentY + 1)){
                if(board.spaceNotOccupied(currentX, currentY + 1)){
                    Coordinate c = new Coordinate(currentX, currentY + 1);
                    possibleMoves.add(c);
                }
            }
            if(board.validSpace(currentX - 1, currentY + 1)){
                if(board.spaceOccupied(currentX - 1, currentY + 1)){
                    if(board.getSpace(currentX - 1, currentY + 1).getPiece().getColor().equals(WHITE)){
                        Coordinate c = new Coordinate(currentX - 1, currentY + 1);
                        possibleMoves.add(c);
                    }
                }
            }
            if(board.validSpace(currentX + 1, currentY + 1)){
                if(board.spaceOccupied(currentX + 1, currentY + 1)){
                    if(board.getSpace(currentX + 1, currentY + 1).getPiece().getColor().equals(WHITE)){
                        Coordinate c = new Coordinate(currentX + 1, currentY + 1);
                        possibleMoves.add(c);
                    }
                }
            }
            
            if(p.unmoved()){
                if((board.validSpace(currentX, currentY + 1)) && (board.validSpace(currentX, currentY + 2))){
                    if((board.spaceNotOccupied(currentX, currentY + 1)) && (board.spaceNotOccupied(currentX, currentY + 2))){
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

            if(board.validSpace(currentX, currentY + yMod)){
                if(board.spaceNotOccupied(currentX, currentY + yMod)){
                    Coordinate c = new Coordinate(currentX, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board.getSpace(currentX, currentY + yMod).getPiece().getColor().equals(color)){
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

            if(board.validSpace(currentX, currentY + yMod)){
                if(board.spaceNotOccupied(currentX, currentY + yMod)){
                    Coordinate c = new Coordinate(currentX, currentY + yMod);
                    possibleMoves.add(c);
                }
                else{
                    if(!board.getSpace(currentX, currentY + yMod).getPiece().getColor().equals(color)){
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

            if(board.validSpace(currentX + xMod, currentY)){
                if(board.spaceNotOccupied(currentX + xMod, currentY)){
                    Coordinate c = new Coordinate(currentX + xMod, currentY);
                    possibleMoves.add(c);
                }
                else{
                    if(!board.getSpace(currentX + xMod, currentY).getPiece().getColor().equals(color)){
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

            if(board.validSpace(currentX + xMod, currentY)){
                if(board.spaceNotOccupied(currentX + xMod, currentY)){
                     Coordinate c = new Coordinate(currentX + xMod, currentY);
                     possibleMoves.add(c);
                }
                else{
                    if(!board.getSpace(currentX + xMod, currentY).getPiece().getColor().equals(color)){
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
    
    
}
