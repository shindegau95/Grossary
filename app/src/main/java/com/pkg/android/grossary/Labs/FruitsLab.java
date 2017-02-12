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

public class FruitsLab {

    private static FruitsLab sFruitsLab;

    private List<CartItem> itemList;

    public List<CartItem> getCartItemList() {
        return itemList;
    }

    public FruitsLab(Context context) {
        List<Product> productList;
        itemList = new ArrayList<>();

        productList = CSVReader.readCSV(context,3);
        for (int j = 0; j < productList.size(); j++) {
            Product p = productList.get(j);
            CartItem ci = new CartItem(p);
            itemList.add(ci);
        }

    }

    public static FruitsLab get(Context context) {
        if(sFruitsLab == null){
            sFruitsLab = new FruitsLab(context);
        }
        return sFruitsLab;
    }
}
