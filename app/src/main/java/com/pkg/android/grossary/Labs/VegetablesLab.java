package com.pkg.android.grossary.Labs;

import android.content.Context;

import com.pkg.android.grossary.CSVReader;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 11-02-2017.
 */

public class VegetablesLab {
    private static VegetablesLab sVegetablesLab;

    private List<CartItem> itemList;

    public List<CartItem> getCartItemList() {
        return itemList;
    }

    public VegetablesLab(Context context) {
        List<Product> productList;
        itemList = new ArrayList<>();

        productList = CSVReader.readCSV(context,4);
        for (int j = 0; j < productList.size(); j++) {
            Product p = productList.get(j);
            CartItem ci = new CartItem(p);
            itemList.add(ci);
        }

    }

    public static VegetablesLab get(Context context) {
        if(sVegetablesLab == null){
            sVegetablesLab = new VegetablesLab(context);
        }
        return sVegetablesLab;
    }
}
