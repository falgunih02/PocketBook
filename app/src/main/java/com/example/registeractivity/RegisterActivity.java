package com.example.registeractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private EditText username, phone, email, pass1, pass2;
    TextView mLoginBtn;
    DatabaseReference dbUser;
    private FirebaseAuth mAuth;
    public static final String TAG = "TAG";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn = (Button) findViewById(R.id.submit);
        username = (EditText) findViewById(R.id.username1);
        phone = (EditText) findViewById(R.id.mobileno1);
        pass1 = (EditText) findViewById(R.id.pass1);
        email = (EditText) findViewById(R.id.email1);
        pass2 = (EditText) findViewById(R.id.pass2);
        mLoginBtn = findViewById(R.id.createText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PocketBook");
        mAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        String user = username.getText().toString();
        String pass3 = pass1.getText().toString();
        String email3 = email.getText().toString();
        String phone3 = phone.getText().toString();
        String pass4 = pass2.getText().toString();
        dbUser = FirebaseDatabase.getInstance().getReference().child("user_info");
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            if (!TextUtils.isEmpty(user) || !TextUtils.isEmpty(pass3) || !TextUtils.isEmpty(email3) || !TextUtils.isEmpty(phone3))
            {
                if (phone3.length() == 10)
                {
                    if (pass3.equals(pass4))
                    {
                        if (email3.contains("@gmail"))
                        {
                            if (pass3.length() >= 6)
                            {
                                String id = dbUser.push().getKey();
                                User u1 = new User(id, user, phone3, email3, pass3);
                                dbUser.child(id).setValue(u1);
                                //sendEmailVerification();
                                mAuth.createUserWithEmailAndPassword(email3, pass3).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            // send verification link

                                            FirebaseUser fuser = mAuth.getCurrentUser();
                                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(RegisterActivity.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                                }
                                            });

                                            pass1.setText("");
                                            username.setText("");
                                            email.setText("");
                                            phone.setText("");
                                            pass2.setText("");
                                            //Toast.makeText(RegisterActivity.this, "successfully registered!.", Toast.LENGTH_SHORT).show();
                                            //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(RegisterActivity.this, "Password Must Be Greater Than 6 Characters",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Email Is Not Correct",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        pass2.setText("");
                        Toast.makeText(RegisterActivity.this, "Password mismatched", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Invalid phone number",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Please Fill all the details",
                        Toast.LENGTH_LONG).show();
            }
        } else {
                Toast.makeText(RegisterActivity.this, "Please Turn On The Internet Connection",
                        Toast.LENGTH_LONG).show();
            }

    }

    public void click(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ug) {
            Intent intent = new Intent(RegisterActivity.this, UserGuide.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.au) {
            Intent intent = new Intent(RegisterActivity.this, AboutUs.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //public void sendEmailVerification() {
      //  FirebaseUser firebaseUser = mAuth.getCurrentUser();
        //if (firebaseUser != null) {
          //  firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            //   @Override
              //  public void onComplete(@NonNull Task<Void> task) {
                //   if (task.isSuccessful()) {
                  //      Toast.makeText(RegisterActivity.this, "successfully registered,verification message sent", Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                      // mAuth.signOut();
                     //finish();
//
  //                  } else {
    //                    Toast.makeText(RegisterActivity.this, "verification message hasn't sent", Toast.LENGTH_SHORT).show();
//
  //                  }
      //          }
    //        });
        //}
    //}
}

