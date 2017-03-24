package com.pkg.android.grossary.Labs;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.pkg.android.grossary.Adapter.ShoppingListAdapter;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;
import com.pkg.android.grossary.other.CSVReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 20-03-2017.
 */
public class ShoppingListLab {
    private static ShoppingListLab sShoppingListLab;

    private List<CartItem> itemList;
    private List<Boolean> selectedList;

    public List<CartItem> getCartItemList() {
        return itemList;
    }

    public List<Boolean> getSelectedList() {
        return selectedList;
    }


    public ShoppingListLab(Context context) {
        List<Product> productList;
        itemList = new ArrayList<>();
        selectedList = new ArrayList<>();

        /*productList = CSVReader.readShoppingList(context);
        for (int j = 0; j < productList.size(); j++) {
            Product p = productList.get(j);
            CartItem ci = new CartItem(p);
            itemList.add(ci);
        }*/

        productList = CSVReader.getAllProducts(context);
        GrossaryApplication grossaryApplication = (GrossaryApplication)context.getApplicationContext();

        int qty;
        for (int j = 0; j < productList.size(); j++) {
            Product p = productList.get(j);
            CartItem ci = new CartItem(p);
            qty = grossaryApplication.getShoppingListQuantities().get(j);
            ci.setCartquantity(qty);
            if(qty>0)
                itemList.add(ci);

            //for select list
            selectedList.add(false);
        }



    }

    public static ShoppingListLab get(Context context) {
        if(sShoppingListLab == null) {
            sShoppingListLab = new ShoppingListLab(context);
        }
        return sShoppingListLab;
    }

    public void DisableItem(int id){
        for (int j = 0; j < getCartItemList().size(); j++) {
            if(getCartItemList().get(j).getCartItem().getProduct_id() == id) {//check the id

                selectedList.set(j,false);
                GrossaryApplication grossaryApplication = GrossaryApplication.getInstance();
                int qty = grossaryApplication.getShoppingListQuantities().get(id-1); //remember that list starts from 0 and id from 1
                getCartItemList().get(j).setCartquantity(qty);
                break;
            }
        }

    }
    public void EnableItemAtPosition(int position){
        selectedList.set(position,true);
    }

    public void DisableItemAtPosition(int position){
        selectedList.set(position,false);
    }


}
