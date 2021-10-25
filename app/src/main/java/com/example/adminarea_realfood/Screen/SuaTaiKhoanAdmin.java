package com.example.adminarea_realfood.Screen;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.Admin;
import com.example.adminarea_realfood.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuaTaiKhoanAdmin extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ArrayList<Admin> admins = new ArrayList<>();
    CircleImageView civImage;
    EditText edtEmail, edtHoTen, edtNgaySinh, edtSdt;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    Uri avatarAdmin;
    Admin admin;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suataikhoanadmin_activity);
        setControl();
        edtEmail.setText(firebase_manager.auth.getCurrentUser().getEmail());
        setEvent();
        LoadData();
    }

    private void LoadData() {
        storageReference.child("Admin").child("AvatarAdmin").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplication())
                        .load(uri.toString())
                        .into(civImage);
            }
        });
        firebase_manager.mDatabase.child("Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                admin = snapshot.getValue(Admin.class);
                edtHoTen.setText(admin.getHoTen());
                edtNgaySinh.setText(admin.getNgaySinh());
                edtSdt.setText(admin.getsDT());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void setEvent() {
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPickerDailog();
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
                                avatarAdmin = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(SuaTaiKhoanAdmin.this);
            }
        });
    }

    private void setControl() {
        civImage = findViewById(R.id.edit_image_admin);
        edtEmail = findViewById(R.id.edt_email_admin);
        edtHoTen = findViewById(R.id.edt_hoten_admin);
        edtNgaySinh = findViewById(R.id.edt_ngaysinh_admin);
        edtSdt = findViewById(R.id.edt_sdt_admin);

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
        edtNgaySinh.setText(date);
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
        switch (item.getItemId()){
            case R.id.action_Save:
                if(avatarAdmin != null){
                    firebase_manager.UpAvatarAdmin(avatarAdmin);
                }
                KAlertDialog kAlertDialog = new KAlertDialog(SuaTaiKhoanAdmin.this, KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                kAlertDialog.show();

                Admin admin = new Admin(  edtHoTen.getText().toString(), edtEmail.getText().toString(), edtSdt.getText().toString(), edtNgaySinh.getText().toString());
                firebase_manager.Ghi_Admin(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setContentText("Đã lưu thành công");
                    }
                });
        }


        return super.onOptionsItemSelected(item);
    }
}