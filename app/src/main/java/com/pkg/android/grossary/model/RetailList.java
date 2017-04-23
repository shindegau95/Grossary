package com.pkg.android.grossary.model;

/**
 * Created by GAURAV on 06-03-2017.
 */

public class RetailList {
    private int quantity;
    private int price_per_unit;
    private int totalprice;

    public void updateTotalPrice(){
        setTotalprice(quantity*price_per_unit);
    }

    public RetailList(int quantity, int price_per_unit) {
        this.quantity = quantity;
        this.price_per_unit = price_per_unit;
        updateTotalPrice();
    }

    public int getPrice_per_unit() {
        return price_per_unit;
    }

    public void setPrice_per_unit(int price_per_unit) {
        this.price_per_unit = price_per_unit;

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTotalPrice();
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public int incrementqty() {
        quantity++;
        updateTotalPrice();
        return quantity;
    }

    public int decrementqty() {
        if(quantity > 0)
            quantity--;
        updateTotalPrice();
        return quantity;
    }
}
