package com.pkg.android.grossary.other;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.Labs.RecipeLab;
import com.pkg.android.grossary.Labs.RetailLab;
import com.pkg.android.grossary.Labs.ShoppingListLab;

import java.util.Map;


/**
 * Created by GAURAV on 17-03-2017.
 */

public class Session {
    private static final String TAG = "GSession";
    public static final String SESSIONPREF = "SessionPref";
    public static final String USERID = "useridkey";
    private static final String SHOPPINGLISTSTRING = "shoppingListString";
    private static final String RECIPELISTSTRING = "recipeliststring";
    private static final String CURRSTOCKLISTSTRING = "currstockliststring";
    private static final String EXPSTOCKLISTSTRING = "expstockliststring";

    public static void setUserId(final Context context, final FirebaseAuth auth){

        removePreviousUserId(context);
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("users");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Object> keymap = (Map<String, Object>) dataSnapshot.getValue();
                for (Map.Entry<String, Object> entry:keymap.entrySet()) {
                    Map<String, String> mailmap = (Map<String, String>) entry.getValue();
                    SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
                    if(String.valueOf(mailmap.get("emailid")).equals(auth.getCurrentUser().getEmail())){

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt(USERID, Integer.parseInt(String.valueOf(mailmap.get("userid"))));
                        editor.commit();

                        //because for the first time we need id to be set otherwise it will fetch for 0
                        GrossaryApplication.getInstance().setShoppingListQuantities();

                        if((GrossaryApplication.getInstance().getShoppingListQuantities() == null)){
                            //if not there in pref, try to update the shoppinglist as it is

                            CallServer.updateShoppingList(context);//check here
                            GrossaryApplication.getInstance().setShoppingListQuantities();
                            //write code here for showing async task
                        }
                    }

                }
                //Toast.makeText(context, "Done" + String.valueOf(Session.getUserId(context)), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static int getUserId(final Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        int id = sharedpreferences.getInt(USERID,-1);
        return id;
    }


    public static void removePreviousUserId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(USERID);
        editor.commit();
    }

    public static String getShoppingListString(final Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        String listString= sharedpreferences.getString(SHOPPINGLISTSTRING,null);
        return listString;
    }

    public static void setShoppingListString(final Context context, String output){
        removePreviousShoppingList(context);
        SharedPreferences pref = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SHOPPINGLISTSTRING, output);
        editor.commit();
    }

    public static void removePreviousShoppingList(Context context) {
        ShoppingListLab.makeListNull();
        SharedPreferences pref = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(SHOPPINGLISTSTRING);
        editor.commit();
    }

    public static String getRecipeListString(final Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        String listString= sharedpreferences.getString(RECIPELISTSTRING,null);
        return listString;
    }

    public static void setRecipeListString(final Context context, String output){
        removeRecipeList(context);
        SharedPreferences pref = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(RECIPELISTSTRING, output);
        editor.commit();
    }

    public static void removeRecipeList(Context context) {
        RecipeLab.makeListNull();
        SharedPreferences pref = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(RECIPELISTSTRING);
        editor.commit();
    }

    public static String getCurrStockListString(final Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        String listString= sharedpreferences.getString(CURRSTOCKLISTSTRING,null);
        return listString;
    }

    public static void setCurrStockliststring(final Context context, String output){
        removeCurrStockList(context);
        SharedPreferences pref = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(CURRSTOCKLISTSTRING, output);
        editor.commit();
    }

    public static void removeCurrStockList(Context context) {
        RetailLab.makeListNull();
        SharedPreferences pref = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(CURRSTOCKLISTSTRING);
        editor.commit();
    }

    public static String getExpStockListString(final Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        String listString= sharedpreferences.getString(EXPSTOCKLISTSTRING,null);
        return listString;
    }

    public static void setExpStockListString(final Context context, String output){
        removeExpStockList(context);
        SharedPreferences pref = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(EXPSTOCKLISTSTRING, output);
        editor.commit();
    }

    public static void removeExpStockList(Context context) {
        RetailLab.makeListNull();
        SharedPreferences pref = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(EXPSTOCKLISTSTRING);
        editor.commit();
    }
}
