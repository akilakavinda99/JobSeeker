package com.example.mysaved;

import com.google.firebase.database.Exclude;

public class HomeList {
    @Exclude
    String id;
    @Exclude
    String jobid;

    String date,name,title,salary1,job_type,description,email1,phone1,district,img;


    public HomeList() {
    }

    public HomeList(String id, String jobid, String date, String name, String title, String salary1, String job_type, String description, String email1, String phone1, String district, String img) {
        this.id = id;
        this.jobid = jobid;
        this.date = date;
        this.name = name;
        this.title = title;
        this.salary1 = salary1;
        this.job_type = job_type;
        this.description = description;
        this.email1 = email1;
        this.phone1 = phone1;
        this.district = district;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
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

    public String getSalary1() {
        return salary1;
    }

    public void setSalary1(String salary1) {
        this.salary1 = salary1;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "HomeList{" +
                "id='" + id + '\'' +
                ", jobid='" + jobid + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", salary1='" + salary1 + '\'' +
                ", job_type='" + job_type + '\'' +
                ", description='" + description + '\'' +
                ", email1='" + email1 + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", district='" + district + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
