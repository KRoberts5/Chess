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
public class Board {
    
    private GridSpace[][] board;
    
    
    public Board(){
        board = new GridSpace[GameLogic.MAX_SQUARE][GameLogic.MAX_SQUARE];
        
        for(int i = 0; i < GameLogic.MAX_SQUARE; ++i){
            for(int j = 0; j < GameLogic.MAX_SQUARE; ++j){
                GridSpace space = new GridSpace(i,j);
                board[i][j] = space;
            } 
        }
    }
    
    public boolean spaceOccupied(int x, int y){
        boolean occupied = board[x][y].isOccupied();
        return occupied;
    }
    public boolean spaceNotOccupied(int x, int y){       
        return !this.spaceNotOccupied(x, y);
    }
    public boolean validSpace(int x, int y){
        boolean valid = false;
        
        if((x >= GameLogic.MIN_SQUARE) && (x < GameLogic.MAX_SQUARE)){
            if((y >= GameLogic.MIN_SQUARE) && (y < GameLogic.MAX_SQUARE))
                valid = true;
        }
        
        return valid;
    }
    
    public GridSpace getSpace(int x, int y){
        return board[x][y];
    }
}
