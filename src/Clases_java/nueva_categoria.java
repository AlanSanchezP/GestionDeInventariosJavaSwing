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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
/**
 *
 * @author Familia
 */
public final class nueva_categoria extends JDialog implements ActionListener{
    private final Container contenedor;
    private JComboBox categorias;
    private JButton agregar, desplegar, borrar;
    private JTextField nombre;
    private JLabel datos, cat, nomb, ruta;
    
    private ResultSet rs;
    private crear_conexion conexion;
    
    public nueva_categoria(){
        
        rs = null;
        conexion = new crear_conexion();
        setModal(true);
        contenedor=getContentPane();
        contenedor.setLayout(null);
        setTitle("Nueva categoría");
        setSize(420, 248);
        setLocationRelativeTo(null);
        setResizable(false);
        
        
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
        categorias.setBounds(30, 110, 200, 30);
        colocar_categorias(0, "Categoría desde la raíz");
        
        desplegar = new JButton("Ver subcategorías");
        desplegar.setBounds(240, 110, 150, 30);
        desplegar.addActionListener(this);
        
        nomb = new JLabel("Nombre de la categoría");
        nomb.setBounds(30, 140, 200, 30);
        
        nombre = new JTextField();
        nombre.setBounds(30,170,200,30);
        
        agregar = new JButton("Agregar categoría");
        agregar.setBounds(240, 170, 150, 30);
        agregar.addActionListener(this);
        
        contenedor.add(borrar);
        contenedor.add(ruta);
        contenedor.add(datos);
        contenedor.add(categorias);
        contenedor.add(cat);
        contenedor.add(desplegar);
        contenedor.add(agregar);
        contenedor.add(nombre);
        contenedor.add(nomb);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) throws UnsupportedOperationException{
        if(e.getSource().equals(agregar)) {
            String seleccionado = String.valueOf(categorias.getSelectedItem());
            String vnombre = nombre.getText();
            String mensaje = "";
            int id_cate = 0;
            try {
                conexion.conectar();
                rs = conexion.consulta("call obtener_id_categoria('" + seleccionado + "');");
                if(rs.next()){
                    id_cate = rs.getInt("id_categoria");
                }
                rs = conexion.consulta("call nueva_categoria(" + id_cate + ", '" + vnombre + "') ;");
                if(rs.next()){
                    mensaje = rs.getString("mensaje");
                    JOptionPane.showMessageDialog(this, mensaje);
                }
                conexion.cerrar();
            } catch (Exception ex) {
                Logger.getLogger(nueva_categoria.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();
        }
        if(e.getSource().equals(desplegar)) {
            String seleccionado = String.valueOf(categorias.getSelectedItem());
            int posicion = categorias.getSelectedIndex();
            int id_cate = 0;
            if(posicion != 0){
                try {

                    if(seleccionado.equals("Categoría desde la raíz")){
                        id_cate = 0;
                    }
                    else{
                        conexion.conectar();
                        rs = conexion.consulta("call obtener_id_categoria('" + seleccionado + "');");
                        if(rs.next()){
                            id_cate = rs.getInt("id_categoria");
                        }

                    }
                    rs = conexion.consulta("select count(*) from categorias where id_categoria_padre = " + id_cate + ";");
                    if(rs.next()){
                        if(rs.getInt("count(*)") == 0){
                            JOptionPane.showMessageDialog(this, "No hay subcategorías por mostrar.");
                        }
                        else{
                            colocar_categorias(id_cate, seleccionado);
                        }
                    }

                } catch (Exception ex) {
                    Logger.getLogger(nueva_categoria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Seleccione una SUBCATEGORÍA.");
            }
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
        categorias.addItem(nombre_buscar);
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
