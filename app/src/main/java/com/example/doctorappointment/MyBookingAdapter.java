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
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.BookViewHolder>{

    private Context mCtx;
    private List<Book> bookList;
    String output,name,output1;
    SimpleDateFormat sdf;
    DateFormat outputFormat;
    Date date,date1;
//    String RESULT_URL="http://192.168.43.129/doctor/scheduleUpdate.php";

    public MyBookingAdapter(Context mCtx, List<Book> bookList,String name)
    {

        this.mCtx = mCtx;
        this.bookList = bookList;
        this.name=name;

    }
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.mybooking_list, null);

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
        DateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = sdf.parse(product.getTime1());
            output = outputFormat.format(date);
            holder.textViewTime.setText(output);

            date1 = oldFormat.parse(product.getDate1());
            output1 = newFormat.format(date1);
            holder.textViewDate.setText(output1);
            final String price = holder.textViewFees.getText().toString().trim();
            final String schedule_id= String.valueOf(product.getID());
            final String payment_status=product.getPayment_status();

            if (payment_status.equals("paid"))
            {
                holder.buy.setText("PAID");
                holder.buy.setEnabled(false);


            }
            holder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {

                        Intent intent = new Intent(mCtx, Pay.class);
                        intent.putExtra("price", price);
                        intent.putExtra("schedule_id", schedule_id);
                        mCtx.startActivity(intent);
                    }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.textViewName.setText(name);

     }

    @Override
    public int getItemCount()
    {
        return bookList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate,textViewTime,textViewName,textViewFees;
        Button buy;
//        ConstraintLayout relativeLayout;
        public BookViewHolder(View itemView) {
            super(itemView);
            textViewFees=itemView.findViewById(R.id.textViewFees);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime=itemView.findViewById(R.id.textViewTime);
            textViewName=itemView.findViewById(R.id.textViewName);
            buy=itemView.findViewById(R.id.buttonBuy);
//
        }
    }
}