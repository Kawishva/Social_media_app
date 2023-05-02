package com.example.Social_media_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;

    Button logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutbtn = findViewById(R.id.logoutbtn);
        preferences = getSharedPreferences("User_data",MODE_PRIVATE);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                FirebaseAuth.getInstance().signOut();

                Intent intent =new Intent(getApplicationContext(), LoginView.class);
                startActivity(intent);
                finish();
            }
        });


    }
}