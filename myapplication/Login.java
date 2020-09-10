package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {

    Button mLoginbutton;
    TextView mregisterBtn,forgottextlink;
    FirebaseAuth fAuth;
    ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText mEmail = findViewById(R.id.Email);
        final EditText mPassword = findViewById(R.id.Password);
        mLoginbutton = findViewById(R.id.Loginbtn);
        mregisterBtn = findViewById(R.id.CreateText1);
        forgottextlink = findViewById(R.id.resetpass);

        fAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressBar2);

        mLoginbutton.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("password is required");
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent=new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"logged in succesfully",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Login.this,"Error!"+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        mregisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
        forgottextlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetmail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset password?");
                passwordResetDialog.setMessage("Enter Email to get password Reset Link");
                passwordResetDialog.setView(resetmail);

                passwordResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetmail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this,"Reset link sent to registered email",Toast.LENGTH_SHORT).show();
                            }
                        });
                        fAuth.sendPasswordResetEmail(mail).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"Reset link not sent"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }
}
