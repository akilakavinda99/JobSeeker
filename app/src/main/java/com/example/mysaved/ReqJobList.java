package com.example.mysaved;

import com.google.firebase.database.Exclude;

public class ReqJobList {
    @Exclude
    String reqid;

    @Exclude
    String reqjobid;

    String date,name,title,phone1,c_age1,description,email1,gender,img;

    public ReqJobList() {
    }

    public ReqJobList(String reqid, String reqjobid, String date, String name, String title, String phone1, String c_age1, String description, String email1, String gender, String img) {
        this.reqid = reqid;
        this.reqjobid = reqjobid;
        this.date = date;
        this.name = name;
        this.title = title;
        this.phone1 = phone1;
        this.c_age1 = c_age1;
        this.description = description;
        this.email1 = email1;
        this.gender = gender;
        this.img = img;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public String getReqjobid() {
        return reqjobid;
    }

    public void setReqjobid(String reqjobid) {
        this.reqjobid = reqjobid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getC_age1() {
        return c_age1;
    }

    public void setC_age1(String c_age1) {
        this.c_age1 = c_age1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "ReqJobList{" +
                "reqid='" + reqid + '\'' +
                ", reqjobid='" + reqjobid + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", c_age1='" + c_age1 + '\'' +
                ", description='" + description + '\'' +
                ", email1='" + email1 + '\'' +
                ", gender='" + gender + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
