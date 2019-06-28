package com.example.vehicledamageclassification.Model;

import com.google.gson.annotations.SerializedName;

public class sendDetails {


    @SerializedName("image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public sendDetails() {

    }

    public sendDetails(String image) {
        this.image = image;
    }
}
