package com.example.personalfinance.ui.home;

import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.personalfinance.MainActivity;
import com.example.personalfinance.models.Money;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ArrayList<Money> moneyList;



    public HomeViewModel() {
        if(HomeDatabase.moneyList == null || HomeDatabase.moneyList.size()==0)
            getData();
    }
    public void getData(){
        HomeDatabase.moneyList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Money");
        query.whereEqualTo("username","a");
        List<ParseObject> result = null;
        try {
            result = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        for (ParseObject data: result) {
                        HomeDatabase.moneyList.add(
                                new Money(
                                        data.getDouble("amount"),
                                        data.getDate("date"),
                                        data.getString("title"),
                                        data.getString("type"),
                                        data.getObjectId()
                                )
                        );
                    }
    }
}