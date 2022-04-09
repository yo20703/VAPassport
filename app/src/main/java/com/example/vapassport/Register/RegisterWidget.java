package com.example.vapassport.Register;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.vapassport.R;
import com.example.vapassport.Sqlite.SqlDataBaseHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of App Widget functionality.
 */
public class RegisterWidget extends AppWidgetProvider {

    private static final String TAG = "RegisterWidget";
    ArrayList<RegisterData> mList = new ArrayList<>();
    int[] appWidgetIds;
    private static Set idsSet = new HashSet();
    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId){
        initDataBase(context);

        if(mList.size() > 0){
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.register_widget);
            views.setTextViewText(R.id.tv_date, mList.get(0).date);
            views.setTextViewText(R.id.tv_time, mList.get(0).time);
            views.setTextViewText(R.id.tv_place_code, mList.get(0).placeCode);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i(TAG, "onUpdate: ");
        this.appWidgetIds = appWidgetIds;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            idsSet.add(appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        Log.i(TAG, "onEnabled: ");
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        Log.i(TAG, "onDisabled: ");
        if(appWidgetIds != null && idsSet.size() > 0){
            for (int appWidgetId : appWidgetIds) {
                idsSet.remove(Integer.valueOf(appWidgetId));
            }
        }
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive: ");
        if(idsSet.size() > 0){
            updateAllAppWidgets(context, AppWidgetManager.getInstance(context), idsSet);
        }
    }

    private void initDataBase(Context context){
        SqlDataBaseHelper sqlDataBaseHelper = new SqlDataBaseHelper(context);
        SQLiteDatabase db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
        Cursor c = db.rawQuery("SELECT * FROM " + "RegisterData",null);
        c.moveToFirst();

        for(int i = 0;i < c.getCount();i++){
            if(i == c.getCount() - 1){
                mList.add(new RegisterData(c.getInt(0),c.getString(1), c.getString(2), c.getString(3)));
            }
        }

        db.close();
    }

    // 更新所有的 widget
    private void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager, Set set) {

        Log.d(TAG, "updateAllAppWidgets(): size="+set.size());

        // widget 的id
        int appID;
        // 迭代器，用于遍历所有保存的widget的id
        Iterator it = set.iterator();

        while (it.hasNext()) {
            appID = ((Integer)it.next()).intValue();
            // 获取 example_appwidget.xml 对应的RemoteViews
            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.register_widget);

            initDataBase(context);

            if(mList.size() > 0){
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.register_widget);
                views.setTextViewText(R.id.tv_date, mList.get(0).date);
                views.setTextViewText(R.id.tv_time, mList.get(0).time);
                views.setTextViewText(R.id.tv_place_code, mList.get(0).placeCode);
            }

            // 更新 widget
            appWidgetManager.updateAppWidget(appID, remoteView);
        }
    }
}