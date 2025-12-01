package Models;


import Models.Product;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author apolo
 */
public class ProductItem extends Product {
    
    private String size;

    public ProductItem(String size, String nombre, String descripcion, float precio, int stock, String categoria, String pathImage) {
        
        super(nombre, descripcion, precio, stock, categoria, pathImage);
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    
    
    
    
}
