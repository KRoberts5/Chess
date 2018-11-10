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
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import chess.controller.*;
import chess.model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ViewStartMenu extends JPanel implements AbstractView{
    
    private JPanel buttonPanel;
    private JLabel decision;
    private JPanel start;
    
    private String gameType;
    
    private DefaultController controller;
    
    
    public ViewStartMenu(DefaultController controller){
        
        this.controller = controller;
        this.gameType = DefaultModel.AI;
        
        this.initComponents();
    }
    
    private void initComponents(){
        
        this.setLayout(new GridLayout(0,1));
        this.setBackground(Color.WHITE);
        
        decision = new JLabel("AI");
        
        JPanel decisionPanel = new JPanel();
        decisionPanel.setLayout(new BorderLayout());
        decisionPanel.add(decision,BorderLayout.CENTER);
        
        this.add(decisionPanel);
        

        JButton ai = new JButton("AI");
        ai.setActionCommand(DefaultModel.AI);
        
        
        
        JButton pvp = new JButton("PVP");
        pvp.setActionCommand(DefaultModel.PVP);
        

        ai.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            setGameType(e.getActionCommand());
            }
        });
        pvp.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            setGameType(e.getActionCommand());
            }
        });
        
        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new FlowLayout());
        
        buttonPanel.add(ai);
        buttonPanel.add(pvp);
        
        
        this.add(buttonPanel);
        
        
        
        
        this.start = new JPanel();
        
        JButton startButton = new JButton("Start");
        
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                startGame();
            }
        });
        start.add(startButton);
        
        this.add(start);
        
    }
    
    
    
    public void setGameType(String gameType){
        
        System.out.println(gameType);
        this.gameType = gameType;
        this.decision.setText(gameType);
    }
    
    private void startGame(){
        controller.startGame(gameType);
    }
    
    
    public void modelPropertyChange(PropertyChangeEvent e){
        
    }
}
