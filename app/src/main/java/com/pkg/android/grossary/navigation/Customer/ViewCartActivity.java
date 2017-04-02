package com.pkg.android.grossary.navigation.Customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pkg.android.grossary.Adapter.CartItemAdapter;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.ConnectionPackage.ASyncResponse;
import com.pkg.android.grossary.ConnectionPackage.Connection;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.other.CallServer;
import com.pkg.android.grossary.other.DividerItemDecoration;
import com.pkg.android.grossary.other.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 10-02-2017.
 */

/*
    This is the final cart after adding items to cart
    displays all the products wwith their price and quantity
    displays total price
 */
public class ViewCartActivity extends AppCompatActivity {
    private static final String TAG = "GrossaryApplication";
    private List<CartItem> cart;
    private RecyclerView mRecyclerView;
    private CartItemAdapter mAdapter;
    private Toolbar toolbar;
    private int total;
    private TextView totalprice;
    private AppCompatButton checkout;
    private DatabaseReference dbref;
    private JSONObject jsonprodlist;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        //wiring
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        totalprice = (TextView) findViewById(R.id.cart_total_price);
        checkout = (AppCompatButton)findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallServer.updateShoppingList(ViewCartActivity.this);
                CallServer.checkout(ViewCartActivity.this, jsonprodlist);

            }
        });

        cart = new ArrayList<>();
        updateUI();

        if(total>0)
            totalprice.setText(String.valueOf(total));
        else
            setContentView(R.layout.activity_no_items_in_cart);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.green));
        getWindow().setStatusBarColor(getResources().getColor(R.color.dark_green));
        toolbar.setTitle("GrossaryApplication");
        setSupportActionBar(toolbar);
    }

    private void updateUI() {
        prepareCart();


        if(mAdapter == null)
        {
            mAdapter = new CartItemAdapter(cart);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }

    }



    private void prepareCart() {
        GrossaryApplication shoppingCart = (GrossaryApplication)getApplicationContext();

        cart = shoppingCart.getCart();
        total = 0;
        jsonprodlist=new JSONObject();
        for(CartItem c : cart){
            total += c.getCartquantity()*c.getProduct().getPrice();
            try {
                jsonprodlist.put(String.valueOf(c.getProduct().getProduct_id()),c.getCartquantity());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "String="+jsonprodlist.toString());
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
