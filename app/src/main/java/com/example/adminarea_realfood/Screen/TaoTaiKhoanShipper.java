package com.example.adminarea_realfood.Screen;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Map.MapsActivity;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.Model.StorePassword;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiShipper;
import com.example.adminarea_realfood.Validate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;


public class TaoTaiKhoanShipper extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText edtTaikhoan, edtMatkhau, edtHoten, edtNgaysinh, edtMasoxe, edtSdt, edtDiaChi;
    CircleImageView avatar;
    ImageButton ibMattruoc, ibMatsau;
    Button btnTao;
    Validate validate = new Validate();
    ViewGroup viewGroup;
    Uri cmndTrc;
    Uri cmndSau;
    Uri avaTar;
    StorePassword storePassword;
    TextView tvGoToMap;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    KAlertDialog kAlertDialog;
    int LAUNCH_SECOND_ACTIVITY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storePassword = new StorePassword(this);

        setContentView(R.layout.taotaikhoanshipper_activity);
        setControl();
        setEvent();

    }

    private void setEvent() {
        edtNgaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPickerDailog();
            }
        });

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
                    String email = firebase_manager.auth.getCurrentUser().getEmail();
                    firebase_manager.auth.createUserWithEmailAndPassword(edtTaikhoan.getText().toString(), edtMatkhau.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            String uuid = authResult.getUser().getUid();
                            Shipper shipper = new Shipper(uuid, "", edtTaikhoan.getText().toString(), edtMatkhau.getText().toString(), edtHoten.getText().toString(), edtDiaChi.getText().toString(), "", edtNgaysinh.getText().toString(), edtMasoxe.getText().toString(), edtSdt.getText().toString(), TrangThaiShipper.KhongHoatDong, "H??? th???ng");
                            firebase_manager.Ghi_Shipper(shipper).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                    kAlertDialog.setContentText("???? t???o t??i kho???n th??nh c??ng");
                                    firebase_manager.Up2MatCMND(cmndTrc, cmndSau, uuid);
                                    firebase_manager.UpAvatar(avaTar, uuid);
                                    firebase_manager.auth.signOut();
                                    firebase_manager.auth.signInWithEmailAndPassword(email,storePassword.getPassword());
                                    clearForm(viewGroup);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            kAlertDialog.changeAlertType(KAlertDialog.ERROR_TYPE);
                            kAlertDialog.setContentText("Email ???? ???????c s??? d???ng b???i ng?????i d??ng kh??c");
                        }
                    });
                }
            }
        });

        tvGoToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaoTaiKhoanShipper.this, MapsActivity.class);
                startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                edtDiaChi.setText(result);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }
    private void showDataPickerDailog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + "/" + year;
        edtNgaysinh.setText(date);
    }

    private boolean Validated_Form() {
        boolean result = false;
        if (!validate.isBlank(edtTaikhoan) && validate.isEmail(edtTaikhoan)
                && !validate.isBlank(edtMatkhau) && !validate.lessThan6Char(edtMatkhau)
                && !validate.isBlank(edtHoten) && !validate.isBlank(edtNgaysinh)
                && !validate.isBlank(edtMasoxe) && !validate.isBlank(edtSdt) && !validate.isBlank(edtDiaChi)
        ) {
            result = true;
            if (avaTar == null) {
                Toast.makeText(this, "Vui l??ng t???i ???nh c?? nh??n ", Toast.LENGTH_SHORT).show();
                result = false;
            }
            if (cmndTrc == null || cmndSau == null) {
                Toast.makeText(this, "Vui l??ng t???i l??n 2 m???t CMND/CCCD ", Toast.LENGTH_SHORT).show();
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
        edtDiaChi = findViewById(R.id.edt_diachi_taotaikhoan);
        avatar = findViewById(R.id.profile_image);
        ibMatsau = findViewById(R.id.ib_idsau);
        ibMattruoc = findViewById(R.id.ib_idtruoc);
        btnTao = findViewById(R.id.btn_tao);
        viewGroup = findViewById(R.id.linear1);
        tvGoToMap = findViewById(R.id.tvGoToMap);
    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
            if (view instanceof CircleImageView) {
                ((CircleImageView) view).setImageResource(R.drawable.ic_person);
            }
            if (view instanceof ImageButton) {
                if (view.getId() == R.id.ib_idtruoc) {
                    ((ImageButton) view).setImageResource(R.drawable.idtruoc);
                }
                if (view.getId() == R.id.ib_idsau) {
                    ((ImageButton) view).setImageResource(R.drawable.idsau);
                }
            }
            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }
}
