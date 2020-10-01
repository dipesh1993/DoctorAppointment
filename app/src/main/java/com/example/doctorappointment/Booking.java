package com.example.doctorappointment;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Booking extends Fragment {
    MaterialCalendarView mcv;
    List<Book> bookList;
    RecyclerView recyclerView;
    CalendarDay mDate;
    Button btn;
    String dayTime, output, dayDate;
    TextView message;

    private static final String URL_PRODUCTS = "http://192.168.43.129/doctor/booking.php";
    private static final String URL_SCHEDULE = "http://192.168.43.129/doctor/scheduledata.php";


    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_booking, container, false);
        mcv = view.findViewById(R.id.calendarView);
        bookList = new ArrayList<>();
        btn = view.findViewById(R.id.btn);
        message=view.findViewById(R.id.show);

//        date2 = view.findViewById(R.id.date);
        recyclerView = view.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 4, GridLayout.VERTICAL, false));
        mcv.state().edit()
                .setMinimumDate(CalendarDay.today())
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        mcv.setOnDateChangedListener(new OnDateSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override

            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                recyclerView.setAdapter(null);
                mDate = date;
                output = mDate.getDate().toString();
//                date2.setText(output);
            }

        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookList.clear();
                if (output != null) {
                    loadIntoListView();
                } else
                    Toast.makeText(getContext(), "please choose date", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void loadIntoListView() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
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
                                BookingAdapter adapter = new BookingAdapter(getContext(), bookList, output);
                            if (adapter != null)
                            {
                                if (adapter.getItemCount() > 0) {
                                    recyclerView.setAdapter(adapter);
                                } else {
                                    message.setVisibility(View.VISIBLE);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                                pulltoRefresh.setRefreshing(false);
                    }
                })
        {
            protected Map<String, String> getParams ()
            {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("date", output);

                return MyData;
            }
        };;
        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(stringRequest);
    }



}




