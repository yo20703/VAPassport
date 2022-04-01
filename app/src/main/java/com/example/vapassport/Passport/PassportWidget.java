package com.example.vapassport.Passport;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RemoteViews;

import com.example.vapassport.R;
import com.example.vapassport.Sqlite.SqlDataBaseHelper;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class PassportWidget extends AppWidgetProvider {

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        ArrayList<Boolean> list = initData(context);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.passport_widget);
        views.setImageViewResource(R.id.iv_first, list.get(0)?R.drawable.checked:R.drawable.cancel);
        views.setImageViewResource(R.id.iv_second, list.get(1)?R.drawable.checked:R.drawable.cancel);
        views.setImageViewResource(R.id.iv_third, list.get(2)?R.drawable.checked:R.drawable.cancel);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private ArrayList<Boolean> initData(Context context){
        SqlDataBaseHelper sqlDataBaseHelper = new SqlDataBaseHelper(context);
        SQLiteDatabase db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
        Cursor c = db.rawQuery("SELECT * FROM " + "PassportData",null);
        c.moveToFirst();
        ArrayList<Boolean> lists = new ArrayList<>();
        for(int i = 0;i < c.getCount();i++){
            lists.add(Boolean.parseBoolean(c.getString(0)));
            c.moveToNext();
        }
        db.close();
        return lists;
    }
}