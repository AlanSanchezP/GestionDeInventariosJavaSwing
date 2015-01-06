/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases_java;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 *
 * @author Familia
 */
public class nuevo_cliente extends JDialog implements ActionListener{
    private final Container contenedor;
    private JButton agregar;
    private JTextField nombre, apaterno, amaterno, email;
    private JPasswordField pass;
    private JLabel datos, nomb, apat, amat, em, pas;
    private int[] ids;
    
    private ResultSet rs;
    private crear_conexion conexion;
    
    public nuevo_cliente(){
        
        rs = null;
        conexion = new crear_conexion();
        setModal(true);
        contenedor=getContentPane();
        contenedor.setLayout(null);
        setTitle("Nueva factura");
        setSize(260, 460);
        setLocationRelativeTo(null);
        setResizable(false);
        
        
        datos = new JLabel("Datos del cliente");
        datos.setBounds(80, 10, 200, 30);
        
        
        nomb = new JLabel("Nombre(s) del cliente");
        nomb.setBounds(30, 50, 200, 30);
        
        nombre = new JTextField();
        nombre.setBounds(30,90,200,30);
        
        apat = new JLabel("Apellido paterno");
        apat.setBounds(30, 110, 200, 30);
        
        apaterno = new JTextField();
        apaterno.setBounds(30,140,200,30);
        
        amat = new JLabel("Apellido materno");
        amat.setBounds(30, 170, 200, 30);
        
        amaterno = new JTextField();
        amaterno.setBounds(30,200,200,30);
        
        em = new JLabel("Email");
        em.setBounds(30, 230, 200, 30);
        
        email = new JTextField();
        email.setBounds(30, 260, 200, 30);
        
        pas = new JLabel("Password");
        pas.setBounds(30, 290, 200, 30);
        
        pass = new JPasswordField();
        pass.setBounds(30, 320, 200, 30);
        
        agregar = new JButton("Agregar cliente");
        agregar.setBounds(55, 360, 150, 40);
        agregar.addActionListener(this);
        
        contenedor.add(datos);
        contenedor.add(agregar);
        contenedor.add(nombre);
        contenedor.add(nomb);
        contenedor.add(apat);
        contenedor.add(amat);
        contenedor.add(apaterno);
        contenedor.add(amaterno);
        contenedor.add(em);
        contenedor.add(email);
        contenedor.add(pas);
        contenedor.add(pass);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) throws UnsupportedOperationException{
        if(e.getSource().equals(agregar)) {
            String vnombre = nombre.getText();
            String vapat = apaterno.getText();
            String vamat = amaterno.getText();
            String vemail = email.getText();
            String vpass = new String(pass.getPassword());
            encriptacion ob = new encriptacion();
            vpass = ob.cifrar(vpass);
            String mensaje = "";
            try {
                conexion.conectar();
                rs = conexion.consulta("call nuevo_cliente('" + vnombre + "', '" + vapat + "', "
                        + "'" + vamat + "', '" + vemail + "', '" + vpass + "');");
                if(rs.next()){
                    mensaje = rs.getString("mensaje");
                    JOptionPane.showMessageDialog(this, mensaje);
                }
                conexion.cerrar();
                this.dispose();
            } catch (Exception ex) {
                Logger.getLogger(nueva_categoria.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();
        }
    }
    
}
