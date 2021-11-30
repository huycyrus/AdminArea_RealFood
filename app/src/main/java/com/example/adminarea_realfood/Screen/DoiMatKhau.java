package com.example.adminarea_realfood.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class DoiMatKhau extends AppCompatActivity {
    Button btnLuu;
    EditText edtMKCu, edtMKMoi, edtNhapLai;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    Validate validate = new Validate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate.isBlank(edtMKCu) && !validate.isBlank(edtMKMoi) && !validate.isBlank(edtNhapLai)) {
                    KAlertDialog kAlertDialog = new KAlertDialog(DoiMatKhau.this , KAlertDialog.PROGRESS_TYPE);
                    kAlertDialog.setContentText("Loading");
                    kAlertDialog.show();
                    FirebaseUser user = firebase_manager.auth.getCurrentUser();
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(firebase_manager.auth.getCurrentUser().getEmail(), edtMKCu.getText().toString());
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (edtMKMoi.getText().toString().equals(edtNhapLai.getText().toString())) {
                                    user.updatePassword(edtMKMoi.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            kAlertDialog.setContentText("Đã đổi mật khẩu thành công");
                                            kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                            kAlertDialog.setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                                @Override
                                                public void onClick(KAlertDialog kAlertDialog) {
                                                    Intent intent = new Intent(DoiMatKhau.this,TrangChu.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                }
                                            });

                                        }
                                    });
                                } else {
                                    edtNhapLai.setError("Mật khẩu không trùng khớp");
                                    kAlertDialog.dismiss();
                                }

                            } else {
                                kAlertDialog.setContentText("Mật khẩu sai");
                                kAlertDialog.changeAlertType(KAlertDialog.WARNING_TYPE);
                                kAlertDialog.showConfirmButton(false);
                            }
                        }
                    });
                }
            }
        });
    }

    private void setControl() {
        edtMKCu = findViewById(R.id.edt_matkhaucu_admin);
        edtMKMoi = findViewById(R.id.edt_matkhaumoi_admin);
        edtNhapLai = findViewById(R.id.edt_nhaplai_admin);
        btnLuu = findViewById(R.id.btnDoiMatKhau);
    }
}