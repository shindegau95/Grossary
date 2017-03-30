package com.pkg.android.grossary.Labs;

import android.content.Context;
import android.util.Log;

import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.other.CSVReader;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 11-02-2017.
 */

public class DairyLab {
    private static DairyLab sDairyLab;

    private List<CartItem> itemList;

    public List<CartItem> getCartItemList() {
        return itemList;
    }

    public DairyLab(Context context) {
        List<Product> productList;
        itemList = new ArrayList<>();

        productList = CSVReader.readProductList(context,2);
        for (int j = 0; j < productList.size(); j++) {
            Product p = productList.get(j);
            CartItem ci = new CartItem(p);
            itemList.add(ci);
        }

    }

    public static DairyLab get(Context context, boolean noItems) {
        if(sDairyLab == null || noItems){
            sDairyLab = new DairyLab(context);
        }
        return sDairyLab;
    }

    public void setRecommendedQuantity(int id, int qty){
        for (int j = 0; j < getCartItemList().size(); j++) {
            if(getCartItemList().get(j).getProduct().getProduct_id() == id) {//check the id
                GrossaryApplication ShoppingCart = GrossaryApplication.getInstance();
                getCartItemList().get(j).setCartquantity(qty);
                if(qty == 0){
                    ShoppingCart.removeFromCart(getCartItemList().get(j));
                }else{
                    ShoppingCart.addToCart(getCartItemList().get(j));
                }
                break;
            }
        }

    }

    public CartItem getCartItem(int id){
        for (int j = 0; j < getCartItemList().size(); j++) {
            if(getCartItemList().get(j).getProduct().getProduct_id() == id) {//check the id
                return getCartItemList().get(j);
            }
        }
        return null;
    }
}
