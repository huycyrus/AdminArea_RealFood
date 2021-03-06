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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.Model.StorePassword;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiShipper;
import com.example.adminarea_realfood.Validate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tapadoo.alerter.Alerter;
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
    EditText edtTk, edtMk, edtTen, edtNgaysinh, edtMaxe, edtSdt, edtDiaChi;
    ImageButton ibTrc, ibSau;
    Button btnXoa, btnKhoa, btnGoKhoa;
    Shipper shipper;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Uri cmndTrc;
    Uri cmndSau;
    Uri avaTar;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    Validate validate = new Validate();
    StorePassword storePassword ;
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
        storePassword = new StorePassword(getContext());
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
        edtDiaChi = view.findViewById(R.id.edt_diachi_ttctshipper);
        ibTrc = view.findViewById(R.id.ib_idtruoc_ttctshipper);
        ibSau = view.findViewById(R.id.ib_idsau_ttctshipper);
        btnXoa = view.findViewById(R.id.btn_xoa_ttctshipper);
        btnKhoa = view.findViewById(R.id.btn_khoa_ttctshipper);
        btnGoKhoa = view.findViewById(R.id.btn_Gokhoa_ttctshipper);

        storageReference.child("Shipper").child(shipper.getiDShipper()).child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(civImage);
                avaTar = uri;
            }
        });
        passWord = shipper.getMatKhau();

        firebase_manager.mDatabase.child("Shipper").child(shipper.getiDShipper()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Shipper temp = snapshot.getValue(Shipper.class);
                shipper = temp;
                edtTk.setText(shipper.geteMail());
                edtMk.setText(shipper.getMatKhau());
                edtTen.setText(shipper.getHoVaTen());
                edtNgaysinh.setText(shipper.getNgaySinh());
                edtMaxe.setText(shipper.getMaSoXe());
                edtSdt.setText(shipper.getSoDienThoai());
                edtDiaChi.setText(shipper.getDiaChi());
                LoadButton();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        storageReference.child("Shipper").child(shipper.getiDShipper()).child("CMND_MatTruoc").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(ibTrc);
                cmndTrc = uri;
            }
        });
        storageReference.child("Shipper").child(shipper.getiDShipper()).child("CMND_MatSau").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(ibSau);
                cmndSau = uri;
            }
        });

        btnKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                kAlertDialog.show();
                shipper.setTrangThaiShipper(TrangThaiShipper.BiKhoa);
                firebase_manager.mDatabase.child("Shipper").child(shipper.getiDShipper()).setValue(shipper).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                shipper.setTrangThaiShipper(TrangThaiShipper.KhongHoatDong);
                firebase_manager.mDatabase.child("Shipper").child(shipper.getiDShipper()).setValue(shipper).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        LoadButton();
                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setContentText("C???a h??ng ???????c g??? kh??a");
                    }
                });
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
        switch (item.getItemId()) {
            case R.id.action_Save:
                if (Validated_Form()) {
                    KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                    kAlertDialog.show();
                    if (shipper == null) {
                        String email = firebase_manager.auth.getCurrentUser().getEmail();
                        firebase_manager.auth.createUserWithEmailAndPassword(edtTk.getText().toString(), edtMk.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                String uuid = authResult.getUser().getUid();
                                Shipper shipper = new Shipper(uuid, "", edtTk.getText().toString(), edtMk.getText().toString(), edtTen.getText().toString(), edtDiaChi.getText().toString(), "", edtNgaysinh.getText().toString(), edtMaxe.getText().toString(), edtSdt.getText().toString(), TrangThaiShipper.KhongHoatDong, "H??? th???ng");
                                firebase_manager.Ghi_Shipper(shipper).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                        kAlertDialog.setContentText("???? t???o t??i kho???n th??nh c??ng");
                                        firebase_manager.Up2MatCMND(cmndTrc, cmndSau, uuid);
                                        firebase_manager.UpAvatar(avaTar, uuid);
                                        firebase_manager.auth.signOut();
                                        firebase_manager.auth.signInWithEmailAndPassword(email,storePassword.getPassword());
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
                    } else {
                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setContentText("???? l??u th??nh c??ng");
                        Shipper temp = new Shipper(shipper.getiDShipper(), "", edtTk.getText().toString(), edtMk.getText().toString(), edtTen.getText().toString(), edtDiaChi.getText().toString(), "", edtNgaysinh.getText().toString(), edtMaxe.getText().toString(), edtSdt.getText().toString(), TrangThaiShipper.KhongHoatDong, "H??? th???ng");
                        firebase_manager.Ghi_Shipper(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                if (cmndTrc != null && cmndSau != null) {
                                    firebase_manager.Up2MatCMND(cmndTrc, cmndSau, shipper.getiDShipper());
                                }
                                if (avaTar != null) {
                                    firebase_manager.UpAvatar(avaTar, shipper.getiDShipper());
                                }

                            }
                        });
                    }
                }
            case android.R.id.home:
                getActivity().finish();
                return true;
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
                Alerter.create(getActivity())
                        .setText("Vui l??ng t???i l??n 2 m???t CMND/CCCD")
                        .setTitle("Th??ng b??o")
                        .setIcon(R.drawable.ic_error)
                        .setDuration(3000)
                        .show();
                result = false;
            }
            if (avaTar == null) {
                Alerter.create(getActivity())
                        .setText("Vui l??ng t???i ???nh c?? nh??n")
                        .setTitle("Th??ng b??o")
                        .setIcon(R.drawable.ic_error)
                        .setDuration(3000)
                        .show();
                result = false;
            }
        }
        return result;
    }

    private void LoadButton() {
        if (shipper.getTrangThaiShipper() == TrangThaiShipper.BiKhoa) {
            btnGoKhoa.setVisibility(View.VISIBLE);
            btnKhoa.setVisibility(View.GONE);
        }
        if (shipper.getTrangThaiShipper() == TrangThaiShipper.KhongHoatDong) {
            btnKhoa.setVisibility(View.VISIBLE);
            btnGoKhoa.setVisibility(View.GONE);
        }
    }


}