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
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Validate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class TaoTaiKhoanShipper extends AppCompatActivity {

    EditText edtTaikhoan, edtMatkhau, edtHoten, edtNgaysinh, edtMasoxe, edtSdt;
    CircleImageView avatar;
    ImageButton ibMattruoc, ibMatsau;
    Button btnTao;
    Validate validate = new Validate();
    ViewGroup viewGroup;
    Uri cmndTrc;
    Uri cmndSau;
    Uri avaTar;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    KAlertDialog kAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taotaikhoanshipper_activity);
        setControl();
        setEvent();

    }

    private void setEvent() {
        ibMattruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                ibMattruoc.setImageBitmap(r.getBitmap());
                                cmndTrc = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(TaoTaiKhoanShipper.this);
            }
        });

        ibMatsau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                ibMatsau.setImageBitmap(r.getBitmap());
                                cmndSau = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(TaoTaiKhoanShipper.this);
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                avatar.setImageBitmap(r.getBitmap());
                                avaTar = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(TaoTaiKhoanShipper.this);
            }
        });

        btnTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validated_Form()) {
                    KAlertDialog kAlertDialog = new KAlertDialog(TaoTaiKhoanShipper.this, KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                    kAlertDialog.show();

                    String uuid = UUID.randomUUID().toString().replace("-", "");
                    Shipper shipper = new Shipper(uuid, edtTaikhoan.getText().toString(), edtMatkhau.getText().toString(), edtHoten.getText().toString(),"","", edtNgaysinh.getText().toString(), edtMasoxe.getText().toString(),"",edtSdt.getText().toString());
                    firebase_manager.Ghi_Shipper(shipper).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                            kAlertDialog.setContentText("Đã tạo tài khoản thành công");
                        }
                    });
                    firebase_manager.Up2MatCMND(cmndTrc,cmndSau,uuid);
                    firebase_manager.UpAvatar(avaTar,uuid);
                    clearForm(viewGroup);

                }
            }
        });
    }
    private boolean Validated_Form() {
        boolean result = false;
        if (!validate.isBlank(edtTaikhoan) && validate.isEmail(edtTaikhoan)
                &&!validate.isBlank(edtMatkhau) && !validate.lessThan6Char(edtMatkhau)
                &&!validate.isBlank(edtHoten) &&!validate.isBlank(edtNgaysinh)
                &&!validate.isBlank(edtMasoxe) &&!validate.isBlank(edtSdt)
        ){
            result = true;
            if (cmndTrc == null||cmndSau == null)
            {
                Toast.makeText(this, "Vui lòng tải lên 2 mặt CMND/CCCD ", Toast.LENGTH_SHORT).show();
                result = false;
            }
        }
        return result;
    }

    private void setControl() {
        edtTaikhoan = findViewById(R.id.edt_taikhoan_taotaikhoan);
        edtMatkhau = findViewById(R.id.edt_matkhau_taotaikhoan);
        edtHoten = findViewById(R.id.edt_hoten_taotaikhoan);
        edtNgaysinh = findViewById(R.id.edt_ngaysinh_taotaikhoan);
        edtMasoxe = findViewById(R.id.edt_masoxe_taotaikhoan);
        edtSdt = findViewById(R.id.edt_sdt_taotaikhoan);
        avatar = findViewById(R.id.profile_image);
        ibMatsau = findViewById(R.id.ib_idsau);
        ibMattruoc = findViewById(R.id.ib_idtruoc);
        btnTao = findViewById(R.id.btn_tao);
        viewGroup = findViewById(R.id.linear1);
    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }
            if (view instanceof CircleImageView) {
                ((CircleImageView) view).setImageResource(R.drawable.ic_person);
            }
            if (view instanceof ImageButton)
            {
                if(view.getId() == R.id.ib_idtruoc){
                    ((ImageButton)view).setImageResource(R.drawable.idtruoc);
                }
                if (view.getId() == R.id.ib_idsau){
                    ((ImageButton) view).setImageResource(R.drawable.idsau);
                }
            }
            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }
}
