package com.pkg.android.grossary.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by GAURAV on 31-01-2017.
 */

public class Product {
    private int product_id;
    private String product_name;
    private int price;
    private Date expiry_time;
    private int thumbnail;
    private String product_unit;
    private int category_id;

    //constructors
    public Product(int product_id) {
        this.product_id = product_id;
    }

    public Product() {
        expiry_time = new Date();
    }

    //getters and setters
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
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

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    //toString
    @Override
    public String toString() {
        return product_name;
    }


}
