package com.example.hantera_studenter_v1;


public class Student {
    private int id;
    private String namn;
    private String course;

    public Student(int id, String namn, String course){
        this.id = id;
        this.namn = namn;
        this.course = course;
    }


    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.namn;
    }

    public String getCourse(){
        return this.course;
    }
}