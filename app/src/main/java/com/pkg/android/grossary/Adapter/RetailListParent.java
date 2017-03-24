package com.pkg.android.grossary.Adapter;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.pkg.android.grossary.model.Product;

import java.util.List;

/**
 * Created by GAURAV on 06-03-2017.
 */
public class RetailListParent implements Parent<RetailList> {
    private Product cartItem;
    private int cartquantity;

    private List<Object> mChildrenList;

    public RetailListParent(Product cartItem, int cartquantity) {
        this.cartItem = cartItem;
        this.cartquantity = cartquantity;
    }

    public RetailListParent(Product p) {
        cartItem = p;
    }

    public Product getCartItem() {
        return cartItem;
    }

    public void setCartItem(Product cartItem) {
        this.cartItem = cartItem;
    }

    public int getCartquantity() {
        return cartquantity;
    }

    public void setCartquantity(int cartquantity) {
        this.cartquantity = cartquantity;
    }



    @Override
    public List getChildList() {
        return mChildrenList;
    }

    public void setChildObjectList(List<Object> childrenList) {
        mChildrenList = childrenList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
