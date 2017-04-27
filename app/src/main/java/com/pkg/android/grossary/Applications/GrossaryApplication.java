package com.pkg.android.grossary.Applications;

import android.app.Application;

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
    private ArrayList<Integer> currstocklist;
    private ArrayList<Integer> expstocklist;
    private boolean isLoading;

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

    public ArrayList<Integer> getCurrstocklist() {
        return currstocklist;
    }

    public void setCurrstocklist() {
        this.currstocklist = Parser.parseStockList(Session.getCurrStockListString(this));
    }

    public ArrayList<Integer> getExpstocklist() {
        return expstocklist;
    }

    public void setExpstocklist() {
        this.expstocklist = Parser.parseStockList(Session.getExpStockListString(this));
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }


}