package com.example.adminarea_realfood.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.Admin;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.SuaTaiKhoanAdmin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class CaiDat_fragment extends Fragment {

    CircleImageView civImage;
    Button btnLuu, btnDangXuat;
    TextView tvSua, tvTen, tvNgaySinh, tvSdt, tvEmail, tvTroGiup, tvRiengTu, tvChinhSach;
    EditText edtMKCu, edtMKMoi, edtNhapLai;
    Admin admin;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public CaiDat_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.caidat_fragment, container, false);

        civImage = view.findViewById(R.id.profile_image_admin);
        btnLuu = view.findViewById(R.id.btn_luu_admin);
        btnDangXuat = view.findViewById(R.id.btn_dangxuat_admin);
        tvSua = view.findViewById(R.id.tv_sua_admin);
        tvTen = view.findViewById(R.id.tv_ten_admin);
        tvEmail = view.findViewById(R.id.tv_email_admin);
        tvNgaySinh = view.findViewById(R.id.tv_ngaysinh_admin);
        tvSdt = view.findViewById(R.id.tv_sdt_admin);
        tvTroGiup = view.findViewById(R.id.tv_trogiup_admin);
        tvRiengTu = view.findViewById(R.id.tv_riengtu_admin);
        tvChinhSach = view.findViewById(R.id.tv_dvvacs_admin);
        edtMKCu = view.findViewById(R.id.edt_matkhaucu_admin);
        edtMKMoi = view.findViewById(R.id.edt_matkhaumoi_admin);
        edtNhapLai = view.findViewById(R.id.edt_nhaplai_admin);

        tvSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SuaTaiKhoanAdmin.class);
                startActivity(intent);
            }
        });

        storageReference.child("Admin").child("AvatarAdmin").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(civImage);
            }
        });
        tvEmail.setText(firebase_manager.auth.getCurrentUser().getEmail());
        firebase_manager.mDatabase.child("Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                admin = snapshot.getValue(Admin.class);
                tvTen.setText(admin.getHoTen());
                tvNgaySinh.setText(admin.getNgaySinh());
                tvSdt.setText(admin.getsDT());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }
}
