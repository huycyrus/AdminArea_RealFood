package com.example.adminarea_realfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TrangChu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.trangchu_activity);
    }
}