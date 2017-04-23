package com.pkg.android.grossary.other;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pkg.android.grossary.Adapter.Customer.CategoryProductAdapter;
import com.pkg.android.grossary.Adapter.retailer.RetailExpandableAdapter;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.ConnectionPackage.ASyncResponse;
import com.pkg.android.grossary.ConnectionPackage.Connection;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.navigation.Retailer.RetailerHomeFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Created by GAURAV on 20-03-2017.
 */

public class CallServer {
    private static final String TAG = "GCallServer";

    public static void transaction(final Activity activity, JSONObject jsonprodlist){
        //this is to enter a transaction as record in the csv at server


        Context context=activity.getApplicationContext();
        ASyncResponse a=new ASyncResponse() {
            @Override
            public void processFinish(String output) {   // Server will return the output & store it in the string variable 'output'
                Log.e("Call Server","transaction, output = "+ output);
                GrossaryApplication.getInstance().setLoading(false);
            }
        };

        String ip = context.getString(R.string.ip_addr);
        Connection c=new Connection(ip + "/cgi-bin/customer/index1.php",context);  //For transaction index1
        String jsonString = jsonprodlist.toString();
        String id = String.valueOf(Session.getUserId(context));c.onPost(id+jsonString, a);


    }

    public static void checkout(final Activity activity, JSONObject jsonprodlist){
        //this is to checkout
        //for customer this decreases the curr_stock
        //for retailer this increases the curr_stock

        Context context=activity.getApplicationContext();
        ASyncResponse a=new ASyncResponse() {
            @Override
            public void processFinish(String output) {   // Server will return the output & store it in the string variable 'output'
                Log.e("Call Server","checkout, output = "+ output);
                GrossaryApplication.getInstance().setLoading(false);
            }
        };

        String ip = context.getString(R.string.ip_addr);
        String id = String.valueOf(Session.getUserId(context));
        Connection c;
        if(id.equals("1")){
            Session.removeCurrStockList(context);
            Session.removeExpStockList(context);
            c =new Connection(ip + "/cgi-bin/retailer/updateRetailer.php",context);  //For transaction transaction
        }else{
            c=new Connection(ip + "/cgi-bin/customer/updateCustomer.php",context);  //For transaction transaction
        }
        String jsonString = jsonprodlist.toString();
        jsonString = jsonString.replace("\"","");

        c.onPost(jsonString, a);
    }

    public static void updateShoppingList(final Context context){
        //updating the shopping list for customer

        ASyncResponse a=new ASyncResponse() {
            @Override
            public void processFinish(String output) {
                Log.e("Call Server","updateShoppingList, output = "+ output);

                Session.setShoppingListString(context, output);
                GrossaryApplication.getInstance().setShoppingListQuantities();
            }
        };

        String ip = context.getString(R.string.ip_addr);
        Connection c=new Connection(ip + "/cgi-bin/customer/index.php",context);  //For personalized recommendation,the php file is index.php
        String id = String.valueOf(Session.getUserId(context));

        c.onPost(id, a);
    }

    public static void updateRecipeList(final Activity activity, ArrayList<Integer> prodlist, final ProgressBar progressBar, final RecyclerView mRecipeRecyclerView){
        //updating the recipelist for customer

        final Context context=activity.getApplicationContext();
        ASyncResponse a=new ASyncResponse() {
            @Override
            public void processFinish(String output) {   // Server will return the output & store it in the string variable 'output'
                Log.e("Call Server","updateRecipeList, output = "+ output);

                Session.setRecipeListString(context, output);
                GrossaryApplication.getInstance().setRecipeList();

                CategoryProductAdapter.getRecipies();
                progressBar.setVisibility(View.GONE);
                mRecipeRecyclerView.setVisibility(View.VISIBLE);
            }
        };
        String ip = context.getString(R.string.ip_addr);
        Connection c=new Connection(ip + "/cgi-bin/customer/fp-grwoth/python-fp-growth-master/connect.php",context);  //For personalized recommendation,the php file is index.php
        String prodListString = prodlist.toString();
        prodListString = prodListString.replace(" ","");

        c.onPost(prodListString, a);
    }

    public static void updateStock(final Activity activity){
        //update stock for retailer

        final Context context = activity.getApplicationContext();
        ASyncResponse a=new ASyncResponse() {
            @Override
            public void processFinish(String output) {   // Server will return the output & store it in the string variable 'output'

                Log.e("Call Server","updateStock, output = "+ output);

                String s = Parser.removeBrackets(output);
                Scanner sc = new Scanner(s);
                sc.useDelimiter("\\], \\[");

                String curr_stock_string = Parser.removeBrackets(sc.next());
                Log.d("Call Server STOCK","curr="+curr_stock_string);
                Session.setCurrStockliststring(context, curr_stock_string);
                GrossaryApplication.getInstance().setCurrstocklist();

                String exp_stock_string = Parser.removeBrackets(sc.next());
                Log.d("Call Server STOCK","exp="+exp_stock_string);
                Session.setExpStockListString(context, exp_stock_string);
                GrossaryApplication.getInstance().setExpstocklist();

                GrossaryApplication.getInstance().setLoading(false);

            }
        };

        String ip = context.getString(R.string.ip_addr);
        Connection c=new Connection(ip + "/cgi-bin/customer/fetchData.php",context);

        c.onGet(a);
    }
}