package com.pkg.android.grossary.model;

/**
 * Created by GAURAV on 10-02-2017.
 */

public class CartItem {
    private Product product;
    private int cartquantity;

    public CartItem(Product p) {
        product = p;
        cartquantity = 0;
    }

    public CartItem(Product p, int cartquantity) {
        product = p;
        this.cartquantity = cartquantity;
    }

    public void setProduct(Product cartItem) {
        this.product = cartItem;
    }

    public Product getProduct() {
        return product;
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

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", cartquantity=" + cartquantity +
                '}';
    }
}
