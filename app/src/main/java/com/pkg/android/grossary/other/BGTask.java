package com.pkg.android.grossary.other;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.navigation.Customer.CategoryWiseProductListActivity;
import com.pkg.android.grossary.navigation.Customer.CustomerHomeFragment;
import com.pkg.android.grossary.navigation.Customer.CustomerMainActivity;
import com.pkg.android.grossary.startScreenActivities.LoginActivity;

import java.util.Map;

import static com.pkg.android.grossary.other.Session.SESSIONPREF;
import static com.pkg.android.grossary.other.Session.USERID;

/**
 * Created by GAURAV on 01-04-2017.
 */

public class BGTask extends AsyncTask<Void, Void, Void> {
    private Context mContext;
    private String id;
    private ProgressDialog progressDialog;

    public BGTask(Context context) {
        this.mContext =  context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        /*DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("users");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> keymap = (Map<String, Object>) dataSnapshot.getValue();
                for (Map.Entry<String, Object> entry:keymap.entrySet()) {
                    Map<String, String> mailmap = (Map<String, String>) entry.getValue();
                    if(String.valueOf(mailmap.get("emailid")).equals(auth.getCurrentUser().getEmail())){

                        setId(String.valueOf(mailmap.get("userid")));


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(mContext,"DONE", Toast.LENGTH_SHORT).show();
        /*if(progressDialog != null)
            progressDialog.dismiss();*/
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(mContext,"WAIT", Toast.LENGTH_SHORT).show();
        /*progressDialog = ProgressDialog.show(mContext,
                "ProgressDialog",
                "Wait ");*/


    }
}
