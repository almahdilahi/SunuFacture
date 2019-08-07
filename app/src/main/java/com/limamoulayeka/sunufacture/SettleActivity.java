package com.limamoulayeka.sunufacture;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class SettleActivity extends AppCompatActivity {

    private static final String TAG = "SettleActivity";

    Databasehelper mDatabasehelper;
    private Button btnAdd, btnViewData,btnViewData1;
    private TextView txtSettleDate;
    private DatePickerDialog.OnDateSetListener mSettleDateSetListener;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);

        spinner = (Spinner) findViewById(R.id.billspinner);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnViewData);
        btnViewData1 = (Button) findViewById(R.id.btnViewData1);

        txtSettleDate = (TextView) findViewById(R.id.txtSettleDate);

        txtSettleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SettleActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mSettleDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mSettleDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Log.d(TAG,"onDataSet: date: dd/mm/yyyy " + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                txtSettleDate.setText(date);
            }
        };

        mDatabasehelper = new Databasehelper(this);

        Cursor dataspinner = mDatabasehelper.getDataBillUnpaid();
        ArrayList<Integer> listData = new ArrayList<>();
        while (dataspinner.moveToNext()){
            listData.add(dataspinner.getInt(0));
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, listData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String newEntry2 = txtSettleDate.getText().toString();

                String newEntry4 = spinner.getSelectedItem().toString();

                if(newEntry2.length() != 0 && newEntry4.length() != 0)
                {
                        int newEntry1 = mDatabasehelper.getBillAmountById(Integer.parseInt(newEntry4));
                        AddData(newEntry1, newEntry2, Integer.parseInt(newEntry4));
                        cleanSettleTextFields(txtSettleDate);
                        mDatabasehelper.updateStateBill(Integer.parseInt(newEntry4));

                }else {
                    toastMessage("You must put something in all the text fields!");
                }
            }
        });
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettleActivity.this, ListDataSettleActivity.class);
                startActivity(intent);
            }
        });
        btnViewData1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettleActivity.this, ListDataUnSettleActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(int newEntry1,String newEntry2, int newEntry3){
        boolean insertData = mDatabasehelper.addDataSettle(newEntry1, newEntry2, newEntry3);

        if(insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void cleanSettleTextFields(TextView s1){

        s1.setText("Select Date");
    }

}
