package com.example.adminarea_realfood.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Validate;
import com.example.adminarea_realfood.databinding.ActivityPhanTranTrenDonHangBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.tapadoo.alerter.Alerter;

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
                                .setTitle("Th??ng b??o")
                                .setText("L???i nhu???n c???a h??? th???ng kh??ng ???????c l???n h??n 25%")
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