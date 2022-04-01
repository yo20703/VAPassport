package com.example.vapassport.Passport;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vapassport.R;
import com.example.vapassport.Register.RegisterData;
import com.example.vapassport.Sqlite.SqlDataBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class PassportActivity extends AppCompatActivity {
    private RecyclerView passportRecycleCard;
    private List<DataBean> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport);
        initData();

        initLayout();
    }

    void initPassportData(SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT * FROM " + "PassportData",null);
        c.moveToFirst();
        if(c.getCount() == 0){
            for(int i = 0;i < 3;i++){
                ContentValues contentValues = new ContentValues();
                contentValues.put("IsCheck", String.valueOf(i < 1));
                db.insert("PassportData",null,contentValues);
            }
        }

    }

    private void initData(){
        SqlDataBaseHelper sqlDataBaseHelper = new SqlDataBaseHelper(this);
        SQLiteDatabase db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
        initPassportData(db);
        Cursor c = db.rawQuery("SELECT * FROM " + "PassportData",null);
        c.moveToFirst();
        lists = new ArrayList<>();
        for(int i = 0;i < c.getCount();i++){
            lists.add(new DataBean(Boolean.parseBoolean(c.getString(0)),
                    Boolean.parseBoolean(c.getString(0))?"已完成":"未完成",
                    "第" + (i+1) + "劑施打"));
            c.moveToNext();
        }
        db.close();

    }

    private void initLayout(){
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
            actionBar.setSplitBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        }

        LinearLayoutManager m = new LinearLayoutManager(this);
        m.setOrientation(LinearLayoutManager.HORIZONTAL);

        passportRecycleCard = (RecyclerView) findViewById(R.id.recycler_View);
        PassPortViewAdapter adapter = new PassPortViewAdapter(lists, this);
        passportRecycleCard.setLayoutManager(m);
        passportRecycleCard.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}