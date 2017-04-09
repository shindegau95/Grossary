package com.pkg.android.grossary.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.pkg.android.grossary.Adapter.CustomerCategoryProductAdapter;
import com.pkg.android.grossary.Adapter.RecipeAdapter;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.ConnectionPackage.ASyncResponse;
import com.pkg.android.grossary.ConnectionPackage.Connection;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.navigation.Customer.CategoryWiseProductListActivity;
import com.pkg.android.grossary.navigation.Customer.ViewCartActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GAURAV on 20-03-2017.
 */

public class CallServer {
    private static final String TAG = "GCallServer";

    public static void checkout(final Activity activity, JSONObject jsonprodlist){
        //start
        Intent i = activity.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( activity.getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Context context=activity.getApplicationContext();
        ASyncResponse a=new ASyncResponse() {
            @Override
            public void processFinish(String output) {   // Server will return the output & store it in the string variable 'output'
                Log.e("param", output);
                Toast.makeText(activity, output, Toast.LENGTH_LONG).show();
            }
        };
        String ip = context.getString(R.string.ip_addr);
        Connection c=new Connection(ip + "/cgi-bin/customer/index1.php",context);  //For transaction checkout
        // Make sure you change the url accordingly
        String jsonString = jsonprodlist.toString();
        Toast.makeText(activity.getApplicationContext(), String.valueOf(jsonString),Toast.LENGTH_SHORT).show();

        String id = String.valueOf(Session.getUserId(context));
        // String of id will be the id of the customer
        c.onPost(id+jsonString, a);
        // 'Connection to the server' code ends here
        //end
        activity.startActivity(i);
    }
    public static void updateShoppingList(final Context context){


        ASyncResponse a=new ASyncResponse() {
            @Override
            public void processFinish(String output) {
                // Server will return the output & store it in the string variable 'output'
                Log.d(TAG,"output + " +output);

                //Toast.makeText(context, "from server, output = " + output, Toast.LENGTH_LONG).show();
                Session.setShoppingListString(context, output);

                GrossaryApplication.getInstance().setShoppingListQuantities();
                Log.d(TAG, "After updating, list = " + String.valueOf(GrossaryApplication.getInstance().getShoppingListQuantities()));
            }
        };
        String ip = context.getString(R.string.ip_addr);
        Connection c=new Connection(ip + "/cgi-bin/customer/index.php",context);  //For personalized recommendation,the php file is index.php
        // Make sure you change the url accordingly
        String id = String.valueOf(Session.getUserId(context));

        // String of id will be the id of the customer
        c.onPost(id, a);
        // 'Connection to the server' code ends here
        //end

    }

    public static void updateRecipeList(final Activity activity, ArrayList<Integer> prodlist){
        //start
        final Context context=activity.getApplicationContext();
        ASyncResponse a=new ASyncResponse() {
            @Override
            public void processFinish(String output) {   // Server will return the output & store it in the string variable 'output'
                Log.e("RECIPE", output);
                //Toast.makeText(activity, output, Toast.LENGTH_LONG).show();
                Session.setRecipeListString(context, output);

                GrossaryApplication.getInstance().setRecipeList();

                CustomerCategoryProductAdapter.getRecipies();
            }
        };
        String ip = context.getString(R.string.ip_addr);
        Connection c=new Connection(ip + "/cgi-bin/customer/fp-grwoth/python-fp-growth-master/connect.php",context);  //For personalized recommendation,the php file is index.php
        // Make sure you change the url accordingly/

        String prodListString = prodlist.toString();
        prodListString = prodListString.replace(" ","");

        // String of id will be the id of the customer


        c.onPost(prodListString, a);
        // 'Connection to the server' code ends here
        //end
    }
}