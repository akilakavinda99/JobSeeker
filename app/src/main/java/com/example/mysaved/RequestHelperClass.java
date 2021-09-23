package com.example.mysaved;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RequestHelperClass {

    String name,title,c_age1,description,email1,phone1,gender;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    public RequestHelperClass() {
    }

    public RequestHelperClass(String name, String title, String c_age1, String description, String email1, String phone1, String gender, String date) {
        this.name = name;
        this.title = title;
        this.c_age1 = c_age1;
        this.description = description;
        this.email1 = email1;
        this.phone1 = phone1;
        this.gender = gender;
        this.date = date;
    }

    //Getter & setters


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

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
