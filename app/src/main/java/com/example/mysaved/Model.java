package com.example.mysaved;

import com.google.firebase.database.Exclude;

public class Model {

    @Exclude
    String JobID;

    String name,title,job_type,img,salary1,phone1,district;

    //Default constructor
    public Model() {
    }

    //Overload Constructor
    public Model(String jobID, String name, String title, String job_type, String img, String salary1, String phone1, String district) {
        JobID = jobID;
        this.name = name;
        this.title = title;
        this.job_type = job_type;
        this.img = img;
        this.salary1 = salary1;
        this.phone1 = phone1;
        this.district = district;
    }

    public String getJobID() {
        return JobID;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getJob_type() {
        return job_type;
    }

    public String getImg() {
        return img;
    }

    public String getSalary1() {
        return salary1;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getDistrict() {
        return district;
    }

    @Override
    public String toString() {
        return "Model{" +
                "JobID='" + JobID + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", job_type='" + job_type + '\'' +
                ", img='" + img + '\'' +
                ", salary1='" + salary1 + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}