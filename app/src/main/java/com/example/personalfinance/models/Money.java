package com.example.personalfinance.models;

import java.util.Date;

public class Money {
    private double Amount;
    private String Date;
    private String Title;
    private String Type;
    private String Id;

    public Money(double amount, String date, String title, String type, String id) {
        Amount = amount;
        Date = date;
        Title = title;
        Type = type;
        Id = id;
    }

    public double getAmount() {
        return Amount;
    }
    public String getDate() {
        return Date;
    }
    public String getTitle() {
        return Title;
    }
    public String getType() {
        return Type;
    }

    public String getId() {
        return Id;
    }
}
