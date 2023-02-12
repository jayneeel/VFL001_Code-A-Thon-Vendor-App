package com.example.vendorlyvendorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Navigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        getSupportActionBar().hide();

    }

    public void nxtRegister(View view) {
        Intent i = new Intent(Navigation.this,Register.class);
        startActivity(i);
    }

    public void nxtLogin(View view) {
        Intent i = new Intent(Navigation.this,Login.class);
        startActivity(i);
    }
}