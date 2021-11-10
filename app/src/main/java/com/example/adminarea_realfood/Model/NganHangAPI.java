package com.example.adminarea_realfood.Model;


import retrofit2.Call;
import retrofit2.http.GET;

public interface NganHangAPI {
    String BASE_URL = "https://api.vietqr.io/";
    @GET("v1/banks/")
    Call<NganHangWrapper<NganHang>> getNganHang();
}
