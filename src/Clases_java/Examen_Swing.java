/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases_java;

import java.awt.EventQueue;
import javax.swing.UIManager;

/**
 *
 * @author Familia
 */
public class Examen_Swing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
              ventana_login login = new ventana_login();
            }
        });
    }
    
}
