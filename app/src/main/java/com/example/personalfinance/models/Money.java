package com.example.personalfinance.models;

import java.util.Date;

public class Money {
    private float Amount;
    private Date Date;
    private String Title;
    private String Type;

    public Money(float amount, Date date, String title, String type) {
        Amount = amount;
        Date = date;
        Title = title;
        Type = type;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date.getDate()+"/"+Date.getMonth()+"/"+Date.getYear();
    }

    public void setDate(Date date) {
        Date = date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return Type;
    }
//
//    public void setType(String type) {
//        Type = type;
//    }
}
