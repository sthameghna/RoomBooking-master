package com.example.hotelbooking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


    AlertDialog.Builder builder;
    Spinner spnLocation,spnRoomType;
    Button btnBook;
    EditText etCheckin,etCheckout,etAdult,etChild,etRoom,etRate;
    int day1,day2,month1,month2,year1,year2;
    TextView tvService,tvVat,tvTotal,tvGrandTotal;
    int turn=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         */

        setContentView(R.layout.activity_main);



        tvGrandTotal=findViewById(R.id.tvGrandTotal);
        tvVat=findViewById(R.id.tvVat);
        tvService=findViewById(R.id.tvService);
        tvTotal=findViewById(R.id.tvTotal);
        spnLocation=findViewById(R.id.spnLocation);
        spnRoomType=findViewById(R.id.spnRoomType);
        btnBook=findViewById(R.id.btnBook);
        etCheckin=findViewById(R.id.etCheckin);
        etCheckout=findViewById(R.id.etCheckout);
        etAdult=findViewById(R.id.etAdult);
        etChild=findViewById(R.id.etChild);
        etRate=findViewById(R.id.etRate);
        etRoom=findViewById(R.id.etRoom);
        builder=new AlertDialog.Builder(this);

        String location[]={"Kathmandu","Pokhara","Jhapa","Nepaljung","Chitwan","Rara"};
        String roomType[]={"Normal","AC","Deluxe"};
        ArrayAdapter adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,location);
        spnLocation.setAdapter(adapter);

        ArrayAdapter adapter1=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,roomType);
        spnRoomType.setAdapter(adapter1);

        spnRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type=spnRoomType.getSelectedItem().toString();
                if(type=="Normal")
                {
                    etRate.setText("1000");
                }
                else if(type=="AC")
                {
                    etRate.setText("2000");
                }
                else if(type=="Deluxe")
                {
                    etRate.setText("3000");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                etRate.setText("0");
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Do you want to book this room")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(etAdult.getText().toString()=="")
                                {
                                    Toast.makeText(getApplicationContext(),"How many adults?",Toast.LENGTH_SHORT).show();
                                }
                                if(etChild.getText().toString()=="")
                                {
                                    Toast.makeText(getApplicationContext(),"How many child?",Toast.LENGTH_SHORT).show();
                                }
                                if(etRoom.getText().toString()=="")
                                {
                                    Toast.makeText(getApplicationContext(),"How many rooms?",Toast.LENGTH_SHORT).show();
                                }


                                if(year1>year2)
                                {
                                    Toast.makeText(getApplicationContext(),"Invalid date for checkout",Toast.LENGTH_SHORT).show();
                                }
                                if(year1==year2 && month1>month2)
                                {
                                    Toast.makeText(getApplicationContext(),"Invalid date for checkout",Toast.LENGTH_SHORT).show();
                                }
                                if(year1==year2 && month1==month2 && day1>day2)
                                {
                                    Toast.makeText(getApplicationContext(),"Invalid date for checkout",Toast.LENGTH_SHORT).show();
                                }
                                int leftDay,leftMonth,leftYear;
                                leftYear=year2-year1;
                                leftMonth=month2-month1;
                                leftDay=day2-day1;

                                Calculation calculation=new Calculation();
                                calculation.setRoom(Integer.parseInt(etRoom.getText().toString()));
                                calculation.setStayedDay(leftYear*365+leftMonth*30+leftDay);
                                calculation.setPrice(Integer.parseInt(etRate.getText().toString()));
                                tvGrandTotal.setText(calculation.calculate()+"");
                                tvTotal.setText(calculation.getTotal()+"");
                                tvVat.setText(calculation.getVat()+"");
                                tvService.setText(calculation.getServiceCharge()+"");
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"Booking Cancelled",Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog alert=builder.create();
                alert.setTitle("Confirmation");
                alert.show();


            }
        });



        etCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn=1;
                calenderLoader();

            }
        });

        etCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn=2;
                calenderLoader();

            }
        });

    }



    private  void calenderLoader()
    {
        Calendar calendar=Calendar.getInstance();
        int year,month,day;
        year=calendar.get(calendar.YEAR);
        month=calendar.get(calendar.MONTH);
        day =calendar.get(calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,this,year,month,day);
        datePickerDialog.show();

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date=dayOfMonth+"/"+month+"/"+year;
        if(turn==1)
        {
            day1=dayOfMonth;
            month1=month;
            year1=year;
            etCheckin.setText(date);
        }
        if(turn==2)
        {
            day2=dayOfMonth;
            month2=month;
            year2=year;
            etCheckout.setText(date);
        }

    }


}
