package com.example.vapassport.Passport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vapassport.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PassPortViewAdapter extends RecyclerView.Adapter{
    final String TAG = "PassPortViewAdapter";
    private List<DataBean> lists;
    private Context context;

    public PassPortViewAdapter(List<DataBean> lists, Context context){
        this.lists = lists;
        this.context = context;
    }

    static class myHolder extends RecyclerView.ViewHolder{
        TextView tv1;
        TextView tv2;
        ImageView iv1;
        public myHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv1 = (ImageView) itemView.findViewById(R.id.iv1);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
        }
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup viewGroup, int i) {
        return new myHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.passport_recycler_item,viewGroup,false));
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG, "getItemId: " + position);
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder, int position) {
        Log.i(TAG, "onBindViewHolder: " + position);
        ((myHolder)viewHolder).iv1.setImageResource(lists.get(position).isCheck ? R.drawable.checked : R.drawable.cancel);
        ((myHolder)viewHolder).tv1.setText(lists.get(position).status1);
        ((myHolder)viewHolder).tv2.setText(lists.get(position).status2);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: " + lists.size());
        return lists.size();
    }


}

class DataBean{
    boolean isCheck = false;
    String status1 = "";
    String status2 = "";

    public DataBean(boolean isCheck, String status1, String status2){
        this.isCheck = isCheck;
        this.status1 = status1;
        this.status2 = status2;
    }
}
