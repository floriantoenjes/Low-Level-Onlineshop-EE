package com.floriantoenjes.learning.model;

import java.util.Arrays;
import java.util.Date;

public class Item {
    private Long id;
    private String description;
    private byte[] foto;
    private Double price;
    private String title;
    private Date traded;
    private Long seller_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTraded() {
        return traded;
    }

    public void setTraded(Date traded) {
        this.traded = traded;
    }

    public Long getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(Long seller_id) {
        this.seller_id = seller_id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", foto=" + Arrays.toString(foto) +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", traded=" + traded +
                ", seller_id=" + seller_id +
                '}';
    }
}
