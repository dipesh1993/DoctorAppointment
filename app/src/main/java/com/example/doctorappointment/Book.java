package com.example.doctorappointment;

import java.sql.Time;

public class Book {
    private String week,payment_status;
    private String time;
    private String date;
    Integer id;


    public Book(String week,String time,String date,Integer id,String payment_status)
    {
        this.week = week;
        this.time=time;
        this.date=date;
        this.id=id;
        this.payment_status=payment_status;
    }


    public String getStatus() {
        return week;
    }

    public String getTime1() {
        return time;
    }
    public String getDate1() {
        return date;
    }
    public Integer getID() {
        return id;
    }
    public String getPayment_status() {
        return payment_status;
    }
}