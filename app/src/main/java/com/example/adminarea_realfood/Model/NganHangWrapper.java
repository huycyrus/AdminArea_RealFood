package com.example.adminarea_realfood.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NganHangWrapper <T>{
    @SerializedName("data")
    public ArrayList<T> items;
}
