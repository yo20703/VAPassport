package com.example.vapassport;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPassport;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
    }

    private void initLayout(){

        btnPassport = findViewById(R.id.btn_passport1);
        btnRegister = findViewById(R.id.btn_register);

        btnPassport.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_passport1:
                Intent goPassportActivity = new Intent(MainActivity.this, PassportActivity.class);
                startActivity(goPassportActivity);
                break;
            case R.id.btn_register:
                Intent goRegisterActivity = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(goRegisterActivity);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}