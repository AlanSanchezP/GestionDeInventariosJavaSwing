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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Familia
 */
public class compra_cliente extends JDialog implements ActionListener{
    private final Container contenedor;
    private ResultSet rs;
    private crear_conexion conexion;
    private DefaultTableModel modelo;
    private JTable tabla;
    private JLabel etiqueta;
    private int id_cliente;
    
    public compra_cliente(int id_clien){
        id_cliente = id_clien;
        setModal(true);
        contenedor=getContentPane();
        contenedor.setLayout(null);
        setTitle("Compras del usuario");
        setSize(600, 335);
        setLocationRelativeTo(null);
        setResizable(false);
        conexion = new crear_conexion();
        
        boolean compras = true;
        
        etiqueta = new JLabel("Este cliente no ha realizado ninguna compra.");
        etiqueta.setBounds(160, 135, 300, 30);
        
        
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo){
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }};
        
        modelo.addColumn("ID de compra");
        modelo.addColumn("ID de producto");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Fecha");
        modelo.addColumn("Subtotal");
        
        int total_gasto = 0;
        
        try {
            conexion.conectar();
            rs = conexion.consulta("call existen_compras(" + id_cliente + ");");
            if(rs.next()){
                if(rs.getInt("mensaje") == 0){
                    compras = false;
                }
                else{
                    rs = conexion.consulta("call obtener_compras(" + id_cliente + ");");
                    while(rs.next()){
                        int total = rs.getInt("costo_venta") * rs.getInt("cantidad");
                        Object [] fila = new Object[7];
                        for(int i = 0; i < 6; i ++){
                            fila[i] = rs.getObject(i+1);
                        }
                        total_gasto += total;
                        fila[6] = total;
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
        if(compras == true){
            contenedor.add(scroll);
            etiqueta.setText("<html><strong>Total de gastos: </strong></html>" + total_gasto);
            etiqueta.setBounds(400,305,200,30);
        }
        contenedor.add(etiqueta);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)  throws UnsupportedOperationException{
        
    }
    
}
