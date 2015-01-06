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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
/**
 *
 * @author Familia
 */
public final class tabla_categorias extends JDialog implements ActionListener{
    private final Container contenedor;
    private JComboBox categorias;
    private JButton agregar, ver, desplegar, borrar;
    private JLabel datos, cat, ruta;
    private int id_cate;
    private ResultSet rs;
    private crear_conexion conexion;
    
    public tabla_categorias(){
        
        rs = null;
        conexion = new crear_conexion();
        setModal(true);
        contenedor=getContentPane();
        contenedor.setLayout(null);
        setTitle("Ver categorías");
        setSize(420, 230);
        setLocationRelativeTo(null);
        setResizable(false);
        id_cate = 0;
        
        datos = new JLabel("<html><strong>Ruta de la categoría: </strong></html>");
        datos.setBounds(30, 5, 150, 30);
        
        ruta = new JLabel("Raíz ");
        ruta.setBounds(30, 40, 200, 30);
        
        borrar = new JButton("Regresar a la raíz");
        borrar.setBounds(240, 40, 150, 30);
        borrar.addActionListener(this);
        
        cat = new JLabel("Seleccionar categoría madre.");
        cat.setBounds(30, 80, 360, 30);
        
        categorias = new JComboBox();
        categorias.setBounds(30, 110, 360, 30);
        colocar_categorias(0,"");
        
        desplegar = new JButton("Ver subcategorías");
        desplegar.setBounds(30, 155, 110, 30);
        desplegar.addActionListener(this);
        
        ver = new JButton("Ver productos");
        ver.setBounds(155, 155, 110, 30);
        ver.addActionListener(this);
        
        agregar = new JButton("Agregar producto");
        agregar.setBounds(280, 155, 110, 30);
        agregar.addActionListener(this);
        
        contenedor.add(borrar);
        contenedor.add(ruta);
        contenedor.add(datos);
        contenedor.add(categorias);
        contenedor.add(cat);
        contenedor.add(ver);
        contenedor.add(desplegar);
        contenedor.add(agregar);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) throws UnsupportedOperationException{
        if(e.getSource().equals(agregar)) {
            String seleccionado = String.valueOf(categorias.getSelectedItem());
            
            try {
                conexion.conectar();
                rs = conexion.consulta("call obtener_id_categoria('" + seleccionado + "');");
                if(rs.next()){
                    id_cate = rs.getInt("id_categoria");
                }
                conexion.cerrar();
            } catch (Exception ex) {
                Logger.getLogger(nueva_categoria.class.getName()).log(Level.SEVERE, null, ex);
            }
            EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                nuevo_producto nproducto = new nuevo_producto(id_cate);
            }
        });
        }
        if(e.getSource().equals(desplegar)) {
            String seleccionado = String.valueOf(categorias.getSelectedItem());
            int id_categ = 0;
                try {
                    conexion.conectar();
                    rs = conexion.consulta("call obtener_id_categoria('" + seleccionado + "');");
                    if(rs.next()){
                        id_categ = rs.getInt("id_categoria");
                    }

                    rs = conexion.consulta("select count(*) from categorias where id_categoria_padre = " + id_categ + ";");
                    if(rs.next()){
                        if(rs.getInt("count(*)") == 0){
                            JOptionPane.showMessageDialog(this, "No hay subcategorías por mostrar.");
                        }
                        else{
                            colocar_categorias(id_categ, seleccionado);
                        }
                    }

                } catch (Exception ex) {
                    Logger.getLogger(nueva_categoria.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        }
        if(e.getSource().equals(ver)) {
            String seleccionado = String.valueOf(categorias.getSelectedItem());
            try {
                conexion.conectar();
                rs = conexion.consulta("call obtener_id_categoria('" + seleccionado + "');");
                if(rs.next()){
                    id_cate = rs.getInt("id_categoria");
                }
                conexion.cerrar();
            } catch (Exception ex) {
                Logger.getLogger(nueva_categoria.class.getName()).log(Level.SEVERE, null, ex);
            }
            EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
              tabla_productos vproductos = new tabla_productos(id_cate);
            }
            });
        }
        if(e.getSource().equals(borrar)) {
            colocar_categorias(0, "Categoría desde la raíz");
            ruta.setText("Raíz ");
        }
    }
    
    public void colocar_categorias(int idbuscar, String nombrebuscar){
        int id_buscar = idbuscar;
        String nombre_buscar = nombrebuscar;
        categorias.removeAllItems();
        if(id_buscar != 0){
            ruta.setText(ruta.getText() + "/" + nombre_buscar + " ");
        }
        try {
            conexion.conectar();
            rs = conexion.consulta("call obtener_categorias(" + id_buscar + ");");
            while(rs.next()){
                categorias.addItem(rs.getString("nombre"));
            }
            rs.close();
            conexion.cerrar();
        } catch (Exception ex) {
            Logger.getLogger(nueva_categoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
