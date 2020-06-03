package com.example.personalfinance;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalfinance.ui.home.HomeDatabase;
import com.example.personalfinance.ui.home.HomeViewModel;
import com.example.personalfinance.ui.setting.SettingFragment;
import com.example.personalfinance.ui.setting.SettingPopupMenu;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Signup_Login extends AppCompatActivity implements View.OnClickListener{

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_EMAIL = "Email";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";
    private final String DefaultUnameValue = "";
    private final String DefaultPasswordValue = "";
    private final String DefaultEmailValue = "";
    private String email,username,password;
    private State state;
    private Button btnSignUpLogIn;
    private EditText edtEmail,edtUsername,edtPassword;
    private CheckBox cbxRememberMe;
    private boolean rmbMe;

    enum State{
        SIGNUP, LOGIN
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        ParseInstallation.getCurrentInstallation().saveInBackground();
        state = State.SIGNUP;
        rmbMe = false;
        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUpLogIn = findViewById(R.id.btnSignUpLogIn);


        cbxRememberMe = findViewById(R.id.checkBoxRememberMe);
        cbxRememberMe.setOnClickListener(this);

//        if(ParseUser.getCurrentUser() != null){
//            password = edtPassword.getText().toString();
//            SettingPopupMenu.setPasswordSettingPopup(password);
//            Log.i("TAG", "Password: " + password);
//            transitionToHomeActivity();
//        }

        btnSignUpLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View btnView) {
        switch (btnView.getId()) {
            case R.id.btnSignUpLogIn:
                final ProgressDialog progressDialog = new ProgressDialog(Signup_Login.this);

                if (state == State.SIGNUP) {
                    progressDialog.setMessage("Signing up...");
                    progressDialog.show();

                    if(!edtEmail.getText().toString().isEmpty() && !edtUsername.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty()) {
                        final ParseUser appUser = new ParseUser();
                        appUser.setEmail(edtEmail.getText().toString());
                        appUser.setUsername(edtUsername.getText().toString());
                        appUser.setPassword(edtPassword.getText().toString());
                        appUser.signUpInBackground(new SignUpCallback() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    //Credential credential = new Credential().getUserCredential().setPassword(edtPassword.getText().toString());//builder(edtEmail.getText().toString()).setPassword(edtPassword.getText().toString()).build();
                                    Toast.makeText(Signup_Login.this, "You have signed up", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    transitionToHomeActivity();
                                } else {
                                    Toast.makeText(Signup_Login.this, "Unknown errors happened", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Signup_Login.this, "You should fill in Email, Username and Password", Toast.LENGTH_SHORT).show();
                    }
                } else if (state == State.LOGIN) {

                    progressDialog.setMessage("Logging in...");
                    progressDialog.show();

                    if(!edtUsername.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty()) {
                        ParseUser.logInInBackground(edtUsername.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null && e == null) {
                                    Toast.makeText(Signup_Login.this, "You have logged in", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    transitionToHomeActivity();
                                } else {
                                    Toast.makeText(Signup_Login.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Signup_Login.this, "You should fill in Username and Password", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.switch_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.switchSignupLoginItem){
            if(state == State.SIGNUP){

                state = State.LOGIN;
                item.setTitle("Sign Up");
                btnSignUpLogIn.setText("Log In");
                edtEmail.setVisibility(View.GONE);

            } else if(state == State.LOGIN){

                state = State.SIGNUP;
                item.setTitle("Log In");
                btnSignUpLogIn.setText("Sign Up");
                edtEmail.setVisibility(View.VISIBLE);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void transitionToHomeActivity() {
        if(ParseUser.getCurrentUser() != null) {

            SettingPopupMenu.setEmailSettingPopup(email);
            SettingPopupMenu.setUsernameSettingPopupSettingPopup(username);
            SettingPopupMenu.setPasswordSettingPopup(password);
            Intent intent = new Intent(Signup_Login.this, MainActivity.class);
            startActivity(intent);
            HomeDatabase.moneyList = null;

//            Bundle bundle = new Bundle();
//            bundle.putString("password", password);
//            SettingFragment fragInfo = new SettingFragment();
//            fragInfo.setArguments(bundle);
//            SettingFragment.

            //transaction.replace(R.id.fragment_single, fragInfo);
            //transaction.commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        email = edtEmail.getText().toString();
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();
        editor.putString(PREF_EMAIL, email);
        editor.putString(PREF_UNAME, username);
        editor.putString(PREF_PASSWORD, password);
        editor.apply();
    }

    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        username = settings.getString(PREF_UNAME, DefaultUnameValue);
        password = settings.getString(PREF_PASSWORD, DefaultPasswordValue);
        if(cbxRememberMe.isChecked()) {
            edtEmail.setText(email);
            edtUsername.setText(username);
            edtPassword.setText(password);
        }
    }
}
