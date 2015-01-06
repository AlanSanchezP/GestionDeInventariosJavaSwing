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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Alumno
 */
public class ventana_login extends JFrame implements ActionListener {
    
    private JLabel e_em, e_pass;
    private JTextField c_em;
    private JPasswordField c_pass; 
    private JButton boton1, boton2;
    private Container contenedor;
    
    private ResultSet rs;
    private crear_conexion conexion;
    private validaciones valida;
    
    public ventana_login(){
        rs = null;
        conexion = new crear_conexion();
        
        valida = new validaciones();
        contenedor=getContentPane();
        contenedor.setLayout(null);
        setTitle("Registro y Login de usuario");
        setSize(260, 280);
        setLocationRelativeTo(null);
        setResizable(false);
        
        
        e_em = new JLabel("Email");
        e_em.setBounds(30, 40, 100, 20);
        
        c_em = new JTextField();
        c_em.setBounds(30, 60, 200, 35);
        
        e_pass = new JLabel("Password");
        e_pass.setBounds(30, 100, 100, 20);
        
        c_pass = new JPasswordField();
        c_pass.setBounds(30, 120, 200, 35);
        
        boton1 = new JButton("Registrarse");
        boton1.setBounds(10, 170, 120, 30);
        boton1.addActionListener(this);
        
        
        boton2 = new JButton("Log in");
        boton2.setBounds(145, 170, 100, 30);
        boton2.addActionListener(this);
        
        
        contenedor.add(e_em);
        contenedor.add(c_em);
        contenedor.add(e_pass);
        contenedor.add(c_pass);
        contenedor.add(boton1);
        contenedor.add(boton2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) throws UnsupportedOperationException{ 
        if(e.getSource().equals(boton1)){
            String email = c_em.getText();
            String pass = new String(c_pass.getPassword()); 
            
            
            if(valida.validaEmail(email) == true && valida.validaPassword(pass) == true){ 
                encriptacion ob = new encriptacion();

                String pass_en = ob.cifrar(pass);

                System.out.println("Contraseña original: " + pass);
                System.out.println("Contraseña cifrada: " + pass_en);
                try {
                    conexion.conectar();
                    rs = conexion.consulta("call registrar('" +
                             email + "', '" + pass_en + "');");
                    if(rs.next()){
                        String mensaje = rs.getString("mensaje");
                        c_em.setText("");
                        c_pass.setText("");
                        JOptionPane.showMessageDialog(this, mensaje);
                    }
                    rs.close();
                    conexion.cerrar();

                } catch (Exception ex) {
                    Logger.getLogger(ventana_login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(valida.validaEmail(email) == false){
                JOptionPane.showMessageDialog(this, "Ese no es un email");
            }
            if(valida.validaPassword(pass) == false){
                JOptionPane.showMessageDialog(this, "La contraseña debe contener al menos 6 caracteres.");
            }
        }
        if(e.getSource().equals(boton2)){
            try {
                conexion.conectar();
                String vemail = c_em.getText();
                String vpass = new String(c_pass.getPassword());
                if(valida.validaEmail(vemail) == true && valida.validaPassword(vpass) == true){ 
                    encriptacion ob = new encriptacion();
                    String pass_en = ob.cifrar(vpass);
                    String mensaje;
                    rs = conexion.consulta("call verificar_existe('" + vemail + "', " +
                            "'" + pass_en + "');");
                    if(rs.next()){
                        mensaje = rs.getString("mensaje");
                        if(mensaje.equals("Bienvenid@")){
                            JOptionPane.showMessageDialog(this, mensaje);
                            EventQueue.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                  menu_principal menup = new menu_principal();  
                                }
                            }); 
                            this.dispose();
                        }
                        else if(mensaje.equals("Error. Password incorrecto.")){
                            JOptionPane.showMessageDialog(this, mensaje);
                            rs.close();
                            conexion.cerrar();
                        }
                        else{
                            JOptionPane.showMessageDialog(this, mensaje);
                            rs.close();
                            conexion.cerrar();
                        }
                    }
                }
                if(valida.validaEmail(vemail) == false){
                JOptionPane.showMessageDialog(this, "Ese no es un email");
                }
                if(valida.validaPassword(vpass) == false){
                    JOptionPane.showMessageDialog(this, "La contraseña debe contener al menos 6 caracteres.");
                }
            }
             catch (Exception ex) {
                Logger.getLogger(ventana_login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
