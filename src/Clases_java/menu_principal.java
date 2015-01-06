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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Familia
 */
public class menu_principal extends JFrame implements ActionListener{

    private JButton factura, cliente, salir;
    private JLabel texto;
    private Container contenedor;
    
    
    public menu_principal(){
        
        contenedor= getContentPane();
        contenedor.setLayout(null);
        setTitle("Menú principal");
        setSize(530, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        
        factura = new JButton("Catalogos");
        factura.setActionCommand(""); 
        factura.addActionListener(this); 
        factura.setBounds(30, 100, 150, 40);
        
        salir = new JButton("Log out");
        salir.setActionCommand(""); 
        salir.addActionListener(this); 
        salir.setBounds(350, 100, 150, 40);
        
        cliente = new JButton("Clientes");
        cliente.setActionCommand(""); 
        cliente.addActionListener(this); 
        cliente.setBounds(190, 100, 150, 40);
        
        texto = new JLabel();
        texto.setBounds(220, 20, 200, 50);
        texto.setText("Elija una opción");
        
        contenedor.add(factura);
        contenedor.add(cliente);
        contenedor.add(salir);
        contenedor.add(texto);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) throws UnsupportedOperationException{
        if(e.getSource().equals(factura)) {
            EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
              menu_catalogos vcatalogo = new menu_catalogos();
            }
        });
            this.dispose();
        }
        if(e.getSource().equals(cliente)) {
            EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
              menu_clientes vcliente = new menu_clientes();
            }
        });
            this.dispose();
        }
        if(e.getSource().equals(salir)) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                  ventana_login lg = new ventana_login(); 
                }
            });
            this.dispose();
        }
    }
    
}
