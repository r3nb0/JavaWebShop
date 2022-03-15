/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import entities.Product;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author r3nb0
 */
public class CartItem implements Serializable {
        private int id;
        private int amount;
        private Product product;

    public CartItem(int id, int amount, Product product) {
        this.id = id;
        this.amount = amount;
        this.product = product;
    }

    public CartItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName(){
        return this.product.getName();
    }
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return product.getPrice();
    }

    public void setPrice(BigDecimal price) {
        this.product.setPrice(price);
    }
        
}
