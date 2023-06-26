package com.example.backend.model;

import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    @ManyToOne()
    private Item item;
    private int quantity;
    private double totalPrice;
    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = quantity * item.getPrice();
    }

    public CartItem() {

    }

    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
