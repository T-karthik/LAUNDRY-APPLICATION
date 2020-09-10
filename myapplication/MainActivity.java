package com.example.myapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.annotation.NonNull;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


import android.util.Log;

import android.widget.Toast;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.messaging.FirebaseMessaging;


import javax.annotation.Nullable;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    final FirebaseAuth fAuth = FirebaseAuth.getInstance();
    TextView fullName,laundry,dayofsub,dayofcol;
    FirebaseFirestore fStore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyNotifications",
                    "MyNotifications",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "succesful";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }

                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

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






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.home)
        {
            Intent main_activity4 = new Intent(MainActivity.this, show_data.class);
            startActivity(main_activity4);
            
        }
        if(item.getItemId() == R.id.about)
        {
            Intent main_activity6 = new Intent(MainActivity.this, About.class);
            startActivity(main_activity6);

        }
        if(item.getItemId() == R.id.QRscn)
        {
            Intent main_activity3 = new Intent(MainActivity.this, Scanner.class);
            startActivity(main_activity3);
        }


        if(item.getItemId() == R.id.logout)
        {
            fAuth.signOut();
            Intent main_activity = new Intent(MainActivity.this, Login.class);
            startActivity(main_activity);
        }
        if(item.getItemId() == R.id.personal)
        {
            Intent main_activity1 = new Intent(MainActivity.this, personal.class);
            startActivity(main_activity1);
        }
        return true;
    }
}
