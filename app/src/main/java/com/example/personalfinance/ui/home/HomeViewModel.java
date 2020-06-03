package com.example.personalfinance.ui.home;

import android.app.Application;
import android.content.Context;
import android.os.CountDownTimer;
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
import java.util.Timer;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Money>> mMoneyList;

    void init(){
        if(mMoneyList != null){
            return;
        }
        mMoneyList = new MutableLiveData<>();
//        mMoneyList = HomeDataManager.getMoneyList();
        getData();
    }

    LiveData<ArrayList<Money>> getMoneyList(){
        return mMoneyList;
    }

    void refreshData(){
        getData();
    }

    public HomeViewModel() {

    }
    private void getData(){
        final ArrayList<Money> moneyList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Money");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    for (ParseObject data: objects) {
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
                    mMoneyList.setValue(moneyList);
                }
            }
        });
    }
}