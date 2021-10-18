package com.example.adminarea_realfood.Screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.DangNhap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class QuenMatKhau extends AppCompatActivity {

    EditText edtEmail;
    Button btn_gui;
    TextView tv_linkdangnhap;
    FirebaseAuth auth;
    KAlertDialog kAlertDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.quenmatkhau_activity);
        this.getSupportActionBar().hide();
        setControl();
        setEvent();
    }

    private void setEvent() {
        Context context = this;

        btn_gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kAlertDialog = new KAlertDialog(context);

                kAlertDialog.setTitleText("Vui lòng chờ... ");
                kAlertDialog.show();
                kAlertDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE);
                auth.sendPasswordResetEmail(edtEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    kAlertDialog.setTitleText("Gửi thành công ");
                                    kAlertDialog.show();
                                    kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                } else {
                                    kAlertDialog.changeAlertType(KAlertDialog.WARNING_TYPE);
                                    kAlertDialog.setTitleText("Sai tài khoản hoặc mật khẩu");
                                }
                            }
                        });
            }
        });

        tv_linkdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DangNhap.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        edtEmail = findViewById(R.id.edt_email);
        btn_gui = findViewById(R.id.btn_guiemailxacthuc);
        tv_linkdangnhap = findViewById(R.id.tv_dangnhap);
    }
}
