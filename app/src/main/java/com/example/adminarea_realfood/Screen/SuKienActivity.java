package com.example.adminarea_realfood.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.SuKien;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.adapter.SuKienAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SuKienActivity extends AppCompatActivity {
    FloatingActionButton btnAdd;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    RecyclerView rcvSuKien;
    LinearLayoutManager linearLayoutManager;
    SuKienAdapter suKienAdapter;
    ArrayList<SuKien> suKiens;
    ProgressBar pbLoadSuKien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_su_kien);
        suKiens = new ArrayList<>();
        suKienAdapter = new SuKienAdapter(this, R.layout.sukien_item, suKiens);
        setControl();
        setEvent();
    }


    private void setEvent() {
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvSuKien.setLayoutManager(linearLayoutManager);
        rcvSuKien.setAdapter(suKienAdapter);
        LoadSuKien();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuKienActivity.this, TaoSuKien.class);
                startActivity(intent);
            }
        });


    }

    private void LoadSuKien() {
        firebase_manager.mDatabase.child("SuKien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                suKiens.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SuKien suKien = dataSnapshot.getValue(SuKien.class);
                    suKiens.add(suKien);
                    suKienAdapter.notifyDataSetChanged();
                }
                if (suKiens.size() == 0) {
                    suKiens.clear();
                    suKienAdapter.notifyDataSetChanged();
                }
                pbLoadSuKien.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        btnAdd = findViewById(R.id.fab_add);
        rcvSuKien = findViewById(R.id.rcvSuKien);
        pbLoadSuKien = findViewById(R.id.pbLoadSuKien);
    }
}