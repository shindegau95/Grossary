package com.pkg.android.grossary.model;

import android.content.SharedPreferences;

import com.pkg.android.grossary.Adapter.RetailListParent;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GAURAV on 10-02-2017.
 */

public class CartItem {
    private Product cartItem;
    private int cartquantity;

    public CartItem(Product p) {
        cartItem = p;
        cartquantity = 0;
    }

    public CartItem(Product p, int cartquantity) {
        cartItem = p;
        this.cartquantity = cartquantity;
    }

    public void setCartItem(Product cartItem) {
        this.cartItem = cartItem;
    }

    public Product getCartItem() {
        return cartItem;
    }

    public int getCartquantity() {
        return cartquantity;
    }

    public void setCartquantity(int cartquantity) {
        this.cartquantity = cartquantity;
    }

    public int incrementqty() {
        cartquantity++;
        return cartquantity;
    }

    public int decrementqty() {
        if(cartquantity > 0)
            cartquantity--;
        return cartquantity;
    }

}
