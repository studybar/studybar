package com.wedo.studybar.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wedo.studybar.R;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ForgetPassword extends AppCompatActivity {

    private TextInputEditText verificationCodeEditText;
    private MaterialButton buttonGet;
    private TextInputEditText passwordNewEditText;
    private TextInputEditText passwordConfirmationEditText;
    private MaterialButton buttonCancel;
    private MaterialButton buttonOk;

    private String verificationCode;
    private String passwordNew;
    private String passwordConfirm;

    private Boolean isGetClicked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        verificationCodeEditText = findViewById(R.id.forget_password_verification);
        buttonGet = findViewById(R.id.forget_password_get);
        passwordNewEditText = findViewById(R.id.forget_password_new);
        passwordConfirmationEditText = findViewById(R.id.forget_password_new_confirm);
        buttonCancel = findViewById(R.id.forget_password_cancel);
        buttonOk = findViewById(R.id.forget_password_ok);

        verificationCodeEditText.setFocusable(false);
        verificationCodeEditText.setClickable(true);
        passwordNewEditText.setFocusable(false);
        passwordNewEditText.setClickable(true);
        passwordConfirmationEditText.setFocusable(false);
        passwordConfirmationEditText.setClickable(true);

        verificationCodeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGetClicked){
                    Toast.makeText(getApplicationContext(),R.string.wait_for_send,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),R.string.click_get_code,Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGetClicked = true;

            }
        });

        passwordNewEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationCode = verificationCodeEditText.getText().toString();
                if (verificationCode!=null&&!verificationCode.matches("")){
                    passwordNewEditText.setFocusableInTouchMode(true);
                    passwordNewEditText.requestFocus();
                    passwordNewEditText.setClickable(false);
                }else {

                }
            }
        });

        passwordConfirmationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordNew = passwordNewEditText.getText().toString();
                if (passwordNew!=null && !passwordNew.equals("")){
                    passwordConfirmationEditText.setFocusableInTouchMode(true);
                    passwordConfirmationEditText.requestFocus();
                    passwordConfirmationEditText.setClickable(false);
                }else {

                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordConfirm = passwordConfirmationEditText.getText().toString();
                if (passwordConfirm.equals(passwordNew)){
                    try {
                        JSONObject base = new JSONObject();
                        base.put("password",passwordNew);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),R.string.check_your_intput,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
