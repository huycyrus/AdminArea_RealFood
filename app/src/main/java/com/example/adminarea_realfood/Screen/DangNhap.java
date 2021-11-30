package com.example.adminarea_realfood.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhap extends AppCompatActivity {

    EditText edtTK, edtMK;
    TextView tvLink;
    Button btnDangnhap;
    FirebaseAuth auth;
    KAlertDialog kAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.dangnhap_activity);
        setControl();
        setEvent();
        this.getSupportActionBar().hide();
        if(auth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(), TrangChu.class);
            edtTK.setText(auth.getCurrentUser().getEmail());
            startActivity(intent);
        }

    }

    private void setEvent() {
        Context context = this;

        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kAlertDialog = new KAlertDialog(context);

                kAlertDialog.setTitleText("Vui lòng chờ... ");
                kAlertDialog.show();
                if (edtTK.getText().toString().equals("huycyrusamin@gmail.com")){
                    kAlertDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE);
                    auth.signInWithEmailAndPassword(edtTK.getText().toString(), edtMK.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            kAlertDialog.setTitleText("Gửi thành công ");
                            kAlertDialog.show();
                            kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                            kAlertDialog.dismiss();
                            Intent intent = new Intent(DangNhap.this, TrangChu.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            kAlertDialog.changeAlertType(KAlertDialog.WARNING_TYPE);
                            kAlertDialog.setTitleText("Sai tài khoản hoặc mật khẩu");
                        }
                    });
                }
                else {
                    kAlertDialog.changeAlertType(KAlertDialog.WARNING_TYPE);
                    kAlertDialog.setTitleText("Tài khoản không khả dụng");
                }

            }
        });

        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuenMatKhau.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        edtTK = findViewById(R.id.edt_taikhoan);
        edtMK = findViewById(R.id.edt_matkhau);
        tvLink = findViewById(R.id.tv_quenmatkhau);
        btnDangnhap = findViewById(R.id.btn_dangnhap);
    }


}