package com.pkg.android.grossary.other;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pkg.android.grossary.Applications.GrossaryApplication;

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
                    //Log.d(TAG, entry.getKey());
                    //Log.d(TAG, entry.getValue().toString());
                    Map<String, String> mailmap = (Map<String, String>) entry.getValue();
                    //GrossaryApplication appn = (GrossaryApplication)context.getApplicationContext();
                    SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_PRIVATE);
                    if(String.valueOf(mailmap.get("emailid")).equals(auth.getCurrentUser().getEmail())){
                        Log.d(TAG,"userid = "+ String.valueOf(mailmap.get("userid")));
                        //appn.setUserID(Integer.parseInt(String.valueOf(mailmap.get("userid"))));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt(USERID, Integer.parseInt(String.valueOf(mailmap.get("userid"))));
                        editor.commit();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static int getUserId(final Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_PRIVATE);
        int id = sharedpreferences.getInt(USERID,0);
        return id;
    }

    public static String getShoppingListString(final Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SESSIONPREF, Context.MODE_PRIVATE);
        String listString= sharedpreferences.getString(SHOPPINGLISTSTRING,null);
        return listString;
    }

    public static void setShoppingListString(final Context context, String output){
        SharedPreferences pref = context.getSharedPreferences(SESSIONPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SHOPPINGLISTSTRING, output);
        editor.commit();
    }
}
