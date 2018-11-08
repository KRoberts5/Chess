/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.model;

/**
 *
 * @author Brendan
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;

public class ArtificialIntelligence  {
    
    public static final double MAX = 16;
    public static final String NAME = "NAME";
    public static final String VALUE = "VALUE";
    
    
    public static HashMap<String,Object> getDecision(BoardSpace[][] board,Player whitePlayer,HashMap<String,ArrayList<Coordinate>> blackMoves,HashMap<String,ArrayList<Coordinate>> whiteMoves){
        HashMap<String,Object> chosenMove = new HashMap();
        
        Coordinate whiteKingSpace = whitePlayer.getKing().getCoordinate();
        
        ArrayList<HashMap<String,Object>> primeMoves = new ArrayList();
        
        for(HashMap.Entry<String, ArrayList<Coordinate>> e: blackMoves.entrySet()){
            HashMap<String,Object> primeMove = new HashMap();
            
            ArrayList<Coordinate> moves = e.getValue();
            
            for(Coordinate c : moves){
                Double value = calculateMoveValue(c,whiteKingSpace,board,blackMoves,whiteMoves);
                
                if(primeMove.isEmpty()){
                    primeMove.put(NAME, e.getKey());
                    primeMove.put(VALUE, value);
                    primeMove.put(e.getKey(), c);
                    
                }
                else{
                    Double currentValue = (Double)primeMove.get(VALUE);
                    if(value > currentValue){
                        primeMove.put(VALUE, value);
                        primeMove.put(e.getKey(), c);
                    }
                }
            }
        }
        
        
        
        return chosenMove;
    }
    
    private static Double calculateMoveValue(Coordinate coordinate,Coordinate opponentKing,BoardSpace[][] board,HashMap<String,ArrayList<Coordinate>> blackMoves,HashMap<String,ArrayList<Coordinate>> whiteMoves){
        Double value = new Double(0);
        
        double opponentKingRisk = calculateOpponentKingRisk(coordinate,opponentKing);
        double selfRisk = calculateSelfRisk(coordinate,whiteMoves);
        double opponentPieceRisk = calculateOpponentPieceRisk(coordinate,blackMoves);
        int opponentCapture = calculateOpponentCapture(coordinate,board);
        
        value += opponentKingRisk;
        value -= selfRisk;
        value += opponentPieceRisk;
        value += opponentCapture;
        
        
        return value;
    }
    
    private static double calculateOpponentKingRisk(Coordinate potentialMove, Coordinate opponentKingPosition){
        double risk = 0;
        
        
        double distance = Logic.getDistance(potentialMove, opponentKingPosition) - 1;
        risk = (1 - (distance/MAX)) * 100;
        
        
        return risk;
    }
    
    private static double calculateSelfRisk(Coordinate c, HashMap<String,ArrayList<Coordinate>> whiteMoves){
        double risk = 0;
        
        int potentialAttackers = 0;
        
        for(HashMap.Entry<String,ArrayList<Coordinate>> e: whiteMoves.entrySet()){
            ArrayList<Coordinate> moves = e.getValue();
            
            for(Coordinate coordinate : moves){
                if(coordinate.equals(c))
                    ++potentialAttackers;
            }
        }
        
        risk = (potentialAttackers/MAX)*100;
        
        return risk;
    }
    private static double calculateOpponentPieceRisk(Coordinate c, HashMap<String,ArrayList<Coordinate>> blackMoves){
        double risk = 0;
        
        int potentialAttackers = 0;
        
        for(HashMap.Entry<String,ArrayList<Coordinate>> e: blackMoves.entrySet()){
            ArrayList<Coordinate> moves = e.getValue();
            
            for(Coordinate coordinate : moves){
                if(coordinate.equals(c))
                    ++potentialAttackers;
            }
        }
        
        risk = (potentialAttackers/MAX)*100;
        
        return risk;
    }
    private static int calculateOpponentCapture(Coordinate c, BoardSpace[][] board){
        int chance = 0;
        
        int x = c.getX();
        int y = c.getY();
        
        if(board[y][x].isOccupied())
            chance = 100;
        
        return chance;
    }
    
}
