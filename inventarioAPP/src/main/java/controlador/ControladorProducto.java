/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Modelo.Producto;
import Repositorio.RepositorioProducto;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import vista.Principal;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;

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
        vista.setVisible(true);
        agregarEventos();
        Listarsql();
    }

    private void agregarEventos() {
        vista.getBtnAgregar().addActionListener(this);
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnEliminar().addActionListener(this);
        vista.getBtnInforme().addActionListener(this);
        vista.getTblTablaProductos().addMouseListener(new MouseAdapter() {
            public void mouseCliked(MouseEvent e) {
                llenarsql(e);
            }
        });
    }

    public void Listarsql() {
        String[] titulos = new String[]{"id", "nombre", "cantidad", "precio"};
        defaultTableModel = new DefaultTableModel(titulos, 0);
        List<Producto> listaProductos = (List<Producto>) repoProducto.findAll();
        for (Producto producto : listaProductos) {
            defaultTableModel.addRow(new Object[]{producto.getIdproductos(), producto.getNombreproductos(), producto.getCantidadproductos(),
                producto.getPrecioproductos()});
        }
        vista.getTblTablaProductos().setModel(defaultTableModel);
        vista.getTblTablaProductos().setPreferredSize(new Dimension(350, defaultTableModel.getRowCount() * 16));
    }

    public void llenarsql(MouseEvent e) {
        JTable target = (JTable) e.getSource();
        vista.getTxtIdProducto().setText(vista.getTblTablaProductos().getModel().getValueAt(target.getSelectedRow(), 0).toString());
        vista.getTxtNombreProducto().setText(vista.getTblTablaProductos().getModel().getValueAt(target.getSelectedRow(), 1).toString());
        vista.getTxtCantidadProducto().setText(vista.getTblTablaProductos().getModel().getValueAt(target.getSelectedRow(), 2).toString());
        vista.getTxtPrecioProducto().setText(vista.getTblTablaProductos().getModel().getValueAt(target.getSelectedRow(), 3).toString());

    }

    private boolean validarDatos() {
        if ("".equals(vista.getTxtIdProducto().getText()) || "".equals(vista.getTxtNombreProducto().getText())
                || "".equals(vista.getTxtCantidadProducto().getText()) || "".equals(vista.getTxtPrecioProducto().getText())) {
            JOptionPane.showMessageDialog(null, "Algun Campo esta vacio", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    private boolean CargarDatos() {
        try {
            idproductos = Integer.parseInt(vista.getTxtIdProducto().getText());
            nombreproductos = vista.getTxtNombreProducto().getText();
            cantidadproductos = Integer.parseInt(vista.getTxtCantidadProducto().getText());
            precioproductos = Float.parseFloat(vista.getTxtPrecioProducto().getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void limpiarCampos() {
        vista.getTxtIdProducto().setText("");
        vista.getTxtNombreProducto().setText("");
        vista.getTxtCantidadProducto().setText("");
        vista.getTxtPrecioProducto().setText("");
    }

    private void AgregarProducto() {
        try {
            if (CargarDatos()) {
                Producto producto = new Producto(idproductos, nombreproductos, cantidadproductos, precioproductos);
                repoProducto.save(producto);
                JOptionPane.showMessageDialog(null, "Producto se Agrego");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "el codigo, cantidad y precio deben ser numericos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DbActionExecutionException e) {
            JOptionPane.showMessageDialog(null, "Producto ya Existe Actualiza los datos que sean necesarios");
        } finally {
            Listarsql();
        }
    }

    private void ActualizarProducto() {
        try {
            if (CargarDatos()) {
                Producto producto = new Producto(idproductos, nombreproductos, cantidadproductos, precioproductos);
                repoProducto.save(producto);
                JOptionPane.showMessageDialog(null, "Producto se Actualizado");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "el codigo, cantidad y precio deben ser numericos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DbActionExecutionException e) {
            JOptionPane.showMessageDialog(null, "Error El nombre de producto ya existe");
        } finally {
            Listarsql();
        }
    }

    private void EliminarProducto() {
        try {
            if (CargarDatos()) {
                Producto producto = new Producto(idproductos, nombreproductos, cantidadproductos, precioproductos);
                repoProducto.delete(producto);
                JOptionPane.showMessageDialog(null, "Producto se Elimino");
                limpiarCampos();
            }
        } catch (DbActionExecutionException e) {
            JOptionPane.showMessageDialog(null, "Error Producto no Eliminado");
        } finally {
            Listarsql();
        }
    }

    private void generarInforme() {
        String nombreMayor = precioMayor();
        String nombreMenor = precioMenor();
        String Promedio = PrecioPromedio();
        String total = PrecioTotal();
        JOptionPane.showMessageDialog(null, nombreMayor + " " + nombreMenor + " " + Promedio + " " + total + " ");

    }

    private String precioMayor() {
        String nombre = "";
        float precioAux = 0;
        List<Producto> listaProductos = (List<Producto>) repoProducto.findAll();
        for (Producto producto : listaProductos) {
            if (producto.getPrecioproductos() > precioAux) {
                nombre = producto.getNombreproductos();
                precioAux = producto.getPrecioproductos();
            }
        }
        return nombre;
    }

    private String precioMenor() {
        String nombre = "";
        float precioAux = 1000000;
        List<Producto> listaProductos = (List<Producto>) repoProducto.findAll();
        for (Producto producto : listaProductos) {
            if (producto.getPrecioproductos() < precioAux) {
                nombre = producto.getNombreproductos();
                precioAux = producto.getPrecioproductos();
            }
        }
        return nombre;
    }

    private String PrecioPromedio() {
        double suma = 0;
        double resultado = 0;
        List<Producto> listaProductos = (List<Producto>) repoProducto.findAll();
        for (Producto producto : listaProductos) {
            suma += producto.getPrecioproductos();
        }
        resultado = suma / listaProductos.size();
        return String.format("%.2f", resultado);
    }

    private String PrecioTotal() {
        double suma = 0;
        double resultado = 0;
        List<Producto> listaProductos = (List<Producto>) repoProducto.findAll();
        for (Producto producto : listaProductos) {
            suma = producto.getPrecioproductos() * producto.getCantidadproductos();
            resultado += suma;
        }
        return String.format("%.2f", resultado);
    }

    @Override
    public void actionPerformed(ActionEvent ac) {
        if (ac.getSource() == vista.getBtnAgregar()) {
            AgregarProducto();
        }
        if (ac.getSource() == vista.getBtnActualizar()) {
            ActualizarProducto();
        }
        if (ac.getSource() == vista.getBtnEliminar()) {
            EliminarProducto();
        }
        if (ac.getSource() == vista.getBtnInforme()) {
            generarInforme();
        }

    }

}
