package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Element adsElement = new Element();
        adsElement.setTitle("Thanks for becoming our user");

        View aboutpage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.edmt)
                .setDescription("Laundry app")
                .addItem(new Element().setTitle("Project by Vijayendar,Sreevathsav,Karthik,Titus "))
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("tammanakarthik7@gmail.com")
                .addFacebook("titus das")
                .addInstagram("sreevathsav01")
                .addItem(createCopyright())
                .create();

        setContentView(aboutpage);

    }

    private Element createCopyright() {
        Element copyright = new Element();
        final String copyrightstring = String.format("Thanks again", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightstring);
        copyright.setIcon(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(About.this,copyrightstring,Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;

    }
}
