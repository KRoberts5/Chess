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
    
    
}
