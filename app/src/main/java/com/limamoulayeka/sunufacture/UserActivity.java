package com.limamoulayeka.sunufacture;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";

    Databasehelper mDatabasehelper;
    private Button btnAdd, btnViewData;
    private EditText txtUsername,txtLogin, txtPassword;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        spinner = (Spinner) findViewById(R.id.rulespinner);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnViewData);
        mDatabasehelper = new Databasehelper(this);

        Cursor dataspinner = mDatabasehelper.getDataRule();
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
                String newEntry1 = txtUsername.getText().toString();
                String newEntry2 = txtLogin.getText().toString();
                String newEntry3 = txtPassword.getText().toString();
                String newEntry4 = spinner.getSelectedItem().toString();

                if(newEntry1.length() != 0 && newEntry2.length() != 0 && newEntry3.length() != 0 && newEntry4.length() != 0)
                {
                    AddData(newEntry1,newEntry2,newEntry3,mDatabasehelper.getIdRule(newEntry4));
                    cleanUserTextFields(txtUsername,txtLogin,txtPassword);
                }else {
                    toastMessage("You must put something in all the text fields!");
                }
            }
        });
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, ListDataUserActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String newEntry1,String newEntry2, String newEntry3, int newEntry4){
        boolean insertData = mDatabasehelper.addData(newEntry1, newEntry2, newEntry3, newEntry4);

        if(insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void cleanUserTextFields(EditText s1,EditText s2,EditText s3){
        s1.setText("");
        s2.setText("");
        s3.setText("");
    }




}
