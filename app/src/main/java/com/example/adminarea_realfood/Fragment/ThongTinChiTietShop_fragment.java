package com.example.adminarea_realfood.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.SanPham;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiCuaHang;
import com.example.adminarea_realfood.Adapter.SanPhamAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinChiTietShop_fragment extends Fragment {

    ImageView ivWallpaper, ivTruoc, ivSau;
    CircleImageView civAvatar;
    TextView tvTenCuaHang, tvTenQuanLi, tvSDT, tvDiaChi, tvEmail;
    Button btnKhoa, btnGoKhoa, btnKichHoat;
    CuaHang cuaHang;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Firebase_Manager firebase_manager = new Firebase_Manager();
    SanPham sanPham;
    ArrayList<SanPham> sanPhams = new ArrayList<>();
    RecyclerView rv_danhsachsp;
    SanPhamAdapter sanPhamAdapter;
    GridLayoutManager gridLayoutManager ;
    ImageButton ib_down;
    LinearLayoutManager linearLayoutManager;


    public ThongTinChiTietShop_fragment(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        //GetSanPham();
        //GetDanhSachDanhMuc();
        sanPhamAdapter.notifyDataSetChanged();
        super.onResume();
        Log.d("a","oke");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.thongtinchitietshop_fragment, container, false);

        ivWallpaper = view.findViewById(R.id.iv_wallpaper);
        civAvatar = view.findViewById(R.id.civ_avatar_ttshop);
        ivTruoc = view.findViewById(R.id.ib_idtruoc_ttshop);
        ivSau = view.findViewById(R.id.ib_idsau_ttshop);
        tvTenCuaHang = view.findViewById(R.id.tv_tencuahang_ttshop);
        tvTenQuanLi = view.findViewById(R.id.tv_tenquanli_ttshop);
        tvSDT = view.findViewById(R.id.tv_sdt_ttshop);
        tvDiaChi = view.findViewById(R.id.tv_diachi_ttshop);
        tvEmail = view.findViewById(R.id.tv_email_ttshop);
        btnKhoa = view.findViewById(R.id.btn_khoa_ttshop);
        btnGoKhoa = view.findViewById(R.id.btn_gokhoa_ttshop);
        btnKichHoat = view.findViewById(R.id.btn_kichhoat_ttshop);
        rv_danhsachsp = view.findViewById(R.id.rv_danhsachsanpham_ttctshop);

        storageReference.child("CuaHang").child(cuaHang.getIDCuaHang()).child("WallPaper").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(ivWallpaper);
            }
        });
        storageReference.child("CuaHang").child(cuaHang.getIDCuaHang()).child("Avatar").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(civAvatar);
            }
        });

        storageReference.child("CuaHang").child(cuaHang.getIDCuaHang()).child("CMND_MatTruoc").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(ivTruoc);
            }
        });
        storageReference.child("CuaHang").child(cuaHang.getIDCuaHang()).child("CMND_MatSau").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(ivSau);
            }
        });

        firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                CuaHang temp  = snapshot.getValue(CuaHang.class);
                cuaHang = temp;
                tvTenCuaHang.setText(cuaHang.getTenCuaHang());
                tvTenQuanLi.setText(cuaHang.getChuSoHuu());
                tvSDT.setText(cuaHang.getSoDienThoai());
                tvDiaChi.setText(cuaHang.getDiaChi());
                tvEmail.setText(cuaHang.getEmail());
                LoadButton();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btnKichHoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                kAlertDialog.show();
                cuaHang.setTrangThaiCuaHang(TrangThaiCuaHang.DaKichHoat);
                firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        LoadButton();
                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setContentText("???? k??ch ho???t th??nh c??ng");
                    }
                });
            }
        });

        btnKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                kAlertDialog.show();
                cuaHang.setTrangThaiCuaHang(TrangThaiCuaHang.BiKhoa);
                firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        LoadButton();
                        kAlertDialog.changeAlertType(KAlertDialog.WARNING_TYPE);
                        kAlertDialog.setContentText("C???a h??ng b??? kh??a");
                    }
                });
            }
        });

        btnGoKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                kAlertDialog.show();
                cuaHang.setTrangThaiCuaHang(TrangThaiCuaHang.DaKichHoat);
                firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        LoadButton();
                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setContentText("C???a h??ng ???????c g??? kh??a");
                    }
                });
            }
        });
        LoadInfoSP();

        return view;
    }


    private void LoadInfoSP() {
        sanPhamAdapter = new SanPhamAdapter(getActivity(), R.layout.sanpham_item, sanPhams);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rv_danhsachsp.setLayoutManager(gridLayoutManager);
        rv_danhsachsp.setLayoutManager(linearLayoutManager);
        rv_danhsachsp.setAdapter(sanPhamAdapter);
        firebase_manager.GetSanPham(sanPhams,sanPhamAdapter,cuaHang);

    }


    private void LoadButton() {
        btnKhoa.setVisibility(View.GONE);
        btnKichHoat.setVisibility(View.GONE);
        btnGoKhoa.setVisibility(View.GONE);
        if(cuaHang.getTrangThaiCuaHang() == TrangThaiCuaHang.ChuaKichHoat){
            btnKichHoat.setVisibility(View.VISIBLE);
        }
        if(cuaHang.getTrangThaiCuaHang() == TrangThaiCuaHang.DaKichHoat){
            btnKhoa.setVisibility(View.VISIBLE);
        }
        if(cuaHang.getTrangThaiCuaHang() == TrangThaiCuaHang.BiKhoa){
            btnGoKhoa.setVisibility(View.VISIBLE);
        }
    }

    public ThongTinChiTietShop_fragment() {
    }

    public static ThongTinChiTietShop_fragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        ThongTinChiTietShop_fragment f = new ThongTinChiTietShop_fragment();
        f.setArguments(args);
        return f;
    }


}