package com.example.backend.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private float price;
    private String name;
    private String description;
    private String imageUrl;
    private String category;


    public Item() {


    }
    public Item(Long id, float price, String name, String description, String imageUrl, String category) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", price=" + price + ", name=" + name + ", description=" + description
                + ", imageUrl=" + imageUrl + ", category=" + category + "]";
    }


}
