package com.example.vapassport.Passport;

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

    private void initData(){
        lists = new ArrayList<>();
        lists.add(new DataBean(true,"已完成", "第一劑施打"));
        lists.add(new DataBean(false,"未完成", "第二劑施打"));
        lists.add(new DataBean(false,"未完成", "第三劑施打"));
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