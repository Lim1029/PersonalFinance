package com.example.personalfinance.ui.home;

import android.app.Application;
import android.content.Context;
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

    private MutableLiveData<ArrayList<Money>> mMoneyList;

    void init(){
        if(mMoneyList != null){
            return;
        }
        mMoneyList = HomeDataManager.getMoneyList();
    }

    LiveData<ArrayList<Money>> getMoneyList(){
        return mMoneyList;
    }

    void refreshData(){
        mMoneyList.postValue(HomeDataManager.getAndReturnData());
    }

    public HomeViewModel() {

    }

}