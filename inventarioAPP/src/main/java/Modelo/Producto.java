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
@Table("producto")
        
public class Producto {
    @Id
    @Column("idproductos")
    private int idproductos;
    @Column("nombreproductos")    
    private String nombreproductos;
    @Column("cantidadproductos")
    private int cantidadproductos;
    @Column("precioproductos")
    private float precioproductos;

    public Producto() {
    }
    

    public Producto(int idproductos, String nombreproductos, int cantidadproductos, float precioproductos) {
        this.idproductos = idproductos;
        this.nombreproductos = nombreproductos;
        this.cantidadproductos = cantidadproductos;
        this.precioproductos = precioproductos;
    }

    public int getIdproductos() {
        return idproductos;
    }

    public void setIdproductos(int idproductos) {
        this.idproductos = idproductos;
    }

    public String getNombreproductos() {
        return nombreproductos;
    }

    public void setNombreproductos(String nombreproductos) {
        this.nombreproductos = nombreproductos;
    }

    public int getCantidadproductos() {
        return cantidadproductos;
    }

    public void setCantidadproductos(int cantidadproductos) {
        this.cantidadproductos = cantidadproductos;
    }

    public float getPrecioproductos() {
        return precioproductos;
    }

    public void setPrecioproductos(float precioproductos) {
        this.precioproductos = precioproductos;
    }
    
    
    
    
}
