package com.example.personalfinance.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.personalfinance.R;
import com.example.personalfinance.models.Money;
import com.parse.ParseException;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final GridView gv = root.findViewById(R.id.homeGrid);
        gv.setAdapter(new HomeGridAdapter(getContext(),HomeDatabase.moneyList));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeItemDetailFragment fragment = new HomeItemDetailFragment();
                Bundle bundle=new Bundle();
                bundle.putString("itemId", HomeDatabase.moneyList.get(position).getId());
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(),"SETUP");
            }
        });
        return root;


    }
}
