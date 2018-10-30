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
    
    private Color beige;
    private Color brown;
    private Color green;
    
    public ViewTrackingGrid(DefaultController controller, int playerId) {
        super();
        this.controller = controller;        
        
        beige = new Color(239,227,178);
        brown = new Color(150,94,62);
        green = new Color(122,217,149);
    }
    public void modelPropertyChange(final PropertyChangeEvent e){
        
    }
}
