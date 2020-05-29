package com.example.personalfinance.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.personalfinance.R;
import com.example.personalfinance.models.Money;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeItemDetailFragment.isItemUpdated{

    private HomeViewModel homeViewModel;
    private HomeGridAdapter homeGridAdapter;
    private GridView gv;
    private ProgressBar progressBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        homeViewModel.init();
        homeViewModel.getMoneyList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Money>>() {
            @Override
            public void onChanged(ArrayList<Money> monies) {
                homeGridAdapter = new HomeGridAdapter(getContext(), homeViewModel.getMoneyList().getValue());
                gv.setAdapter(homeGridAdapter);
            }
        });

        gv = root.findViewById(R.id.homeGrid);
        progressBar = root.findViewById(R.id.progressBar);
//        homeGridAdapter = new HomeGridAdapter(getContext(), homeViewModel.getMoneyList().getValue());
//        gv.setAdapter(homeGridAdapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeItemDetailFragment fragment = new HomeItemDetailFragment();
                fragment.setTargetFragment(HomeFragment.this,1);
                Bundle bundle = new Bundle();
                bundle.putString("itemId", homeViewModel.getMoneyList().getValue().get(position).getId());
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "SETUP");
            }
        });

        return root;
    }

    @Override
    public void sendResult(Boolean result) {
        if(result){
            homeViewModel.refreshData();
        }
    }
}
