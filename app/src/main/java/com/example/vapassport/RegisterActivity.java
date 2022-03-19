package com.example.vapassport;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.SearchManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.example.vapassport.Register.RegisterAdapter;
import com.example.vapassport.Register.RegisterData;
import com.example.vapassport.Sqlite.SqlDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    RegisterAdapter mAdapter;
    Button btnAddRegister;
    Button btnDeleteRegister;
    RecyclerView recyclerView;
    ArrayList<RegisterData> arrayList;
    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "RegisterData";
    private static SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initDataBase();
        initLayout();
    }

    private void initLayout(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("防疫實聯制");
        }

        btnAddRegister = findViewById(R.id.btn_add_register);
        btnDeleteRegister = findViewById(R.id.btn_delete_register);
        btnAddRegister.setOnClickListener(this);
        btnDeleteRegister.setOnClickListener(this);

        recyclerView = findViewById(R.id.register_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setOnClickListener(this);
        mAdapter = new RegisterAdapter(arrayList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initDataBase(){
        sqlDataBaseHelper = new SqlDataBaseHelper(this,DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫

        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable,null);
        c.moveToFirst();
        arrayList = new ArrayList();
        for(int i = 0;i < c.getCount();i++){
            arrayList.add(new RegisterData(c.getInt(0),c.getString(1), c.getString(2), c.getString(3)));
            c.moveToNext();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        /**SearchView設置，以及輸入內容後的行動*/
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /**調用RecyclerView內的Filter方法*/
                Log.i("aaa", "onQueryTextChange: " + newText);
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_register:
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.add_register_dialog,null);
                Button btnSend = dialogView.findViewById(R.id.btn_send_data);
                Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
                EditText etPlaceCode = dialogView.findViewById(R.id.et_place_code);
                EditText etDate = dialogView.findViewById(R.id.et_date);
                EditText etTime = dialogView.findViewById(R.id.et_time);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("date", etDate.getText().toString());
                        contentValues.put("time", etTime.getText().toString());
                        contentValues.put("placeCode", etPlaceCode.getText().toString());
                        long id = db.insert(DataBaseTable,null,contentValues);

                        arrayList.add(new RegisterData(id, etDate.getText().toString(), etTime.getText().toString(), etPlaceCode.getText().toString()));
                        mAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
                break;
            case R.id.btn_delete_register:
                ArrayList<Long> deleteIds = new ArrayList<Long>();
                for(int i = 0;i < mAdapter.arrayList.size();i++){
                    if(mAdapter.arrayList.get(i).isCheck()){
                        deleteIds.add(mAdapter.arrayList.get(i).getId());
                        db.delete(DataBaseTable,"_id=" + arrayList.get(i).getId(),null);
                        arrayList.remove(i);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }
}