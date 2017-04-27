package com.pkg.android.grossary.navigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.pkg.android.grossary.Adapter.Customer.CartItemAdapter;
import com.pkg.android.grossary.Adapter.Customer.ShoppingListAdapter;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.ConnectionPackage.ConnectivityReceiver;
import com.pkg.android.grossary.Labs.RetailLab;
import com.pkg.android.grossary.Labs.ShoppingListLab;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.navigation.Customer.CategoryWiseProductListActivity;
import com.pkg.android.grossary.navigation.Customer.CustomerMainActivity;
import com.pkg.android.grossary.navigation.Retailer.RetailerMainActivity;
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
    private ProgressDialog progressDialog;

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
                if(checkConnection()) {

                    GrossaryApplication.getInstance().setCart(null);
                    new CheckoutAsyncTask().execute();

                }
            }
        });

        cart = new ArrayList<>();
        updateUI();

        if(total>0)
            totalprice.setText(getString(R.string.Rs)+String.valueOf(total));
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
            mAdapter = new CartItemAdapter(cart, this);
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

    }

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
        return isConnected;
    }

    private void showSnack(boolean isConnected) {
        if(!isConnected) {
            String msg = "Check Internet Connection";
            Snackbar snackbar = Snackbar.make(findViewById(R.id.parentLayout), msg, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        GrossaryApplication shoppingCart = (GrossaryApplication)getApplicationContext();
        cart = shoppingCart.getCart();
        if(!cart.isEmpty()){
            getMenuInflater().inflate(R.menu.viewcart_menu, menu);
        }

        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_remove_from_cart) {

            if(Session.getUserId(this) != 1) {
                cart.removeAll(cart);

                ShoppingListLab s = ShoppingListLab.get(getApplicationContext());
                s.DisableAllItems();
                for (CartItem ci : s.getCartItemList()) {
                    ShoppingListAdapter.setLabQuantity(ci, ci.getProduct().getProduct_id(), 0, getApplicationContext());
                }
                CategoryWiseProductListActivity.getAdapter().notifyDataSetChanged();
                CategoryWiseProductListActivity.getSelectButton().setChecked(false);
                Intent intent = new Intent(ViewCartActivity.this, CustomerMainActivity.class);
                startActivity(intent);
            }else{
                cart.removeAll(cart);
                RetailLab.makeListNull();
                Intent intent = new Intent(ViewCartActivity.this, RetailerMainActivity.class);
                startActivity(intent);
            }
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private class CheckoutAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ViewCartActivity.this,
                    "Loading Stock",
                    "Please Wait");
            if (Session.getUserId(getApplicationContext()) == 1) {

            } else {

                CallServer.updateShoppingList(ViewCartActivity.this);

            }
            GrossaryApplication.getInstance().setLoading(true);
            CallServer.transaction(ViewCartActivity.this, jsonprodlist);
            waittillloading();

            GrossaryApplication.getInstance().setLoading(true);
            CallServer.checkout(ViewCartActivity.this, jsonprodlist);
            waittillloading();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = ViewCartActivity.this.getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( ViewCartActivity.this.getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (progressDialog!=null)
                progressDialog.dismiss();
            startActivity(i);
        }

        public void waittillloading(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(GrossaryApplication.getInstance().isLoading()){
                        Log.d("Cart","Wait");
                    }
                }
            }).start();

        }
    }
}