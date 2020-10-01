package com.example.doctorappointment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyBookings extends Fragment {
    List<Book> bookList;
    RecyclerView recyclerView;
    Button btn;
    SessionManager sessionManager;
    String mName;
    SwipeRefreshLayout pulltoRefresh;


    private static final String URL_MYBOOKINGS = "http://192.168.43.129/doctor/MyBooking.php";


    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_bookings, container, false);
        sessionManager=new SessionManager(getContext());
        HashMap<String, String> user=sessionManager.getUserDetail();
        mName=user.get(sessionManager.NAME);
        bookList = new ArrayList<>();
        btn = view.findViewById(R.id.btn);
        pulltoRefresh=view.findViewById(R.id.pullToRefresh);
//        date2 = view.findViewById(R.id.date);
        recyclerView = view.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayout.VERTICAL, false));

        pulltoRefresh.post(new Runnable() {

            @Override
            public void run()
            {
                pulltoRefresh.setRefreshing(true);
                loadIntoListView();
            }
        });
        pulltoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh() {
                loadIntoListView();
            }
        });
        return view;
    }

    private void loadIntoListView() {
        pulltoRefresh.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MYBOOKINGS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        pDialog.dismiss();
                        bookList.clear();
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                bookList.add(new Book
                                        (
                                                product.getString("status"),
                                                product.getString("startTime"),
                                                product.getString("date"),
                                                product.getInt("id"),
                                                product.getString("payment_status")


//                                                product.getString("st")

                                        ));
                            }

                            //creating adapter object and setting it to recyclerview
                            MyBookingAdapter adapter = new MyBookingAdapter(getContext(), bookList,mName);

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pulltoRefresh.setRefreshing(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pulltoRefresh.setRefreshing(false);
                    }
                })
        {
            protected Map<String, String> getParams ()
            {
                Map<String, String> MyData = new HashMap<String, String>();
                return MyData;
            }
        };;
        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(stringRequest);
    }



}




