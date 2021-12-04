package com.example.adminarea_realfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.databinding.ActivityPhanTranTrenDonHangBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.tapadoo.alerter.Alerter;

import java.util.Date;

public class PhanTranTrenDonHangActivity extends AppCompatActivity {
    ActivityPhanTranTrenDonHangBinding binding;
    Validate validate  = new Validate();
    Firebase_Manager firebase_manager = new Firebase_Manager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhanTranTrenDonHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadData();
        setEvent();
    }

    private void loadData() {
        firebase_manager.mDatabase.child("LoiNhuan").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Long value = dataSnapshot.getValue(Long.class);
                    binding.edtPhanTram.setText(value+"");
                }
            }
        });
    }

    private void setEvent() {
        binding.btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate.isBlank(binding.edtPhanTram)&&validate.isNumber(binding.edtPhanTram))
                {
                    int num = Integer.parseInt((binding.edtPhanTram.getText().toString()));
                    if (num>25)
                    {
                        Alerter.create(PhanTranTrenDonHangActivity.this)
                                .setTitle("Thông báo")
                                .setText("Lợi nhuận của hệ thống không được lớn hơn 25%")
                                .setBackgroundColorRes(R.color.error_stroke_color)
                                .setDuration(5000)// or setBackgroundColorInt(Color.CYAN)
                                .show();
                    }
                    else {
                        firebase_manager.mDatabase.child("LoiNhuan").setValue(num);
                    }
                }
            }
        });
    }

}