package com.ats.hreasy.model;

public class EmpListTemp {
int img;
String name;
String designation;

    public EmpListTemp(int img, String name, String designation) {
        this.img = img;
        this.name = name;
        this.designation = designation;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "EmpListTemp{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
