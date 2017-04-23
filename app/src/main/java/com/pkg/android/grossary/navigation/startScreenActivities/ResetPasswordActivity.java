package com.pkg.android.grossary.navigation.startScreenActivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.pkg.android.grossary.ConnectionPackage.ConnectivityReceiver;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.R;

/**
 * Created by GAURAV on 25-01-2017.
 */

/*
    Used for resetting password
    requires email and internet access
 */
public class ResetPasswordActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private EditText inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //wiring
        inputEmail = (EditText) findViewById(R.id.email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplication(), "Email required", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(checkConnection()) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, "Hello Hello We have sent you instructions to reset your password", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ResetPasswordActivity.this, "Failed to reseet email!", Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }else{
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    //to check Internet Connection
    @Override
    protected void onResume() {
        super.onResume();
        GrossaryApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
        return isConnected;
    }

    private void showSnack(boolean isConnected) {
        if(!isConnected) {
            String msg = "Check Internet Connection";
            Snackbar snackbar = Snackbar.make(findViewById(R.id.linearLayout), msg, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}
