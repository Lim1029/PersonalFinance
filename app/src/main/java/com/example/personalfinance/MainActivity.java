package com.example.personalfinance;

import android.content.Intent;
import android.os.Bundle;
<<<<<<<<< Temporary merge branch 1
import android.view.View;
import android.widget.Button;
=========
import android.view.Menu;
import android.view.MenuItem;
>>>>>>>>> Temporary merge branch 2
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_spending, R.id.navigation_earning, R.id.navigation_lending, R.id.navigation_setting)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

<<<<<<<<< Temporary merge branch 1
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);

=========
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
>>>>>>>>> Temporary merge branch 2

        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
<<<<<<<<< Temporary merge branch 1
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add){
            Toast.makeText(this, "Button is pressed", Toast.LENGTH_SHORT).show();
        }
=========
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
>>>>>>>>> Temporary merge branch 2
    }
}
