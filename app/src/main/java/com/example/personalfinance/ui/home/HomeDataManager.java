package com.example.personalfinance.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.example.personalfinance.models.Money;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeDataManager {
    public static ArrayList<Money> moneyList;

    static MutableLiveData<ArrayList<Money>> getMoneyList(){

            getData();

        MutableLiveData<ArrayList<Money>> data = new MutableLiveData<>();
        data.setValue(moneyList);
        return data;
    }

    private static void getData(){
        moneyList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Money");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if(e==null){
//                    for (ParseObject data: objects) {
//                        moneyList.add(
//                                new Money(
//                                        data.getDouble("amount"),
//                                        data.getString("date"),
//                                        data.getString("title"),
//                                        data.getString("type"),
//                                        data.getObjectId()
//                                )
//                        );
//                    }
//                }
//            }
//        });
        List<ParseObject> results = null;
        try {
            results = query.find();
            for (ParseObject data: results) {
                moneyList.add(
                        new Money(
                                data.getDouble("amount"),
                                data.getString("date"),
                                data.getString("title"),
                                data.getString("type"),
                                data.getObjectId()
                        )
                );
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public static ArrayList<Money> getAndReturnData(){
        moneyList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Money");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if(e==null){
//                    for (ParseObject data: objects) {
//                        moneyList.add(
//                                new Money(
//                                        data.getDouble("amount"),
//                                        data.getString("date"),
//                                        data.getString("title"),
//                                        data.getString("type"),
//                                        data.getObjectId()
//                                )
//                        );
//                    }
//                }
//            }
//        });
        List<ParseObject> results = null;
        try {
            results = query.find();
            for (ParseObject data: results) {
                moneyList.add(
                        new Money(
                                data.getDouble("amount"),
                                data.getString("date"),
                                data.getString("title"),
                                data.getString("type"),
                                data.getObjectId()
                        )
                );
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        moneyList.add(new Money(1,"a","a","spending","a"));
        return moneyList;
    }
}
