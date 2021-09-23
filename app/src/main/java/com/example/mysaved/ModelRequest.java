package com.example.mysaved;

import com.google.firebase.database.Exclude;

public class ModelRequest {

    @Exclude
    String ReqId;

    String name,title,c_age1,img,gender,phone1,email1;

    public ModelRequest() {
    }

    public ModelRequest(String reqId, String name, String title, String c_age1, String img, String gender, String phone1, String email1) {
        ReqId = reqId;
        this.name = name;
        this.title = title;
        this.c_age1 = c_age1;
        this.img = img;
        this.gender = gender;
        this.phone1 = phone1;
        this.email1 = email1;
    }

    public String getReqId() {
        return ReqId;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getC_age1() {
        return c_age1;
    }

    public String getImg() {
        return img;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getEmail1() {
        return email1;
    }

    @Override
    public String toString() {
        return "ModelRequest{" +
                "ReqId='" + ReqId + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", c_age1='" + c_age1 + '\'' +
                ", img='" + img + '\'' +
                ", gender='" + gender + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", email1='" + email1 + '\'' +
                '}';
    }
}
