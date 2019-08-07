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

public class BillActivity extends AppCompatActivity {

    private static final String TAG = "BillActivity";

    Databasehelper mDatabasehelper;
    private Button btnAdd, btnViewData;
    private EditText txtBillAmount;
    private TextView txtBillDate;
    private DatePickerDialog.OnDateSetListener mBillDateSetListener;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        txtBillAmount = (EditText) findViewById(R.id.txtBillAmount);

        spinner = (Spinner) findViewById(R.id.userspinner);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnViewData);

        txtBillDate = (TextView) findViewById(R.id.txtBillDate);

        txtBillDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        BillActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mBillDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mBillDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Log.d(TAG,"onDataSet: date: dd/mm/yyyy " + dayOfMonth + "/" + month + "/" + year);
            String date = dayOfMonth + "/" + month + "/" + year;
            txtBillDate.setText(date);
            }
        };

        mDatabasehelper = new Databasehelper(this);

        Cursor dataspinner = mDatabasehelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (dataspinner.moveToNext()){
            listData.add(dataspinner.getString(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry1 = txtBillAmount.getText().toString();
                String newEntry2 = txtBillDate.getText().toString();
                int newEntry3 = 0;
                String newEntry4 = spinner.getSelectedItem().toString();

                if(newEntry1.length() != 0 && newEntry2.length() != 0 && newEntry4.length() != 0)
                {
                    if(isANumber(newEntry1)) {
                        AddData(Integer.parseInt(newEntry1), newEntry2, newEntry3, mDatabasehelper.getIdUser(newEntry4));
                        cleanBillTextFields(txtBillAmount, txtBillDate);
                    }else {
                        toastMessage("You must put a number in amount text field!");
                    }
                    }else {
                    toastMessage("You must put something in all the text fields!");
                }
            }
        });
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BillActivity.this, ListDataBillActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(int newEntry1,String newEntry2, int newEntry3, int newEntry4){
        boolean insertData = mDatabasehelper.addDataBill(newEntry1, newEntry2, newEntry3, newEntry4);

        if(insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void cleanBillTextFields(EditText s1,TextView s2){
        s1.setText("");
        s2.setText("Select Date");
    }

    public boolean isANumber(String s) {
        if(s==null) return false;
        try {
            new java.math.BigDecimal(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
