package com.pkg.android.grossary.Labs;

import android.content.Context;

import com.pkg.android.grossary.Adapter.RetailListParent;
import com.pkg.android.grossary.other.CSVReader;
import com.pkg.android.grossary.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 08-03-2017.
 */

public class RetailLab {
    private static RetailLab sRetailLab;

    private List<RetailListParent> itemList;

    public List<RetailListParent> getCartItemList() {
        return itemList;
    }

    public RetailLab(Context context) {
        List<Product> productList;
        itemList = new ArrayList<>();

        productList = CSVReader.getAllProducts(context);
        for (int j = 0; j < productList.size(); j++) {
            Product p = productList.get(j);
            RetailListParent ci = new RetailListParent(p);
            itemList.add(ci);
        }

        CSVReader.setProductQuantities(context, (ArrayList<RetailListParent>) itemList);
    }

    public static RetailLab get(Context context) {
        if (sRetailLab == null) {
            sRetailLab = new RetailLab(context);
        }
        return sRetailLab;
    }
}