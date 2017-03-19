package com.pkg.android.grossary;

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

import com.pkg.android.grossary.Adapter.CartItemAdapter;
import com.pkg.android.grossary.ConnectionPackage.ASyncResponse;
import com.pkg.android.grossary.ConnectionPackage.Connection;
import com.pkg.android.grossary.model.CartItem;

import org.json.JSONException;
import org.json.JSONObject;

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
    private Toolbar toolbar;
    private int total;
    private TextView totalprice;
    private AppCompatButton checkout;
    private JSONObject j;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        totalprice = (TextView) findViewById(R.id.cart_total_price);
        checkout = (AppCompatButton)findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart shoppingCart = (Cart)getApplicationContext();
                shoppingCart.setCartItemItems(null);
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Call to the server starts from here. Start copying the code from here
                Context context=getApplicationContext();
                ASyncResponse a=new ASyncResponse() {
                    @Override
                    public void processFinish(String output) {   // Server will return the output & store it in the string variable 'output'
                        Log.e("param", output);
                        Toast.makeText(ViewCartActivity.this, output, Toast.LENGTH_LONG).show();
                    }
                };
                Connection c=new Connection("https://104.198.113.225/index1.php",context);  //For personalized recommendation,the php file is index.php
                // Make sure you change the url accordingly
                String q=j.toString();
                c.onPost(q, a);
                // 'Connection to the server' code ends here
                startActivity(i);
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
        toolbar.setTitle("Cart");
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
        Cart shoppingCart = (Cart)getApplicationContext();

        cart = shoppingCart.getCart();
        total = 0;
        j=new JSONObject();
        for(CartItem c : cart){
            total += c.getCartquantity()*c.getCartItem().getPrice();
            try {
                j.put(String.valueOf(c.getCartItem().getProduct_id()),c.getCartquantity());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.e(TAG, "String="+j.toString());
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
