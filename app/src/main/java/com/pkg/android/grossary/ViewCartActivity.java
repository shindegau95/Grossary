package com.pkg.android.grossary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.pkg.android.grossary.Adapter.CartItemAdapter;
import com.pkg.android.grossary.Labs.CerealLab;
import com.pkg.android.grossary.Labs.ShoppingCartLab;
import com.pkg.android.grossary.Labs.VegetablesLab;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 10-02-2017.
 */

public class ViewCartActivity extends AppCompatActivity {
    private static final String TAG = "Cart";
    private List<CartItem> cart;
    private RecyclerView mRecyclerView;
    private CartItemAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        cart = new ArrayList<>();
        updateUI();
    }

    private void updateUI() {
        prepareCart();

        if(mAdapter == null)
        {
            mAdapter = new CartItemAdapter(cart);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }

    }



    private void prepareCart() {
        Cart shoppingCart = (Cart)getApplicationContext();

        cart = shoppingCart.getCart();
        /*for(CartItem c : shoppingCart.getCart()){
            cart.add(c);
        }

        for(CartItem c : cart){
            Log.d(TAG,String.valueOf(c.getCartItem().getProduct_name()));
        }*/


        /*Product p = new Product();
        p.setProduct_name("Chana");
        p.setPrice(128);
        CartItem c = new CartItem(p);
        c.setCartquantity(1);
        cart.add(c);*/
        //mAdapter.notifyDataSetChanged();

    }
}
