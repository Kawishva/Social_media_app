package com.example.Social_media_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterView extends AppCompatActivity {

    EditText register_email,register_password,email_back,password_back;
    TextView loginbtn,if_text;
    Button registerbtn,register_bump_btn;
    FirebaseAuth mAuth;
    SharedPreferences preferences;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        mAuth = FirebaseAuth.getInstance();
        register_email = findViewById(R.id.register_email);
        email_back = findViewById(R.id.email_back);
        password_back = findViewById(R.id.password_back);
        register_password = findViewById(R.id.register_password);
        registerbtn = findViewById(R.id.register_button);
        loginbtn = findViewById(R.id.register_log_button);
        register_bump_btn = findViewById(R.id.register_bump_button);
        if_text = findViewById(R.id.if_text);
        preferences = getSharedPreferences("User_data", MODE_PRIVATE);
        progressBar = findViewById(R.id.progressBar);



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginView.class);
                startActivity(intent);
                finish();
            }
        });


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email,password;
                email = String.valueOf(register_email.getText());
                password = String.valueOf(register_password.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterView.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterView.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterView.this,"Enter Email and Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    progressBar.setVisibility(View.VISIBLE);
                    register_email.setAlpha(0.5f);
                    register_email.setEnabled(false);
                    register_password.setAlpha(0.5f);
                    register_password.setEnabled(false);
                    email_back.setVisibility(View.GONE);
                    password_back.setVisibility(View.GONE);
                    registerbtn.setEnabled(false);
                    registerbtn.setAlpha(0.5f);
                    if_text.setAlpha(0.5f);
                    loginbtn.setEnabled(false);
                    loginbtn.setAlpha(0.5f);
                    register_bump_btn.setAlpha(0.1f);





                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("User_Email", email);
                                        editor.putString("User_Password", password);
                                        editor.apply();


                                        Toast.makeText(RegisterView.this, "Authentication successful.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent_main =new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent_main);
                                        finish();

                                    } else {

                                        progressBar.setVisibility(View.GONE);
                                        register_email.setAlpha(1);
                                        register_email.setEnabled(true);
                                        register_password.setAlpha(1);
                                        register_password.setEnabled(true);
                                        email_back.setVisibility(View.VISIBLE);
                                        password_back.setVisibility(View.VISIBLE);
                                        registerbtn.setEnabled(true);
                                        registerbtn.setAlpha(1);
                                        if_text.setAlpha(1);
                                        loginbtn.setEnabled(true);
                                        loginbtn.setAlpha(1);
                                        register_bump_btn.setAlpha(0.5f);

                                        Toast.makeText(RegisterView.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });
                }
            }
        });

    }
}


