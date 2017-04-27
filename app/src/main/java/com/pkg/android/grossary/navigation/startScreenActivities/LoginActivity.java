package com.pkg.android.grossary.navigation.startScreenActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pkg.android.grossary.ConnectionPackage.ConnectivityReceiver;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.navigation.Customer.CustomerMainActivity;
import com.pkg.android.grossary.navigation.Retailer.RetailerMainActivity;
import com.pkg.android.grossary.other.CallServer;
import com.pkg.android.grossary.other.Session;

/**
 * Created by GAURAV on 25-01-2017.
 */

/*
    This enables user to login using his registered email id and password provided the user has internet access
    It also has links to ResetPasswordActivity and SignUpActivity in case the user does not possess the credentials
    In case user has already signed in then it directly jumps to CustomerMainActivity
 */

public class LoginActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignUp, btnLogin, btnReset;
    private ProgressDialog progressDialog;
    Handler handler;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.dark_brown));

        //getting the firebase auth instance
        auth = FirebaseAuth.getInstance();

        //check if the user has already signed in
        if(auth.getCurrentUser() != null){
            if(auth.getCurrentUser().getEmail().equals("retailer.grossary@gmail.com")){
                startActivity(new Intent(LoginActivity.this, RetailerMainActivity.class));
            }
            else{
                startActivity(new Intent(LoginActivity.this, CustomerMainActivity.class));
            }
            finish();
        }

        setContentView(R.layout.activity_login);

        //wiring
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to SignUpActivity
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to ResetPasswordActivity
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fetch email and Password
                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                //Validation
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Email required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Password required", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //first check connection

                        if(checkConnection()) {
                            progressBar.setVisibility(View.GONE);

                            if (!task.isSuccessful()) {
                                if (password.length() < 6) {
                                    inputPassword.setError(getString(R.string.minimum_password));
                                } else {
                                    Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Intent intent;
                                Session.setUserId(LoginActivity.this, auth);


                                if(email.equals("retailer.grossary@gmail.com")){
                                    intent = new Intent(LoginActivity.this, RetailerMainActivity.class);
                                }else{
                                    intent = new Intent(LoginActivity.this, CustomerMainActivity.class);
                                }


                                handler = new Handler(getApplicationContext().getMainLooper());
                                new LoadID(intent).execute();

                            }
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

    private class LoadID extends AsyncTask<Void, Void, Void> {

        private Intent mIntent;

        public LoadID(Intent intent) {
            super();
            mIntent = intent;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while(Session.getUserId(LoginActivity.this) == -1) {
                        //wait till id is loaded
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            startActivity(mIntent);
                            finish();
                        }
                    });

                }
            }).start();


        }
    }
}
