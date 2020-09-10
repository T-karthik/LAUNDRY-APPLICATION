package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class show_data extends AppCompatActivity {
    TextView fullName,laundry,dayofsub,dayofcol;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        fullName = findViewById(R.id.editText);
        laundry = findViewById(R.id.editText5);
        dayofsub = findViewById(R.id.editText3);
        dayofcol = findViewById(R.id.editText4);

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    fullName.setText(documentSnapshot.getString("fName"));

                    laundry.setText(documentSnapshot.getString("laundry number"));
                    dayofsub.setText(documentSnapshot.getString("Submission day"));
                    dayofcol.setText(documentSnapshot.getString("Collection day"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
    }
}
