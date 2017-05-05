package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import view.StartUpPanel;

public class Main extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private StartUpPanel initialView;
    
    Main() {
        initialView = new StartUpPanel();
        initialView.initialize();
        initialView.setVisible(true);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Main();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
    }
    
}