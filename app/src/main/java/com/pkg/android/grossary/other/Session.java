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
import com.pkg.android.grossary.Labs.ShoppingListLab;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by GAURAV on 17-03-2017.
 */

public class Session {
    private static final String TAG = "Session";
    public static final String SESSIONPREF = "SessionPref";
    public static final String USERID = "useridkey";
    private static final String SHOPPINGLISTSTRING = "shoppingListString";

    public static void setUserId(final Context context, final FirebaseAuth auth){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("users");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> keymap = (Map<String, Object>) dataSnapshot.getValue();
                for (Map.Entry<String, Object> entry:keymap.entrySet()) {
                    Map<String, String> mailmap = (Map<String, String>) entry.getValue();
                    SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
                    if(String.valueOf(mailmap.get("emailid")).equals(auth.getCurrentUser().getEmail())){
                        Log.d(TAG,"userid = "+ String.valueOf(mailmap.get("userid")));
                        Toast.makeText(context,"before comit = " + String.valueOf(mailmap.get("userid")), Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt(USERID, Integer.parseInt(String.valueOf(mailmap.get("userid"))));
                        editor.commit();
                        Toast.makeText(context,"from pref comit = " + String.valueOf(sharedpreferences.getInt(USERID,0)), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context,"after comit = " + String.valueOf(Session.getUserId(context)), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*BGTask bgTask = new BGTask(context, auth);
        bgTask.execute();
        String id = bgTask.getId();
        Log.d("HELLO","userid = "+ id);
        Toast.makeText(context,"before comit = " + id, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(USERID, Integer.parseInt(id));
        editor.commit();
        Toast.makeText(context,"from pref comit = " + String.valueOf(sharedpreferences.getInt(USERID,0)), Toast.LENGTH_SHORT).show();
        Toast.makeText(context,"after comit = " + String.valueOf(Session.getUserId(context)), Toast.LENGTH_SHORT).show();
*/
    }

    public static int getUserId(final Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_MULTI_PROCESS);
        int id = sharedpreferences.getInt(USERID,0);
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

}
