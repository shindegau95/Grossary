package com.pkg.android.grossary;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 10-02-2017.
 */

public class Cart extends Application {

    private static final String TAG = "Cart";
    private List<CartItem> mCart;

    public List<CartItem> getCart()
    {
        if(mCart == null){
            mCart = new ArrayList<>();
        }
        return mCart;
    }

    public void setCartItemItems(List<CartItem> cart) {
        mCart = cart;
    }

    public void addToCart(CartItem item){
        mCart = getCart();
        if(!mCart.contains(item)){
            mCart.add(item);
        }
    }

    public void removeFromCart(CartItem item) {
        mCart = getCart();
        if(item.getCartquantity()==0){
            mCart.remove(item);
        }
    }
}
