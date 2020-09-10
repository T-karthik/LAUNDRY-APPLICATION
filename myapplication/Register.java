package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {

    EditText mFullName,mEmail,mPassword,mRepassword,mSubday,mCollday;
    Button mRegisterBtn;
    TextView mloginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName=findViewById(R.id.fullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mRepassword = findViewById(R.id.ReenterPassword);
        mSubday = findViewById(R.id.subday);
        mCollday = findViewById(R.id.collday);
        mRegisterBtn = findViewById(R.id.registerbtn);
        mloginBtn = findViewById(R.id.CreateText);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);
        //below if loop is to open main activity directly for already existing user.

        if(fAuth.getCurrentUser()!=null){
            Intent intent=new Intent(Register.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String reenterpassword = mRepassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String submission = mSubday.getText().toString();
                final String collection = mCollday.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("password is required");
                    return;

                }
                if(password.length()<8){
                    mPassword.setError("password must be atleast 8 characters");
                    return;
                }
                if(reenterpassword.length()<4){
                    mRepassword.setError("number must be exactly 4 digits.");
                }
                progressBar.setVisibility(View.VISIBLE);//spinning of progress bar

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"user created",Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("Submission day",submission);
                            user.put("Collection day",collection);
                            user.put("laundry number",reenterpassword);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG","Onsuccess: User profile is created for"+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","on Failure"+e.toString());
                                }
                            });
                            Intent intent=new Intent(Register.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Register.this,"Error !"+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

                }
            });
        mloginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });
        }
        }






