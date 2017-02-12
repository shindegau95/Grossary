package com.pkg.android.grossary.Labs;

import android.content.Context;
import android.util.Log;

import com.pkg.android.grossary.CSVReader;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by GAURAV on 11-02-2017.
 */

public class ShoppingListLab {

    private static String TAG= "ShoppingList";
    private static ShoppingListLab shoppingListLab;

    private List<CartItem> itemList;

    public List<CartItem> getCartItemList() {
        return itemList;
    }

    public ShoppingListLab(Context context) {
        List<Product> productList;
        itemList = new ArrayList<>();

        productList = CSVReader.readCSV(context,1);
        for (int j = 0; j < productList.size(); j++) {
            Product p = productList.get(j);
            CartItem ci = new CartItem(p);
            itemList.add(ci);
        }

    }

    public static ShoppingListLab get(Context context,int category) {
        if(shoppingListLab == null){
            shoppingListLab = new ShoppingListLab(context);
        }
        return shoppingListLab;
    }
}
