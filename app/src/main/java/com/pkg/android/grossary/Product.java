package com.pkg.android.grossary;

import java.util.Date;
import java.util.UUID;

/**
 * Created by GAURAV on 31-01-2017.
 */

public class Product {
    private UUID product_id;
    private String product_name;
    private int price;
    private Date expiry_time;
    private int thumbnail;

    public Product(String product_name, int price, Date expiry_time, int thumbnail, UUID product_id) {
        this.product_name = product_name;
        this.price = price;
        this.expiry_time = expiry_time;
        this.thumbnail = thumbnail;
        this.product_id = product_id;
    }

    public Product(UUID product_id) {
        this.product_id = product_id;
    }

    public Product() {
        product_id = UUID.randomUUID();
        expiry_time = new Date();
    }

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getExpiry_time() {
        return expiry_time;
    }

    public void setExpiry_time(Date expiry_time) {
        this.expiry_time = expiry_time;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
