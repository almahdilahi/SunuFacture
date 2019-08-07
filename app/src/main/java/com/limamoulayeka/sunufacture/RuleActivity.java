package com.limamoulayeka.sunufacture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RuleActivity extends AppCompatActivity {

    private static final String TAG = "RuleActivity";

    Databasehelper mDatabasehelper;
    private Button btnAdd, btnViewData;
    private EditText txtRulename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);

        txtRulename = (EditText) findViewById(R.id.txtRulename);
        btnAdd = (Button) findViewById(R.id.btnAddRule);
        btnViewData = (Button) findViewById(R.id.btnViewDataRule);
        mDatabasehelper = new Databasehelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry1 = txtRulename.getText().toString();
                if(newEntry1.length() != 0)
                {
                    AddDataRule(newEntry1);
                    cleanRuleTextFields(txtRulename);
                }else {
                    toastMessage("You must put something in all the text fields!");
                }
            }
        });
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RuleActivity.this, ListDataRuleActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddDataRule(String newEntry1){
        boolean insertData = mDatabasehelper.addDataRule(newEntry1);

        if(insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void cleanRuleTextFields(EditText s1){
        s1.setText("");
         }
}
