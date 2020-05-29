package com.example.personalfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalfinance.ui.home.HomeDataManager;
import com.example.personalfinance.ui.home.HomeDatabase;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Signup_Login extends AppCompatActivity {

    private State state;
    private Button btnSignUpLogIn;
    private EditText edtEmail,edtUsername,edtPassword;

    enum State{
        SIGNUP, LOGIN
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        ParseInstallation.getCurrentInstallation().saveInBackground();
        state = State.SIGNUP;

        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUpLogIn = findViewById(R.id.btnSignUpLogIn);

        btnSignUpLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == State.SIGNUP) {
                    if(!edtEmail.getText().toString().isEmpty() && !edtUsername.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty()) {
                        final ParseUser appUser = new ParseUser();
                        appUser.setEmail(edtEmail.getText().toString());
                        appUser.setUsername(edtUsername.getText().toString());
                        appUser.setPassword(edtPassword.getText().toString());
                        appUser.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(Signup_Login.this, "You have signed up", Toast.LENGTH_SHORT).show();
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
                    if(!edtUsername.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty()) {
                        ParseUser.logInInBackground(edtUsername.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null && e == null) {
                                    Toast.makeText(Signup_Login.this, "You have logged in", Toast.LENGTH_SHORT).show();
                                    transitionToHomeActivity();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Signup_Login.this, "You should fill in Username and Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.switch_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.switchSignupLoginItem:
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void transitionToHomeActivity() {
        if(ParseUser.getCurrentUser() != null) {
            HomeDataManager.moneyList = null;
            Intent intent = new Intent(Signup_Login.this, MainActivity.class);
            startActivity(intent);

        }
    }
}
