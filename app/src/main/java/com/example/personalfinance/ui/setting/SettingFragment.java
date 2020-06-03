package com.example.personalfinance.ui.setting;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.personalfinance.MainActivity;
import com.example.personalfinance.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class SettingFragment extends Fragment implements View.OnClickListener{

    private SettingViewModel settingViewModel;
    private Button btnChgUsername,btnChgPassword,btnChgEmail,btnChgTheme;
    private String mode;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);
//        settingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        initialiseAllItems(root);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initialiseAllItems(View view) {
        btnChgUsername = view.findViewById(R.id.btnChangeUsername);
        btnChgPassword = view.findViewById(R.id.btnChangePassword);
        btnChgEmail = view.findViewById(R.id.btnChangeEmail);
        btnChgTheme = view.findViewById(R.id.btnChangeTheme);

        btnChgUsername.setOnClickListener(this);
        btnChgPassword.setOnClickListener(this);
        btnChgEmail.setOnClickListener(this);
        btnChgTheme.setOnClickListener(this);
    }

    @Override
    public void onClick(final View btnView) {
        Intent intent = new Intent(getActivity(), SettingPopupMenu.class);

        switch (btnView.getId()) {
            case R.id.btnChangeEmail:
                intent.putExtra("mode", "changeEmail");
                startActivity(intent);
                break;

            case R.id.btnChangeUsername:
                intent.putExtra("mode", "changeUsername");
                startActivity(intent);
                break;

            case R.id.btnChangePassword:
                intent.putExtra("mode", "changePassword");
                startActivity(intent);
                break;

            case R.id.btnChangeTheme:
                intent.putExtra("mode", "changeTheme");
                startActivity(intent);
                break;


        }
    }

}
