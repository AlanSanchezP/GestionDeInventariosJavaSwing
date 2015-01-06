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
import javax.swing.JTextField;
/**
 *
 * @author Familia
 */
public class nuevo_producto extends JDialog implements ActionListener{
    private final Container contenedor;
    private JButton agregar;
    private JTextField nombre, ccompra, cventa, imagen, cantidad;
    private JLabel datos, nomb, ccom, cven, im, can;
    private int[] ids;
    
    private ResultSet rs;
    private crear_conexion conexion;
    
    int id_categoria;
    
    public nuevo_producto(int idcat){
        id_categoria = idcat;
        rs = null;
        conexion = new crear_conexion();
        setModal(true);
        contenedor=getContentPane();
        contenedor.setLayout(null);
        setTitle("Nuevo producto");
        setSize(260, 460);
        setLocationRelativeTo(null);
        setResizable(false);
        
        
        datos = new JLabel("Datos del producto");
        datos.setBounds(70, 10, 200, 30);
        
        
        nomb = new JLabel("Descripción");
        nomb.setBounds(30, 50, 200, 30);
        
        nombre = new JTextField();
        nombre.setBounds(30,90,200,30);
        
        ccom = new JLabel("Costo de compra");
        ccom.setBounds(30, 110, 200, 30);
        
        ccompra = new JTextField();
        ccompra.setBounds(30,140,200,30);
        
        cven = new JLabel("Costo de venta");
        cven.setBounds(30, 170, 200, 30);
        
        cventa = new JTextField();
        cventa.setBounds(30,200,200,30);
        
        im = new JLabel("URL de imagen");
        im.setBounds(30, 230, 200, 30);
        
        imagen = new JTextField();
        imagen.setBounds(30, 260, 200, 30);
        
        can = new JLabel("Cantidad en inventario");
        can.setBounds(30, 290, 200, 30);
        
        cantidad = new JTextField();
        cantidad.setBounds(30, 320, 200, 30);
        
        agregar = new JButton("Agregar producto");
        agregar.setBounds(55, 360, 150, 40);
        agregar.addActionListener(this);
        
        contenedor.add(datos);
        contenedor.add(agregar);
        contenedor.add(nombre);
        contenedor.add(nomb);
        contenedor.add(ccom);
        contenedor.add(cven);
        contenedor.add(ccompra);
        contenedor.add(cventa);
        contenedor.add(can);
        contenedor.add(cantidad);
        contenedor.add(im);
        contenedor.add(imagen);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) throws UnsupportedOperationException{
        if(e.getSource().equals(agregar)) {
            String vnombre = nombre.getText();
            String vcom = ccompra.getText();
            String vven = cventa.getText();
            String vim = imagen.getText();
            String vcan = cantidad.getText();
            String mensaje = "";
            try {
                conexion.conectar();
                rs = conexion.consulta("call nuevo_producto('" + vnombre + "', " + vcom + ", "
                        + vven + ", " + vcan + ", '" + vim + "', " + id_categoria + ");");
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
