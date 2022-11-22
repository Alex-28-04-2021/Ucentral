/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 *
 * @author edwin
 */
@Table("productos")
        
public class Producto {
    @Id
    
    @Column("codigo")
    private int codigo;
    @Column("nombre")    
    private String nombre;
    @Column("inventario")
    private int inventario;
    @Column("precio")
    private double precio;

    public Producto() {
    }

    public Producto(String nombre, int inventario, double precio) {
        this.nombre = nombre;
        this.inventario = inventario;
        this.precio = precio;
    }
    

    public Producto(int codigo, String nombre, int inventario, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.inventario = inventario;
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }


    
    
}
