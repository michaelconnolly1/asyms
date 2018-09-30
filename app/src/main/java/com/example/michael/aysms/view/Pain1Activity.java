package com.example.michael.aysms.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.michael.aysms.R;

public class Pain1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain1);
    }

    public void nextPage(View view) {
        Intent i = new Intent(Pain1Activity.this, HowDoYouFeelActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {
        Intent i = new Intent(Pain1Activity.this, BodySelectActivity.class);
        startActivity(i);
    }
}