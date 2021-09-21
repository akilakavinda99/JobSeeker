package com.example.mysaved;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JobHelperClass {

    String name,title,salary1,description,email1,phone1,job_type,district;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    public JobHelperClass() {
    }

    public JobHelperClass(String name, String title, String salary1, String description, String email1, String phone1, String job_type, String district, String date) {
        this.name = name;
        this.title = title;
        this.salary1 = salary1;
        this.description = description;
        this.email1 = email1;
        this.phone1 = phone1;
        this.job_type = job_type;
        this.district = district;
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

    public String getSalary1() {
        return salary1;
    }

    public void setSalary1(String salary1) {
        this.salary1 = salary1;
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

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
