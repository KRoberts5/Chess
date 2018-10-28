/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.view;

/**
 *
 * @author Brendan
 */

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

import chess.controller.*;

public class ViewTrackingGrid extends JPanel implements AbstractView {
    private final DefaultController controller;
    
    private static final String ICON_ROOT = "/resources/images/";
    
    public ViewTrackingGrid(DefaultController controller, int playerId) {
        super();
        this.controller = controller;        
        
        
    }
    public void modelPropertyChange(final PropertyChangeEvent e){
        
    }
}
