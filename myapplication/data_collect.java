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

public class data_collect extends AppCompatActivity {
    private static final String TAG = "data_collect";
    private static final String KEY_TITLE = "weekly submission day";
    private static final String KEY_DESCRIPTION = "weekly collection day";
    private EditText mSubmission;
    private EditText mCollection;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collect);

        mSubmission = findViewById(R.id.subdet);
        mCollection = findViewById(R.id.colldet);




        progressBar = findViewById(R.id.progressBar);


    }
    public void saveNote(View v) {
        String title = mSubmission.getText().toString();
        String description = mCollection.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_TITLE, title);
        note.put(KEY_DESCRIPTION, description);
        db.collection("ldetails").document("laundry").set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(data_collect.this, "data saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(data_collect.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}
