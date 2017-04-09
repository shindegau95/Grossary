package com.pkg.android.grossary.Applications;

import android.app.Application;

import com.pkg.android.grossary.Adapter.RetailListParent;
import com.pkg.android.grossary.ConnectionPackage.ConnectivityReceiver;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.other.Parser;
import com.pkg.android.grossary.other.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 10-02-2017.
 */

public class GrossaryApplication extends Application {

    private static GrossaryApplication mInstance;
    private static final String TAG = "GrossaryApplication";
    private List<CartItem> mCart;
    private List<Integer> ShoppingListQuantities;
    private ArrayList<Integer> recipeList;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized GrossaryApplication getInstance(){
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener){
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public List<CartItem> getCart()
    {
        if(mCart == null){
            mCart = new ArrayList<>();
        }
        return mCart;
    }

    public void setCart(List<CartItem> cart) {
        mCart = cart;
    }

    public void addToCart(CartItem item){
        mCart = getCart();
        if(!mCart.contains(item)){
            mCart.add(item);
        }
    }

    public void removeFromCart(CartItem item) {
        mCart = getCart();
        mCart.remove(item);
    }



    public List<Integer> getShoppingListQuantities() {
        return ShoppingListQuantities;
    }

    public void setShoppingListQuantities() {
        ShoppingListQuantities = Parser.parseShoppingList(Session.getShoppingListString(this));;
    }

    public List<Integer> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList() {
        recipeList = Parser.parseRecipeList(Session.getRecipeListString(this));;
    }
}