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

public class FruitsLab {

    private static FruitsLab sFruitsLab;

    private List<CartItem> itemList;

    public List<CartItem> getCartItemList() {
        return itemList;
    }

    public FruitsLab(Context context) {
        List<Product> productList;
        itemList = new ArrayList<>();

        productList = CSVReader.readProductList(context,3);
        for (int j = 0; j < productList.size(); j++) {
            Product p = productList.get(j);
            CartItem ci = new CartItem(p);
            itemList.add(ci);
        }

    }

    public static FruitsLab get(Context context, boolean noItems) {
        if(sFruitsLab == null || noItems){
            sFruitsLab = new FruitsLab(context);
        }
        return sFruitsLab;
    }
    public void setRecommendedQuantity(int id, int qty){
        for (int j = 0; j < getCartItemList().size(); j++) {
            if(getCartItemList().get(j).getCartItem().getProduct_id() == id) {//check the id
                GrossaryApplication ShoppingCart = GrossaryApplication.getInstance();
                getCartItemList().get(j).setCartquantity(qty);
                ShoppingCart.addToCart(getCartItemList().get(j));
                break;
            }
        }

    }
}
