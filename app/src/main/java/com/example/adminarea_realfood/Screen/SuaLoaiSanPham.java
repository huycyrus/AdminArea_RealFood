package com.example.adminarea_realfood.Screen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.LoaiSanPham;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Validate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.nordan.dialog.Animation;
import com.nordan.dialog.DialogType;
import com.nordan.dialog.NordanAlertDialog;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.List;
import java.util.UUID;

public class SuaLoaiSanPham extends AppCompatActivity {


    ImageButton ibImage;
    EditText edtTen;
    Button btnXoa;
    LoaiSanPham loaiSanPham;
    Uri imageLoaiSP;
    Validate validate = new Validate();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Firebase_Manager firebase_manager = new Firebase_Manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sualoaisanpham_activity);
        setControl();
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String dataLoaiSP = intent.getStringExtra("LoaiSanPham");
            Gson gson = new Gson();
            loaiSanPham = gson.fromJson(dataLoaiSP, LoaiSanPham.class);
        }
        LoadData();
        setEvent();
    }

    private void LoadData() {
        storageReference.child("LoaiSanPham").child(loaiSanPham.getiDLoai()).child("Loại sản phẩm").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(SuaLoaiSanPham.this)
                        .load(uri.toString())
                        .into(ibImage);
            }
        });
        edtTen.setText(loaiSanPham.getTenLoai());
    }

    private void setEvent() {
        ibImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                ibImage.setImageBitmap(r.getBitmap());
                                imageLoaiSP = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(SuaLoaiSanPham.this);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NordanAlertDialog.Builder(SuaLoaiSanPham.this)
                        .setDialogType(DialogType.WARNING)
                        .setAnimation(Animation.SLIDE)
                        .isCancellable(true)
                        .setTitle("Thông báo!")
                        .setMessage("Bạn có muốn xóa không ?")
                        .setPositiveBtnText("Có")
                        .setNegativeBtnText("Không")
                        .onPositiveClicked(() -> {
                            KAlertDialog kAlertDialog = new KAlertDialog(SuaLoaiSanPham.this, KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                            kAlertDialog.show();
                            firebase_manager.mDatabase.child("LoaiSanPham").child(loaiSanPham.getiDLoai()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                    kAlertDialog.setContentText("Đã xóa");
                                    kAlertDialog.setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                        @Override
                                        public void onClick(KAlertDialog kAlertDialog) {
                                            finish();
                                        }
                                    });

                                    //Xóa thư mục hình ảnh
                                    firebase_manager.storageRef.child("LoaiSanPham").child(loaiSanPham.getiDLoai()).child(loaiSanPham.getiDLoai()).listAll()
                                            .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                                                      @RequiresApi(api = Build.VERSION_CODES.N)
                                                                      @Override
                                                                      public void onSuccess(ListResult listResult) {
                                                                          List<StorageReference> items = listResult.getItems();
                                                                          items.forEach(storageReference -> storageReference.delete());

                                                                      }

                                                                  }
                                            );

                                }
                            });

                        })
                        .build().show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_save, menu);
        MenuItem saveItem = menu.findItem(R.id.action_Save);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Context context = this;
        switch (item.getItemId()) {
            case R.id.action_Save:
                if (Validated_Form()) {
                    KAlertDialog kAlertDialog = new KAlertDialog(SuaLoaiSanPham.this, KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                    kAlertDialog.show();
                    if(loaiSanPham == null){
                        String uuid = UUID.randomUUID().toString().replace("-", "");
                        loaiSanPham = new LoaiSanPham(uuid, null, edtTen.getText().toString());
                        firebase_manager.Ghi_LoaiSanPham(loaiSanPham).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                kAlertDialog.setContentText("Đã lưu thành công");
                            }
                        });
                        firebase_manager.UpImageLoaiSanPham(imageLoaiSP, uuid);
                    }else{
                        loaiSanPham = new LoaiSanPham(loaiSanPham.getiDLoai(), null, edtTen.getText().toString());
                        firebase_manager.Ghi_LoaiSanPham(loaiSanPham).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                kAlertDialog.setContentText("Đã lưu thành công");
                            }
                        });
                        if (imageLoaiSP != null){
                            firebase_manager.UpImageLoaiSanPham(imageLoaiSP,loaiSanPham.getiDLoai());
                        }
                    }

                }
        }


        return super.onOptionsItemSelected(item);
    }

    private boolean Validated_Form() {
        boolean result = false;
        if (!validate.isBlank(edtTen)
        ) {
            result = true;
            if (ibImage == null) {
                Toast.makeText(this, "Vui lòng chọn hình cho loại sản phẩm ", Toast.LENGTH_SHORT).show();
                result = false;
            }
        }
        return result;
    }

    private void setControl() {
        ibImage = findViewById(R.id.ib_image_sualoaisanpham);
        edtTen = findViewById(R.id.edt_tenloai_sualoaisanpham);
        btnXoa = findViewById(R.id.btn_xoa_sualoaisanpham);
    }
}