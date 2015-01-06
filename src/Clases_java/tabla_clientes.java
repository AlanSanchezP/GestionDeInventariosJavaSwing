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
public class tabla_clientes extends JDialog implements ActionListener{
    private final Container contenedor;
    private ResultSet rs;
    private crear_conexion conexion;
    private DefaultTableModel modelo;
    private JTable tabla;
    private JLabel ecliente;
    private JTextField bcliente;
    private JButton vcompras;
    private int id_cliente;
    
    public tabla_clientes(){
        
        setModal(true);
        contenedor=getContentPane();
        contenedor.setLayout(null);
        setTitle("Tabla de clientes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        conexion = new crear_conexion();
        
        id_cliente = 0;
        
        boolean existen = true;
        
        ecliente = new JLabel("<html>Ingresa el ID de algun <br> cliente para ver sus compras</html>");
        ecliente.setBounds(35, 310, 200, 50);
        
        bcliente = new JTextField();
        bcliente.setBounds(235, 320, 150, 30);
        
        vcompras = new JButton("Ver las compras");
        vcompras.setBounds(395, 320, 160, 30);
        vcompras.addActionListener(this);
        
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo){
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }};
        
        modelo.addColumn("ID de cliente");
        modelo.addColumn("Nombre(s)");
        modelo.addColumn("A. paterno");
        modelo.addColumn("A. materno");
        modelo.addColumn("Email");
        
        
        try {
            conexion.conectar();
            rs = conexion.consulta("call obtener_clientes();");
            while(rs.next()){
                Object [] fila = new Object[5];
                for(int i = 0; i < 5; i ++){
                    fila[i] = rs.getObject(i+1);
                }
                modelo.addRow(fila);
            }
            rs.close();
            conexion.cerrar();
        } catch (Exception ex) {
            Logger.getLogger(tabla_clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        tabla.setBounds(0,0,560,290);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(15,15,560,285);
        if(existen == true){
            contenedor.add(scroll);
            contenedor.add(bcliente);
            contenedor.add(vcompras);
        }
        else{
            ecliente.setText("No hay ningun cliente registrado.");
            ecliente.setBounds(190, 150, 300, 30);
        }
        contenedor.add(ecliente);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)  throws UnsupportedOperationException{
        if(e.getSource().equals(vcompras)){
            id_cliente = Integer.parseInt(bcliente.getText());
            try { 
                conexion.conectar();
                rs = conexion.consulta("call existe_cliente(" + id_cliente + ");");
                if(rs.next()){
                    if(rs.getInt("mensaje") == 0){
                        JOptionPane.showMessageDialog(this, "El numero de ID ingresado no existe en la base de datos.");
                    }
                    else{
                        bcliente.setText("");
                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                              compra_cliente vcompras = new compra_cliente(id_cliente);
                            }
                        });
                    }
                }
                rs.close();
                conexion.cerrar();
            } catch (SQLException ex) {
                Logger.getLogger(tabla_clientes.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }
    }
    
}
