package com.limamoulayeka.sunufacture;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtLogin, txtPassword;
    private Button btnConnection;
    private String login, password;
    private TextView userView;
    Databasehelper mDatabasehelper;

    public MainActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        userView = findViewById(R.id.userView);
        btnConnection = findViewById(R.id.btnConnection);

        mDatabasehelper = new Databasehelper(this);

        btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = txtLogin.getText().toString().trim();
                password = txtPassword.getText().toString().trim();

                if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.error_fields), Toast.LENGTH_SHORT).show();
                } else {
                    String data = mDatabasehelper.searchPass(login);
                    if (!password.equals(data)) {
                        Toast.makeText(MainActivity.this, getString(R.string.user_does_not_exist), Toast.LENGTH_SHORT).show();
                    } else {

                        String rule = mDatabasehelper.getRuleById(mDatabasehelper.getRuleUser(login));
                        if(rule.trim().equals("admin")) {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("login", login);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(MainActivity.this, UserBillActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("login", login);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }

                }
            }
        });

    }
}

/*
* DESCRIPTION
* Etude et mise en place d'un Système de gestion des paiements de factures.
* La thématique qui sera traitée dans ce travail sera dudomaine paiement
* plus précisément dans le cadre des paiements de factures.
* Il sera lieu de fournir à un utilisateur une interface d’accès pour gérer
* ses factures.Dans le dispositif qui sera mis en place les accès essentiels
* sont:
* •Une Présentation du système de paiement
* •Une Rubrique d'aide
* •Une Page pour Consulter les factures (Factures payées, factures non payées)
*
* */