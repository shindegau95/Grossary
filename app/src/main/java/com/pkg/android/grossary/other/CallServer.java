package com.pkg.android.grossary.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.ConnectionPackage.ASyncResponse;
import com.pkg.android.grossary.ConnectionPackage.Connection;
import com.pkg.android.grossary.Labs.ShoppingListLab;
import com.pkg.android.grossary.navigation.Customer.CategoryWiseProductListActivity;
import com.pkg.android.grossary.navigation.Customer.ViewCartActivity;

import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GAURAV on 20-03-2017.
 */

public class CallServer {
    private static final String SHOPPINGLISTSTRING = "shoppingListString";

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
        Connection c=new Connection("http://104.198.118.35" + "/cgi-bin/customer/index1.php",context);  //For personalized recommendation,the php file is index.php
        // Make sure you change the url accordingly
        String jsonString = jsonprodlist.toString();
        String id = String.valueOf(Session.getUserId(activity.getApplicationContext()));
        Toast.makeText(activity.getApplicationContext(), String.valueOf(jsonString),Toast.LENGTH_SHORT).show();
        // String of id will be the id of the customer
        c.onPost(id+jsonString, a);
        // 'Connection to the server' code ends here
        //end

        GrossaryApplication.getInstance().setCart(null);
        activity.startActivity(i);
    }
    public static void updateShoppingList(final Context context){
        //Toast.makeText(activity, "outside async", Toast.LENGTH_LONG).show();


        ASyncResponse a=new ASyncResponse() {
            @Override
            public void processFinish(String output) {   // Server will return the output & store it in the string variable 'output'
                Log.e("param", output);
                /*Log.d("HELLO","sdfjsdhf" + output);
                Toast.makeText(activity, "inside async, update from server = " + output, Toast.LENGTH_LONG).show();
                */
                Toast.makeText(context, "update in remove 0" + Session.getShoppingListString(context), Toast.LENGTH_LONG).show();
                Session.removePreviousShoppingList(context);
                Toast.makeText(context, "update in remove 1" + Session.getShoppingListString(context), Toast.LENGTH_LONG).show();
                Session.setShoppingListString(context, output);
                Toast.makeText(context, "update in remove 2" + output, Toast.LENGTH_LONG).show();
                Log.d("HELLO","update in remove 2" + Session.getShoppingListString(context));
                //Log.d("SHOP", "from pref = " + Session.getShoppingListString(context));
                //Log.d("PARSE", Parser.removeBrackets("[ 1 2 1 3 3 3 1 ]"));
                //Parser.parseShoppingList(Session.getShoppingListString(context));
                GrossaryApplication grossaryApplication = (GrossaryApplication)context.getApplicationContext();
                //set shopping list
                grossaryApplication.setShoppingListQuantities();
                Log.d("AFTER PARSE", String.valueOf(grossaryApplication.getShoppingListQuantities()));
            }
        };
        Connection c=new Connection("http://104.198.118.35" + "/cgi-bin/customer/index.php",context);  //For personalized recommendation,the php file is index.php
        // Make sure you change the url accordingly
        String id = String.valueOf(Session.getUserId(context.getApplicationContext()));

        Toast.makeText(context.getApplicationContext(), "id = "+id,Toast.LENGTH_SHORT).show();
        // String of id will be the id of the customer
        c.onPost(id, a);
        // 'Connection to the server' code ends here
        //end

    }
}
