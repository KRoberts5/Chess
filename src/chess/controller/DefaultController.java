/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.controller;

import chess.controller.AbstractController;
import chess.model.*;

/**
 *
 * @author Brendan
 */


public class DefaultController extends AbstractController {
    public static final String UNOCCUPY_SPACE = "UnoccupySpace";
    public static final String OCCUPY_SPACE = "OccupySpace";
    public static final String VALID_SQUARE_CHOSEN = "ValidSquareChosen";
    public static final String MOVE_CHOSEN = "MoveChosen";
    public static final String CAPTURE_WHITE_PIECE = "CaptureWhitePiece";
    public static final String CAPTURE_BLACK_PIECE = "CaptureBlackPiece";
    public static final String SELECT_PIECE = "SelectPiece";
    public static final String DESELECT_PIECE = "DeselectPiece";
    public static final String SHOW_POSSIBLE_MOVES = "ShowPossibleMoves";
    
    public static final String BLACK_TURN = "blackTurn";
    public static final String WHITE_TURN = "whiteTurn";
    
    public static final String GAME_OVER_STALEMATE = "GameOverStalemate";
    public static final String GAME_OVER_BLACK_WON = "GameOverBlackWon";
    public static final String GAME_OVER_WHITE_WON = "GameOverWhiteWon";
    
    public void moveChosen(Coordinate c){
        setModelProperty(MOVE_CHOSEN,c);
    }
    
}
