/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Modelo.Producto;
import Modelo.RepositorioProducto;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import vista.Principal;
import java.util.List;

/**
 *
 * @author edwin
 */
public class ControladorProducto implements ActionListener {

    RepositorioProducto repoProducto;
    Principal vista;
    DefaultTableModel defaultTableModel;

    private int idproductos;
    private String nombreproductos;
    private int cantidadproductos;
    private float precioproductos;

    public ControladorProducto() {
        super();
    }

    public ControladorProducto(RepositorioProducto repoProducto, Principal vista) {
        super();
        this.repoProducto = repoProducto;
        this.vista = vista;
    }

    private void agregarEventos() {
        vista.getBtnAgregar().addActionListener(this);
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnEliminar().addActionListener(this);
        vista.getBtnInforme().addActionListener(this);
        vista.getTblTablaProductos().addMouseListener(new MouseAdapter() {
        public void mouseCliked(MouseEvent e){
            
        }
        });
    }
    
    public void Listarsql(){
        String[] titulos= new String[]{"id","nombre","cantidad","precio"};
        defaultTableModel=new DefaultTableModel(titulos,0);
        List<Producto> listaProductos=(List<Producto>)repoProducto.findAll();
        for (Producto producto : listaProductos) {
            defaultTableModel.addRow(new Object[]{producto.getIdproductos(),producto.getNombreproductos(),producto.getCantidadproductos(),
            producto.getPrecioproductos()});
        }
        vista.getTblTablaProductos().setModel(defaultTableModel);
        vista.getTblTablaProductos().setPreferredSize(new Dimension(350,defaultTableModel.getRowCount()*16));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
