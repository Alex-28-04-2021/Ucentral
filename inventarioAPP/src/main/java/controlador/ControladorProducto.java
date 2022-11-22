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

    private int codigo;
    private String nombre;
    private int inventario;
    private double precio;
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
            defaultTableModel.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getInventario(),
                producto.getPrecio()});
        }
        vista.getTblTablaProductos().setModel(defaultTableModel);
        vista.getTblTablaProductos().setPreferredSize(new Dimension(350, defaultTableModel.getRowCount() * 16));
    }

    public void llenarsql(MouseEvent e) {
        JTable target = (JTable) e.getSource();
        vista.getTxtCodigo().setText(vista.getTblTablaProductos().getModel().getValueAt(target.getSelectedRow(), 0).toString());
        vista.getTxtNombre().setText(vista.getTblTablaProductos().getModel().getValueAt(target.getSelectedRow(), 1).toString());
        vista.getTxtInventario().setText(vista.getTblTablaProductos().getModel().getValueAt(target.getSelectedRow(), 2).toString());
        vista.getTxtPrecio().setText(vista.getTblTablaProductos().getModel().getValueAt(target.getSelectedRow(), 3).toString());

    }

    private boolean validarDatos() {
        if ("".equals(vista.getTxtCodigo().getText()) || "".equals(vista.getTxtNombre().getText())|| "".equals(vista.getTxtInventario().getText()) || "".equals(vista.getTxtPrecio().getText())) {
            JOptionPane.showMessageDialog(null, "Algun Campo esta vacio", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    private boolean CargarDatos() {
        try {
            codigo = Integer.parseInt("".equals(vista.getTxtCodigo().getText())? "0" : vista.getTxtCodigo().getText());
            nombre = vista.getTxtNombre().getText();
            inventario = Integer.parseInt(vista.getTxtInventario().getText());
            precio = Double.parseDouble(vista.getTxtPrecio().getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void limpiarCampos() {
        vista.getTxtCodigo().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtInventario().setText("");
        vista.getTxtPrecio().setText("");
    }

    private void AgregarProducto() {
        try {
            if (CargarDatos()) {
                Producto producto = new Producto(nombre, inventario, precio);
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
                Producto producto = new Producto(codigo, nombre, inventario, precio);
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
                Producto producto = new Producto(codigo, nombre, inventario, precio);
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
        double precioAux = 0;
        List<Producto> listaProductos = (List<Producto>) repoProducto.findAll();
        for (Producto producto : listaProductos) {
            if (producto.getPrecio() > precioAux) {
                nombre = producto.getNombre();
                precioAux = producto.getPrecio();
            }
        }
        return nombre;
    }

    private String precioMenor() {
        String nombre = "";
        double precioAux = 1000000;
        List<Producto> listaProductos = (List<Producto>) repoProducto.findAll();
        for (Producto producto : listaProductos) {
            if (producto.getPrecio() < precioAux) {
                nombre = producto.getNombre();
                precioAux = producto.getPrecio();
            }
        }
        return nombre;
    }

    private String PrecioPromedio() {
        double suma = 0;
        double resultado = 0;
        List<Producto> listaProductos = (List<Producto>) repoProducto.findAll();
        for (Producto producto : listaProductos) {
            suma += producto.getPrecio();
        }
        resultado = suma / listaProductos.size();
        return String.format("%.2f", resultado);
    }

    private String PrecioTotal() {
        double suma = 0;
        double resultado = 0;
        List<Producto> listaProductos = (List<Producto>) repoProducto.findAll();
        for (Producto producto : listaProductos) {
            suma = producto.getPrecio() * producto.getInventario();
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
