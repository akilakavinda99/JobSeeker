package com.example.mysaved;

public class HomeList {

    String title,job_type,district,img;


    public HomeList() {
    }

    public HomeList(String title, String job_type, String district, String img) {
        this.title = title;
        this.job_type = job_type;
        this.district = district;
        this.img = img;
    }



    public String getTitle() {
        return title;
    }

    public String getJob_type() {
        return job_type;
    }

    public String getDistrict() {
        return district;
    }

    public String getImg() {
        return img;
    }
}
