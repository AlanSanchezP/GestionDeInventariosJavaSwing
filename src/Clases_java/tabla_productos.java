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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Familia
 */
public class tabla_productos extends JDialog implements ActionListener{
    private final Container contenedor;
    private ResultSet rs;
    private crear_conexion conexion;
    private DefaultTableModel modelo;
    private JTable tabla;
    private JLabel etiqueta;
    private JTextField mproducto;
    private JButton modificar;
    private int id_categoria;
    private int id_producto;
    
    public tabla_productos(int id_cat){
        id_categoria = id_cat;
        setModal(true);
        contenedor=getContentPane();
        contenedor.setLayout(null);
        setTitle("Productos de la categoria");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        conexion = new crear_conexion();
        
        id_producto = 0;
        boolean productos = true;
        
        etiqueta = new JLabel("<html>Ingresa el ID de algun producto para modificarlo.</html>");
        etiqueta.setBounds(35, 310, 200, 50);
        
        mproducto = new JTextField();
        mproducto.setBounds(235, 320, 150, 30);
        
        modificar = new JButton("Modificar producto");
        modificar.setBounds(395, 320, 160, 30);
        modificar.addActionListener(this);
        
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo){
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }};
        
        modelo.addColumn("ID de producto");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Costo compra");
        modelo.addColumn("Costo venta");
        modelo.addColumn("URL de imagen");
        modelo.addColumn("En inventario");
        
        try {
            conexion.conectar();
            rs = conexion.consulta("call existen_productos(" + id_categoria + ");");
            if(rs.next()){
                if(rs.getInt("mensaje") == 0){
                    productos = false;
                }
                else{
                    rs = conexion.consulta("call obtener_productos(" + id_categoria + ");");
                    while(rs.next()){
                        Object [] fila = new Object[6];
                        for(int i = 0; i < 6; i ++){
                            fila[i] = rs.getObject(i+1);
                        }
                        modelo.addRow(fila);
                    }
                }
            }
            
            rs.close();
            conexion.cerrar();
        } catch (SQLException ex) {
            Logger.getLogger(tabla_clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        tabla.setBounds(0,0,560,290);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(15,15,560,285);
        if(productos == true){
            contenedor.add(scroll);
            contenedor.add(mproducto);
            contenedor.add(modificar);
        }
        else{
            etiqueta.setText("Esta categoría no contiene productos.");
            etiqueta.setBounds(200, 150, 300, 30);
        }
        contenedor.add(etiqueta);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)  throws UnsupportedOperationException{
        if(e.getSource().equals(modificar)){
            id_producto = Integer.parseInt(mproducto.getText());
            try { 
                conexion.conectar();
                rs = conexion.consulta("call existe_producto(" + id_producto + ", " + id_categoria + ");");
                if(rs.next()){
                    if(rs.getInt("mensaje") == 0){
                        JOptionPane.showMessageDialog(this, "El numero de ID ingresado no existe en la base de datos o no corresponde a la categoría en uso.");
                    }
                    else{
                        mproducto.setText("");
                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                              editar_producto eproducto = new editar_producto(id_producto);
                            }
                        });
                    }
                }
                rs.close();
                conexion.cerrar();
            } catch (SQLException ex) {
                Logger.getLogger(tabla_productos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
