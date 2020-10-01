package com.example.doctorappointment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.media.audiofx.DynamicsProcessing;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookViewHolder> {

    private Context mCtx;
    private List<Book> bookList;
    String day;
    String output,dayTime,dayDate;
    SimpleDateFormat sdf;
    DateFormat outputFormat;
    Date date;
    String RESULT_URL="http://192.168.43.129/doctor/scheduleUpdate.php";

    public BookingAdapter(Context mCtx, List<Book> bookList, String day)
    {

        this.mCtx = mCtx;
        this.bookList = bookList;
        this.day=day;
        this.dayTime=dayTime;
        this.dayDate=dayDate;



//        date1=day.toString();
    }
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.booking_list, null);

        return new BookViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final BookViewHolder holder, final int position) {

        final Book product = bookList.get(position);
//        date1=day.toString();
//        String dt = product.getTime1();
        sdf = new SimpleDateFormat("hh:mm");
        outputFormat = new SimpleDateFormat("hh:mm", Locale.US);
        try {
            date = sdf.parse(product.getTime1());
            output = outputFormat.format(date);
            holder.textViewVillage.setText(output);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        if (product.getStatus().equals("1") && product.getDate1().equals(day))
//        if (dayTime!=null || dayDate!=null)
        {
            holder.relativeLayout.setBackgroundColor(Color.RED);
            holder.relativeLayout.setEnabled(false);
        }


//        String dt1=day.toString();
//            holder.textViewVillage.setText(output);
//        if (dayDate == day || dayTime == product.getTime1())
//        {
//            holder.relativeLayout.setBackgroundColor(Color.GREEN);
//        }
//        else
//        {
//
//        }
//        holder.relativeLayout;
        else {
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {

                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mCtx);
                    // ...Irrelevant code for customizing the buttons and title
                    LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogView = inflater.inflate(R.layout.custompopup, null);
                    dialogBuilder.setView(dialogView);
                    Button visit = dialogView.findViewById(R.id.visit);
                    Button cancel = dialogView.findViewById(R.id.cancel);
                    TextView close = dialogView.findViewById(R.id.close);
                    final TextView date = (TextView) dialogView.findViewById(R.id.date1);
                    final TextView time = (TextView) dialogView.findViewById(R.id.place_name);
                    try {

                        DateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String dateStr = newFormat.format(oldFormat.parse(day));
                        date.setText(dateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        time.setText(outputFormat.format(sdf.parse(product.getTime1())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    final AlertDialog alertDialog = dialogBuilder.create();
                    visit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, RESULT_URL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(mCtx, response, Toast.LENGTH_LONG).show();
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(mCtx, error.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                protected Map<String, String> getParams() {
                                    Map<String, String> MyData = new HashMap<String, String>();
                                    MyData.put("timer", product.getTime1());
                                    MyData.put("dater", day);

                                    return MyData;
                                }
                            };
                            //adding our stringrequest to queue
                            RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
                            requestQueue.add(stringRequest);
                            alertDialog.dismiss();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();

                        }
                    });
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();

                }

            });

        }
    }

    @Override
    public int getItemCount()
    {
        return bookList.size();
    }
    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textViewVillage,message;
        ConstraintLayout relativeLayout;
        public BookViewHolder(View itemView) {
            super(itemView);
            textViewVillage = itemView.findViewById(R.id.textViewVillage);
            relativeLayout=itemView.findViewById(R.id.relativeLayout);
//            relativeLayout = itemView.findViewById(R.id.relativeLayout);
//            visit = itemView.findViewById(R.id.btn);
        }
    }
}