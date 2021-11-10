package com.example.adminarea_realfood.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinChiTietShipper_fragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    CircleImageView civImage;
    EditText edtTk, edtMk, edtTen, edtNgaysinh, edtMaxe, edtSdt;
    ImageButton ibTrc, ibSau;
    Button btnXoa, btnKhoa;
    Shipper shipper;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Uri cmndTrc;
    Uri cmndSau;
    Uri avaTar;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    Validate validate = new Validate();
    private String passWord;

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
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.thongtinchitietshipper_fragment, container, false);
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

        storageReference.child("Shipper").child(shipper.getiDShipper()).child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(civImage);
            }
        });
        passWord = shipper.getMatKhau();
        edtTk.setText(shipper.geteMail());
        edtMk.setText(shipper.getMatKhau());
        edtTen.setText(shipper.getHoVaTen());
        edtNgaysinh.setText(shipper.getNgaySinh());
        edtMaxe.setText(shipper.getMaSoXe());
        edtSdt.setText(shipper.getSoDienThoai());
        storageReference.child("Shipper").child(shipper.getiDShipper()).child("CMND_MatTruoc").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(ibTrc);
            }
        });
        storageReference.child("Shipper").child(shipper.getiDShipper()).child("CMND_MatSau").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(ibSau);
            }
        });

        setEvent();
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_save, menu);
    }

    private void setEvent() {
        edtNgaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPickerDailog();
            }
        });

        ibTrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                ibTrc.setImageBitmap(r.getBitmap());
                                cmndTrc = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(getActivity());
            }
        });

        ibSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                ibSau.setImageBitmap(r.getBitmap());
                                cmndSau = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(getActivity());
            }
        });

        civImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ssss", Toast.LENGTH_SHORT).show();
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                civImage.setImageBitmap(r.getBitmap());
                                avaTar = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(getActivity());
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.action_Save:
                Toast.makeText(getContext(), "oke", Toast.LENGTH_SHORT).show();
                if (Validated_Form()) {
                    KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                    kAlertDialog.show();
                    if (shipper == null) {
                        firebase_manager.auth.createUserWithEmailAndPassword(edtTk.getText().toString(), edtMk.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                String uuid = authResult.getUser().getUid();
                                Shipper shipper = new Shipper(uuid, edtTk.getText().toString(), edtMk.getText().toString(), edtTen.getText().toString(), "", "", edtNgaysinh.getText().toString(), edtMaxe.getText().toString(), "", edtSdt.getText().toString());
                                firebase_manager.Ghi_Shipper(shipper).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                        kAlertDialog.setContentText("Đã tạo tài khoản thành công");
                                        firebase_manager.Up2MatCMND(cmndTrc, cmndSau, uuid);
                                        firebase_manager.UpAvatar(avaTar, uuid);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                kAlertDialog.changeAlertType(KAlertDialog.ERROR_TYPE);
                                kAlertDialog.setContentText("Email đã được sử dụng bởi người dùng khác");
                            }
                        });
                    } else {
                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setContentText("Đã lưu thành công");
                        FirebaseUser user = firebase_manager.auth.getCurrentUser();
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(firebase_manager.auth.getCurrentUser().getEmail(), passWord);
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                }
                            }
                        });
                        Shipper temp = new Shipper(shipper.getiDShipper(), edtTk.getText().toString(), edtMk.getText().toString(), edtTen.getText().toString(), "", "", edtNgaysinh.getText().toString(), edtMaxe.getText().toString(), "", edtSdt.getText().toString());
                        firebase_manager.Ghi_Shipper(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                if (cmndTrc != null || cmndSau != null) {
                                    firebase_manager.Up2MatCMND(cmndTrc, cmndSau, shipper.getiDShipper());
                                }
                                if (avaTar != null) {
                                    firebase_manager.UpAvatar(avaTar, shipper.getiDShipper());
                                }


                            }
                        });
                    }
                }

        }

        return true;
    }

    private void showDataPickerDailog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
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
        if (!validate.isBlank(edtTk) && validate.isEmail(edtTk)
                && !validate.isBlank(edtMk) && !validate.lessThan6Char(edtMk)
                && !validate.isBlank(edtTen) && !validate.isBlank(edtNgaysinh)
                && !validate.isBlank(edtMaxe) && !validate.isBlank(edtSdt)
        ) {
            result = true;
            if (cmndTrc == null || cmndSau == null) {
                Toast.makeText(getContext(), "Vui lòng tải lên 2 mặt CMND/CCCD ", Toast.LENGTH_SHORT).show();
                result = false;
            }
            if (avaTar == null) {
                Toast.makeText(getContext(), "Vui lòng tải ảnh cá nhân", Toast.LENGTH_SHORT).show();
                result = false;
            }
        }
        return result;
    }

}