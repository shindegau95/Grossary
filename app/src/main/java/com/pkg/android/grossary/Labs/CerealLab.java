package com.pkg.android.grossary.Labs;

import android.content.Context;

import com.pkg.android.grossary.CSVReader;
import com.pkg.android.grossary.Cart;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 11-02-2017.
 */

public class CerealLab {
    private static CerealLab sCerealLab;

    private List<CartItem> itemList;

    public List<CartItem> getCartItemList() {
        return itemList;
    }

    public CerealLab(Context context) {
        List<Product> productList;
        itemList = new ArrayList<>();

        productList = CSVReader.readCSV(context,1);
        for (int j = 0; j < productList.size(); j++) {
            Product p = productList.get(j);
            CartItem ci = new CartItem(p);
            itemList.add(ci);
        }

    }

    public static CerealLab get(Context context, boolean noItems) {
        if(sCerealLab == null || noItems){
            sCerealLab = new CerealLab(context);
        }
        return sCerealLab;
    }
}
