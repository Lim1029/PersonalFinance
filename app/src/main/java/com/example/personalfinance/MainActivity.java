package com.example.personalfinance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_add;
    private Spinner spinner_add;
    private TextView borrowDate;
    private EditText edtName;
    private String spinnerSelected, date1, date2="";
    private String[] nameType = {"Earning", "Spending", "Lending", "Borrowing"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        Toast.makeText(this, ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();

        ParseInstallation.getCurrentInstallation().saveInBackground();
        // Passing each menu ID as a set o
        // f Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_spending, R.id.navigation_earning, R.id.navigation_lending, R.id.navigation_setting)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add){
            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = inflater.inflate(R.layout.add_popup_menu, null);

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            spinner_add = popupView.findViewById(R.id.add_type);
            addItemSpinner();

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            spinner_add.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    spinnerSelected = nameType[pos];
                    if(position == 2 || position == 3){
                        borrowDate.setVisibility(View.VISIBLE);
                        edtName.setVisibility(View.VISIBLE);
                    }
                    else{
                        borrowDate.setVisibility(View.GONE);
                        edtName.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });

            final TextView edtDate = popupView.findViewById(R.id.add_edtDate);
            borrowDate = popupView.findViewById(R.id.add_borrowDate);
            edtName = popupView.findViewById(R.id.add_edtBorrowName);

            edtDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    DatePickerDialog picker = new DatePickerDialog(MainActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    date1 = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                    edtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    picker.show();
                }
            });

            borrowDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    DatePickerDialog picker = new DatePickerDialog(MainActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    date2 = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                    borrowDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    picker.show();
                }
            });

            final Button btnSave = popupView.findViewById(R.id.add_btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText edtTitle, edtRemark, edtAmount, edtMoneyType;
                    String title, remark, moneyType;
                    double amount;

                    edtTitle = popupView.findViewById(R.id.add_edtTitle);
                    edtRemark = popupView.findViewById(R.id.add_edtRemark);
                    edtAmount = popupView.findViewById(R.id.add_edtAmount);
                    edtMoneyType = popupView.findViewById(R.id.add_edtMoneyType);

                    title = edtTitle.getText().toString();
                    remark = edtRemark.getText().toString();
                    amount = Double.parseDouble(edtAmount.getText().toString());
                    moneyType = edtMoneyType.getText().toString();

                    Toast.makeText(MainActivity.this,
                            title + "\n" + spinnerSelected + "\n" + remark + "\n" + moneyType + "\n" + amount + "\n" + date1 + "\n" + date2
                            , Toast.LENGTH_SHORT).show();

                    ParseObject parseObject = new ParseObject("Money");
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                    parseObject.put("amount", amount);
                    parseObject.put("title", title);
                    parseObject.put("type", spinnerSelected);
                    parseObject.put("remarks", remark);
                    parseObject.put("moneyType", moneyType);
                    parseObject.put("date", date1);

                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                Toast.makeText(MainActivity.this, "Save successfully", Toast.LENGTH_SHORT).show();
                                popupWindow.dismiss();
                            }
                            else{
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.userLogoutItem) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null) {
                        Toast.makeText(MainActivity.this,"You have logged out successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
            Intent intent = new Intent(MainActivity.this, Signup_Login.class);
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addItemSpinner(){
        int[] imgType = {R.drawable.spending, R.drawable.spending, R.drawable.borrowing, R.drawable.borrowing};
        CustomAdapter dataAdapter = new CustomAdapter(this, imgType, nameType);
        spinner_add.setAdapter(dataAdapter);
    }

}
