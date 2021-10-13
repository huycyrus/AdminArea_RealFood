package com.example.adminarea_realfood;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Firebase_Manager {
    DatabaseReference mDatabase ;
    StorageReference storageRef ;

    public Firebase_Manager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
    }


}
