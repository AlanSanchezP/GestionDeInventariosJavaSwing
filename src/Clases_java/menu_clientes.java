/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases_java;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Familia
 */
public class menu_clientes extends JFrame implements ActionListener{
    private  Container contenedor;
    private JButton nueva, tabla, volver;
    private JLabel texto;
    
    private ResultSet rs;
    private crear_conexion conexion;

    public menu_clientes(){
        rs = null;
        conexion = new crear_conexion();
        
        
        contenedor=getContentPane();
        contenedor.setLayout(null);
        setTitle("Menu de clientes");
        setSize(540, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        
        nueva = new JButton("Nuevo cliente");
        nueva.setActionCommand(""); 
        nueva.addActionListener(this); 
        nueva.setBounds(30, 100, 150, 40);
        
        texto = new JLabel();
        texto.setBounds(225, 20, 200, 50);
        texto.setText("Elija una opción");
        
        tabla = new JButton("Ver clientes");
        tabla.setActionCommand(""); 
        tabla.addActionListener(this); 
        tabla.setBounds(190, 100, 150, 40);
        
        
        volver = new JButton("Volver");
        volver.setActionCommand(""); 
        volver.addActionListener(this); 
        volver.setBounds(350, 100, 150, 40);
        
        contenedor.add(volver);
        contenedor.add(texto);
        contenedor.add(nueva);
        contenedor.add(tabla);
        
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) throws UnsupportedOperationException{
        int id_fac = 0;
        String fecha = "";
        if(e.getSource().equals(nueva)) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                  nuevo_cliente vnuevo = new nuevo_cliente(); 
                }
            });
            
            
        }
        if(e.getSource().equals(tabla)) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                  tabla_clientes vtabla = new tabla_clientes();
                }
            });
        }
        if(e.getSource().equals(volver)) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                  menu_principal ventana = new menu_principal();
                }
            });
            this.dispose();
        }
    }
    
}
