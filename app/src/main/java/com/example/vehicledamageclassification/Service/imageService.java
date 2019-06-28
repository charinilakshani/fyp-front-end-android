package com.example.vehicledamageclassification.Service;

import com.example.vehicledamageclassification.Model.sendDetails;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface imageService {

    @POST("hello")
    Call<JSONObject> CreateUser(@Body sendDetails user);
}
