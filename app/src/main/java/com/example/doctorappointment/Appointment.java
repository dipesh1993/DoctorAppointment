package com.example.doctorappointment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;


public class Appointment extends AppCompatActivity {

    FrameLayout frameLayout;
    TabLayout tabLayout;
    Context context;
    FragmentManager fragmentManager;
    Fragment fragment=null;
    FragmentTransaction fragmentTransaction;
//    SessionManager sessionManager;
    String mId,name1,designation1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        context=this.getApplicationContext();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        sessionManager=new SessionManager(this);
//        HashMap<String, String> user=sessionManager.getUserDetail();
//        mId=user.get(sessionManager.ID);
//        name1=getIntent().getExtras().getString("name");
//        designation1=getIntent().getExtras().getString("designation");

        frameLayout=(FrameLayout)findViewById(R.id.simpleFrameLayout);
        tabLayout=(TabLayout)findViewById(R.id.simpleTabLayout);
//        ActionBar actionBar=((MainActivity ) fragment.getActivity()).getSupportActionBar();

        TabLayout.Tab firstTab=tabLayout.newTab();
        firstTab.setText("Book Appointment");
//        firstTab.setIcon(R.drawable.newsicon);
        tabLayout.addTab(firstTab);

        TabLayout.Tab secondTab=tabLayout.newTab();
        secondTab.setText("My Appointments");
//        secondTab.setIcon(R.drawable.visit);
        tabLayout.addTab(secondTab);



        fragment=new Booking();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.simpleFrameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition())
                {
                    case 0:
                        fragment=new Booking();
                        break;
                    case 1:
                        fragment=new MyBookings();
                        break;
                }

                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout,fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.user_menu,menu);
//
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id=item.getItemId();
//        switch (id)
//        {
//            case R.id.action_logout:
//                sessionManager.logout();
//                break;
//            case R.id.action_profile:
//                Intent intent=new Intent(this,UserProfile.class);
//                intent.putExtra("name",name1);
//                intent.putExtra("designation",designation1);
//                startActivity(intent);
//                finish();
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//        return true;
//    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
//        new AlertDialog.Builder(this)
//                .setTitle("Exit?")
//                .setMessage("Are you sure you want to exit?")
//                .setNegativeButton(android.R.string.no, null)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        setResult(RESULT_OK, new Intent().putExtra("EXIT", true));
//                        finish();
//                    }
//                }).create().show();
        finish();

    }


}
