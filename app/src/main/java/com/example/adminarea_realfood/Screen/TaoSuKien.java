package com.example.adminarea_realfood.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.SuKien;
import com.example.adminarea_realfood.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.UUID;

public class TaoSuKien extends AppCompatActivity {
    ImageView ivSuKien, ivAdd;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    Uri hinhAnhSuKien;
    Button btnTaoSuKien;
    EditText edtTenSuKien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_su_kien);
        setControl();
        setEvent();
    }

    private void setEvent() {
        ivSuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        ivSuKien.setImageBitmap(r.getBitmap());
                        hinhAnhSuKien = r.getUri();
                        ivAdd.setVisibility(View.GONE);
                    }
                }).setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        if (hinhAnhSuKien == null) {
                            ivAdd.setVisibility(View.VISIBLE);
                        }
                    }
                }).show(TaoSuKien.this);
            }
        });
        btnTaoSuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idSuKien = UUID.randomUUID().toString();
                String tenSuKien = edtTenSuKien.getText().toString();
                if (hinhAnhSuKien != null) {
                    firebase_manager.storageRef.child("SuKien").child(idSuKien).child("ImageSuKien").putFile(hinhAnhSuKien).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            SuKien suKien = new SuKien(idSuKien,tenSuKien);
                            firebase_manager.mDatabase.child("SuKien").child(idSuKien).setValue(suKien);
                        }
                    });
                }
                finish();
            }
        });
    }

    private void setControl() {
        edtTenSuKien = findViewById(R.id.edtTenSuKien);
        ivSuKien = findViewById(R.id.ivSuKien);
        btnTaoSuKien = findViewById(R.id.btnTaoSuKien);
        ivAdd = findViewById(R.id.ivAdd);
    }
}