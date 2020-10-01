package com.example.doctorappointment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {
    private View hiddenPanel,hiddenRegPanel;
    Button signin,btn_login,register;
    ImageView imageView;
    TextView textView;
    EditText username,password;
    String mUser,mPass,pName;
    SessionManager sessionManager;
    private ProgressBar loading;
    //    private static String URL_LOGIN = "http://192.168.43.129/doctor/login.php";
    private static String URL_LOGIN = "http://zp.dpcjalgaon.com/android/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager=new SessionManager(this);

        hiddenPanel = findViewById(R.id.hidden_panel);
        hiddenRegPanel = findViewById(R.id.hidden_Reg_Panel);
        signin=findViewById(R.id.signInButton);
        register=findViewById(R.id.regButton);
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView);
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        btn_login = findViewById(R.id.btnLogin);
        loading = findViewById(R.id.loading);
    }

    public void slideUpDown(final View view) {
        if (!isPanelShown()) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            signin.setVisibility(View.GONE);
            register.setVisibility(View.GONE);
//            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

        }
    }

    private boolean isPanelShown()
    {
        return hiddenPanel.getVisibility() == View.VISIBLE;
    }
    public void RegUp(View view)
    {
        if (!isPanelShown()) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenRegPanel.startAnimation(bottomUp);
            hiddenRegPanel.setVisibility(View.VISIBLE);
            register.setVisibility(View.GONE);
            signin.setVisibility(View.GONE);
//            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

        }
    }

    public void sign(View view)
    {
        mUser = username.getText().toString().trim();
        mPass = password.getText().toString().trim();


        if (!mUser.isEmpty() || !mPass.isEmpty()) {
            Login(mUser, mPass);

        } else {
            username.setError("Please insert username");
            password.setError("Please insert password");
        }
//        Intent intent=new Intent(MainActivity.this,Home.class);
//        startActivity(intent);
    }

    private void Login(final String username, final String password)
    {
        loading.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String user_id = object.getString("user_id").trim();
                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();

                                    sessionManager.createSession(name,email,user_id);

                                    Intent intent=new Intent(MainActivity.this,HomeDashboard.class);
                                    startActivity(intent);

//                                    Toast.makeText(MainActivity.this,
//                                            "Success Login. \nYour Name: "
//                                                    + name + "\nYour Email: "
//                                                    + email+"\n ID:---" +user_id, Toast.LENGTH_SHORT)
//                                            .show();
                                    loading.setVisibility(View.GONE);
                                    finish();

                                }
                            }

                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Error -" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
