package com.example.personalfinance.ui.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalfinance.R;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SettingPopupMenu extends AppCompatActivity implements View.OnClickListener{

    //private EditText edtUsername, edtPassword, edtEmail;
    private Button btnConfirmChange;
    //private String emailSettingPopup, usernameSettingPopup, passwordSettingPopup;
    private static String passwordSettingPopup, usernameSettingPopup, emailSettingPopup;
    private static String modeSetting;
    private TextView txtChangeItem, txtOldItem, txtNewItem, txtConfirmNewItem;
    private EditText edtOldItem, edtNewItem, edtConfirmNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_popup_menu);

        btnConfirmChange = findViewById(R.id.btnConfirmChange);
        btnConfirmChange.setOnClickListener(SettingPopupMenu.this);

        initialiseItems();
        modeSetting = getIntent().getStringExtra("mode");
        changeText(modeSetting);

    }

    void initialiseItems() {
        txtChangeItem = findViewById(R.id.txtChangeItem);
        txtOldItem = findViewById(R.id.txtOldItem);
        txtNewItem = findViewById(R.id.txtNewItem);
        txtConfirmNewItem = findViewById(R.id.txtConfirnNewItem);

        edtOldItem = findViewById(R.id.edtOldPassword);
        edtNewItem = findViewById(R.id.edtNewPassword);
        edtConfirmNewItem = findViewById(R.id.edtConfrimNewPassword);
    }

    @Override
    public void onClick(View btnView) {
        if (btnView.getId() == R.id.btnConfirmChange) {

            final ParseUser parseUser = ParseUser.getCurrentUser();
            //ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
            //parseQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

                if (edtOldItem.getText().toString().equals(passwordSettingPopup) &&
                        edtNewItem.getText().toString().equals(edtConfirmNewItem.getText().toString()) &&
                        edtNewItem.getText().toString().length() > 0 && edtConfirmNewItem.getText().toString().length() > 0) {

                    if(modeSetting.equals("changePassword")) parseUser.setPassword(edtNewItem.getText().toString());
                    else if(modeSetting.equals("changeUsername")) parseUser.setUsername(edtNewItem.getText().toString());
                    else if(modeSetting.equals("changeEmail")) parseUser.setEmail(edtNewItem.getText().toString());
                    parseUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                edtOldItem.getText().clear();
                                edtNewItem.getText().clear();
                                edtConfirmNewItem.getText().clear();

                                if(modeSetting.equals("changePassword")) {
                                    Toast.makeText(SettingPopupMenu.this, "Your password has been updated successfully", Toast.LENGTH_SHORT).show();
                                } else if(modeSetting.equals("changeUsername")) {
                                    Toast.makeText(SettingPopupMenu.this, "Your username has been updated successfully", Toast.LENGTH_SHORT).show();
                                } else if(modeSetting.equals("changeEmail")) {
                                    Toast.makeText(SettingPopupMenu.this, "Your password has been changed successfully", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(SettingPopupMenu.this, "Unknown error happened...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SettingPopupMenu.this, "All password should be entered correctly", Toast.LENGTH_SHORT).show();
                }
        }
    }


    public void changeText(String modeSetting) {
        if(modeSetting.equals("changeEmail")) {

            txtChangeItem.setText(R.string.title_change_email);
            txtOldItem.setText(R.string.text_old_email);
            txtNewItem.setText(R.string.text_new_email);
            txtConfirmNewItem.setText(R.string.text_confirm_new_email);

            edtOldItem.setHint(R.string.edt_old_email);
            edtNewItem.setHint(R.string.edt_new_email);
            edtConfirmNewItem.setHint(R.string.edt_confirm_new_email);

        } else if(modeSetting.equals("changeUsername")) {

            txtChangeItem.setText(R.string.title_change_username);
            txtOldItem.setText(R.string.text_old_username);
            txtNewItem.setText(R.string.text_new_username);
            txtConfirmNewItem.setText(R.string.text_confirm_new_username);

            edtOldItem.setHint(R.string.edt_old_username);
            edtNewItem.setHint(R.string.edt_new_username);
            edtConfirmNewItem.setHint(R.string.edt_confirm_new_username);

        } else if(modeSetting.equals("changePassword")) {

            txtChangeItem.setText(R.string.title_change_password);
            txtOldItem.setText(R.string.text_old_password);
            txtNewItem.setText(R.string.text_new_password);
            txtConfirmNewItem.setText(R.string.text_confirm_new_password);

            edtOldItem.setHint(R.string.edt_old_password);
            edtNewItem.setHint(R.string.edt_new_password);
            edtConfirmNewItem.setHint(R.string.edt_confirm_new_password);
        }
    }

    public static void setPasswordSettingPopup(String password) {
        passwordSettingPopup = password;
    }

    public static void setEmailSettingPopup(String email) {
        emailSettingPopup = email;
    }

    public static void setUsernameSettingPopupSettingPopup(String username) {
        usernameSettingPopup = username;
    }

}
