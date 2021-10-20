package com.example.adminarea_realfood.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.adminarea_realfood.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinChiTietShipper_fragment extends Fragment {

    CircleImageView civImage;
    EditText edtTk, edtMk, edtTen, edtNgaysinh, edtMaxe, edtSdt;
    ImageButton ibTrc, ibSau;
    Button btnXoa, btnKhoa;

    public ThongTinChiTietShipper_fragment() {
        // Required empty public constructor
    }

    public static ThongTinChiTietShipper_fragment newInstance(String param1, String param2) {
        ThongTinChiTietShipper_fragment fragment = new ThongTinChiTietShipper_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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


        return view;
    }
}