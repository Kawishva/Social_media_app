package com.example.Social_media_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginView extends AppCompatActivity {

    EditText login_email,login_password,email_back,password_back;

    ProgressBar progressBar;
    TextView registerbtn,if_text,reset_password_button;
    Button loginbtn,login_bump_btn;
    FirebaseAuth mAuth;

    CheckBox checkbox;
    SharedPreferences preferences;

    int attemps=0;

    String email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        mAuth = FirebaseAuth.getInstance();
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        loginbtn  = findViewById(R.id.login_button);
        registerbtn = findViewById(R.id.login_reg_button);
        email_back = findViewById(R.id.email_back);
        password_back = findViewById(R.id.password_back);
        progressBar = findViewById(R.id.progressBar);
        login_bump_btn = findViewById(R.id.login_bump_button);
        checkbox = findViewById(R.id.save_checkbox);
        if_text = findViewById(R.id.if_text);
        reset_password_button = findViewById(R.id.reset_password_button);
        preferences = getSharedPreferences("User_data",MODE_PRIVATE);



        if (preferences.getBoolean("autoLogin",false)) {

            checkbox.setChecked(true);
            login_email.setText(preferences.getString("email",""));
            login_password.setText(preferences.getString("password",""));
        }


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("autoLogin", isChecked);
                editor.putString("email",String.valueOf(login_email.getText()));
                editor.putString("password",String.valueOf(login_password.getText()));
                editor.apply();
            }
        });




        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterView.class);
                startActivity(intent);
                finish();
            }
        });



        reset_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(login_email.getText());

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(),
                                            "Password reset email sent",
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(getApplicationContext(),
                                            "Failed to send password reset email",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = String.valueOf(login_email.getText());
                password = String.valueOf(login_password.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginView.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginView.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    Toast.makeText(LoginView.this,"Enter Email and Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    progressBar.setVisibility(View.VISIBLE);
                    login_email.setAlpha(0.5f);
                    login_email.setEnabled(false);
                    login_password.setAlpha(0.5f);
                    login_password.setEnabled(false);
                    email_back.setVisibility(View.GONE);
                    password_back.setVisibility(View.GONE);
                    loginbtn.setEnabled(false);
                    loginbtn.setAlpha(0.5f);
                    if_text.setAlpha(0.5f);
                    login_bump_btn.setAlpha(0.1f);
                    registerbtn.setEnabled(false);
                    registerbtn.setAlpha(0.5f);



                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {



                                        Toast.makeText(LoginView.this, "Authentication successful.",
                                                Toast.LENGTH_SHORT).show();

                                        Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();


                                    }

                                    else {
                                        progressBar.setVisibility(View.GONE);
                                        login_email.setAlpha(1);
                                        login_email.setEnabled(true);
                                        login_password.setAlpha(1);
                                        login_password.setEnabled(true);
                                        email_back.setVisibility(View.VISIBLE);
                                        password_back.setVisibility(View.VISIBLE);
                                        loginbtn.setEnabled(true);
                                        loginbtn.setAlpha(1);
                                        if_text.setAlpha(1);
                                        login_bump_btn.setAlpha(0.5f);
                                        registerbtn.setEnabled(true);
                                        registerbtn.setAlpha(1);

                                        Toast.makeText(LoginView.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }


                            });
                }
            }
        });
    }
}