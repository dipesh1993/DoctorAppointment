package com.example.doctorappointment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class BookAppointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void profile(View view) {
        Intent intent = new Intent(BookAppointment.this, UserProfile.class);
        startActivity(intent);
    }

    public void call(View view)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+919649614242"));
        startActivity(intent);
    }

    public void booking(View view)
    {
        Intent intent=new Intent(BookAppointment.this,Appointment.class);
        startActivity(intent);
    }

    public void chat(View view)
    {
        Intent intent=new Intent(BookAppointment.this,Chat.class);
        startActivity(intent);
    }
}
