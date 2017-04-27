package com.pkg.android.grossary.navigation.startScreenActivities;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pkg.android.grossary.ConnectionPackage.ConnectivityReceiver;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.UserInfo;
import com.pkg.android.grossary.navigation.Customer.CustomerMainActivity;
import com.pkg.android.grossary.other.Session;

/**
 * Created by GAURAV on 25-01-2017.
 */

public class SignUpActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, ConnectivityReceiver.ConnectivityReceiverListener{

    private static final int RC_SIGN_IN = 9001 ;
    private static final String TAG = "SignUpActivity";
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnGoogleSignIn;
    long count;
    private DatabaseReference dbref;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        dbref = FirebaseDatabase.getInstance().getReference("users");

        //wiring
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        btnGoogleSignIn = (SignInButton) findViewById(R.id.google_sign_in_button);
        btnGoogleSignIn.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if(checkConnection())
                    signIn();
            }
        });
        
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user

                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        //first check Internet access
                                        if(checkConnection()) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(SignUpActivity.this, "Registeration failed",
                                                        Toast.LENGTH_SHORT).show();

                                            } else {
                                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                saveintofirebase(user.getEmail());
                                                Session.setUserId(getApplicationContext(), auth);
                                                startActivity(new Intent(SignUpActivity.this, CustomerMainActivity.class));
                                                finish();
                                            }
                                        }
                                    }
                                });

            }
        });

    }

    private void saveintofirebase(String mail) {
        final String s = mail;

        dbref.addValueEventListener(new ValueEventListener() {
            boolean flag = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!flag) {
                    String userId = dbref.push().getKey();


                    count = dataSnapshot.getChildrenCount();
                    flag = true;
                    UserInfo u = new UserInfo(count + 1, s);
                    dbref.child(userId).setValue(u);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

        //goes to onActivityResult
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if(result.isSuccess()){
            GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            Log.d(TAG,"Successful Google sign In");

            Log.d(TAG, acct.getEmail() + " " +acct.getId() + " signed in");
            checkIfExists(acct);

        }
    }
    private void checkIfExists(final GoogleSignInAccount acct){
        //checks if user is already present

        final String mail = acct.getEmail();
        auth.fetchProvidersForEmail(mail).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if(task.isSuccessful()){
                    ///////// getProviders() will return size 1. if email ID is available.
                    task.getResult().getProviders();
                    handler = new Handler(getApplicationContext().getMainLooper());
                    new LoadID().execute();
                }else{
                    saveintofirebase(mail);
                }

                firebaseAuthWithGoogle(acct);
            }
        });
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this , "Authentication failed", Toast.LENGTH_SHORT).show();
                }
                else{
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Session.setUserId(getApplicationContext(), auth);
                    startActivity(new Intent(SignUpActivity.this, CustomerMainActivity.class));
                    finish();
                }

            }
        });


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //to check Internet Connection
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
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

                    while(Session.getUserId(SignUpActivity.this) == -1) {
                        //wait till id is loaded
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);

                        }
                    });

                }
            }).start();


        }
    }

}
