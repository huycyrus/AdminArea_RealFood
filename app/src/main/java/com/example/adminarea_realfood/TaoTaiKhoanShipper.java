package com.example.adminarea_realfood;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class TaoTaiKhoanShipper extends AppCompatActivity {

    EditText edtTaikhoan, edtMatkhau, edtHoten, edtNgaysinh, edtMasoxe;
    CircleImageView circleImageView;
    ImageButton ibMattruoc, ibMatsau;
    Button btnTao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taotaikhoanshipper_activity);
        setControl();
        setEvent();

    }

    private void setEvent() {
    }

    private void setControl() {
        edtTaikhoan = findViewById(R.id.edt_taikhoan);
        edtMatkhau = findViewById(R.id.edt_matkhau);
        edtHoten = findViewById(R.id.edt_hoten_taotaikhoan);
        edtNgaysinh = findViewById(R.id.edt_ngaysinh_taotaikhoan);
        edtMasoxe = findViewById(R.id.edt_masoxe_taotaikhoan);
        circleImageView = findViewById(R.id.profile_image);
        ibMatsau = findViewById(R.id.ib_idsau);
        ibMattruoc = findViewById(R.id.ib_idtruoc);
        btnTao = findViewById(R.id.btn_tao);
    }
}
