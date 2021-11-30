package com.example.adminarea_realfood.Screen;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.LoaiSanPham;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Validate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.UUID;

public class TaoLoaiSanPham extends AppCompatActivity {

    EditText edtStt, edtTenloai;
    Button btnTao;
    ImageButton ibImage;
    Uri imageLoaiSP;
    Validate validate = new Validate();
    ViewGroup viewGroup;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    KAlertDialog kAlertDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taoloaisanpham_activity);
        setControl();
        setEvent();
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
                        }).show(TaoLoaiSanPham.this);
            }
        });

        btnTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validated_Form()) {
                    KAlertDialog kAlertDialog = new KAlertDialog(TaoLoaiSanPham.this, KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                    kAlertDialog.show();

                    String uuid = UUID.randomUUID().toString().replace("-", "");
                    LoaiSanPham loaiSanPham = new LoaiSanPham(uuid, edtStt.getText().toString(), edtTenloai.getText().toString());
                    firebase_manager.Ghi_LoaiSanPham(loaiSanPham).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                            kAlertDialog.setContentText("Đã tạo loại sản phẩm thành công");
                        }
                    });
                    firebase_manager.UpImageLoaiSanPham(imageLoaiSP,uuid);
                    clearForm(viewGroup);
                }
            }
        });

    }


    private boolean Validated_Form() {
        boolean result = false;
        if (!validate.isBlank(edtTenloai)
        ){
            result = true;
            if (imageLoaiSP == null)
            {
                Toast.makeText(this, "Vui lòng chọn hình cho loại sản phẩm ", Toast.LENGTH_SHORT).show();
                result = false;
            }
        }
        return result;
    }

    private void setControl() {
        edtStt = findViewById(R.id.edt_stt_taoloaisanpham);
        edtTenloai = findViewById(R.id.edt_tenloai_taoloaisanpham);
        btnTao = findViewById(R.id.btn_tao_loaisanpham);
        ibImage = findViewById(R.id.ib_image_loaisanpham);
        viewGroup = findViewById(R.id.linear2);
    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
            if (view instanceof ImageButton) {
                ((ImageButton) view).setImageResource(R.drawable.picturefrontpremium);
            }
            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }
}
