package com.example.doctorappointment;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

public class UserProfile extends AppCompatActivity
{
    SessionManager sessionManager;
    String pName;
    TextView user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        sessionManager=new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String,String> user=sessionManager.getUserDetail();
        pName=user.get(sessionManager.NAME);
        user_name=findViewById(R.id.user_name);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().hide();
        user_name.setText(pName);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
