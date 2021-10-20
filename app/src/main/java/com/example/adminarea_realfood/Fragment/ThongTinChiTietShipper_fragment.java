package com.example.adminarea_realfood.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinChiTietShipper_fragment extends Fragment {

    CircleImageView civImage;
    EditText edtTk, edtMk, edtTen, edtNgaysinh, edtMaxe, edtSdt;
    ImageButton ibTrc, ibSau;
    Button btnXoa, btnKhoa;
    Shipper shipper;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public ThongTinChiTietShipper_fragment(Shipper shipper) {
        this.shipper = shipper;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.thongtinchitietshipper_fragment, container, false);
        civImage = view.findViewById(R.id.profile_image_ttctshipper);
        edtTk = view.findViewById(R.id.edt_taikhoan_ttctshipper);
        edtMk = view.findViewById(R.id.edt_matkhau_ttctshipper);
        edtTen = view.findViewById(R.id.edt_hoten_ttctshipper);
        edtNgaysinh = view.findViewById(R.id.edt_ngaysinh_ttctshipper);
        edtMaxe = view.findViewById(R.id.edt_masoxe_ttctshipper);
        edtSdt = view.findViewById(R.id.edt_sdt_ttctshipper);
        ibTrc = view.findViewById(R.id.ib_idtruoc_ttctshipper);
        ibSau = view.findViewById(R.id.ib_idsau_ttctshipper);
        btnXoa = view.findViewById(R.id.btn_xoa_ttctshipper);
        btnKhoa = view.findViewById(R.id.btn_khoa_ttctshipper);

        storageReference.child("Shipper").child(shipper.getiDShipper()).child("avatar").getDownloadUrl(  ).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Glide.with(getContext())
                        .load(task.getResult().toString())
                        .into(civImage);
            }
        });
        edtTk.setText(shipper.geteMail());
        edtMk.setText(shipper.getMatKhau());
        edtTen.setText(shipper.getHoVaTen());
        edtNgaysinh.setText(shipper.getNgaySinh());
        edtMaxe.setText(shipper.getMaSoXe());
        edtSdt.setText(shipper.getSoDienThoai());
        storageReference.child("Shipper").child(shipper.getiDShipper()).child("CMND_MatTruoc").getDownloadUrl(  ).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Glide.with(getContext())
                        .load(task.getResult().toString())
                        .into(ibTrc);
            }
        });
        storageReference.child("Shipper").child(shipper.getiDShipper()).child("CMND_MatSau").getDownloadUrl(  ).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Glide.with(getContext())
                        .load(task.getResult().toString())
                        .into(ibSau);
            }
        });
        return view;
    }
}