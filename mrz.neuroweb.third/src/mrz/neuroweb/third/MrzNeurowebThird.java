/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrz.neuroweb.third;

import javax.swing.JFrame;
import web.Window;

/**
 *
 * @author NafanyaVictorovna
 */
public class MrzNeurowebThird {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Window window = new Window("relaxation web");
        window.setSize(500,500);
        window.setResizable(true);
        window.setLocation(420, 170);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
        window.repaint();
    }
    
}
