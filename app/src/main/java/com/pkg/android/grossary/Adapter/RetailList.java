package com.pkg.android.grossary.Adapter;

/**
 * Created by GAURAV on 06-03-2017.
 */

public class RetailList {
    private int quantity;
    private int totalprice;

    public RetailList(int quantity, int totalprice) {
        this.quantity = quantity;
        this.totalprice = totalprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public int incrementqty() {
        quantity++;
        return quantity;
    }

    public int decrementqty() {
        if(quantity > 0)
            quantity--;
        return quantity;
    }
}
