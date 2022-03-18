package com.example.vapassport.Register;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vapassport.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RegisterAdapter extends RecyclerView.Adapter implements Filterable {
    public ArrayList<RegisterData> arrayList;
    public ArrayList<RegisterData> arrayListFilter;

    public RegisterAdapter(ArrayList<RegisterData> arrayList){
        this.arrayList = arrayList;
        arrayListFilter = new ArrayList<>();
        arrayListFilter.addAll(arrayList);
    }

    public class registerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CheckBox cbRegisterItem;
        TextView tvDate;
        TextView tvTime;
        TextView tvPlaceCode;
        ImageButton ibEdit;
        public registerViewHolder(@NonNull View itemView) {
            super(itemView);
            cbRegisterItem = itemView.findViewById(R.id.cb_register_item);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvPlaceCode = itemView.findViewById(R.id.tv_place_code);
            ibEdit = itemView.findViewById(R.id.ib_edit);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ib_edit:
                    break;

            }
        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.register_item,viewGroup,false);
        return new registerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int position) {
        ((registerViewHolder)viewHolder).tvDate.setText(arrayList.get(position).date);
        ((registerViewHolder)viewHolder).tvTime.setText(arrayList.get(position).time);
        ((registerViewHolder)viewHolder).tvPlaceCode.setText(arrayList.get(position).placeCode);
        ((registerViewHolder)viewHolder).cbRegisterItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.get(position).isCheck = ((registerViewHolder)viewHolder).ibEdit.isClickable();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    /**使用Filter濾除方法*/
    Filter mFilter = new Filter() {
        /**此處為正在濾除字串時所做的事*/
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            /**先將完整陣列複製過去*/
            ArrayList<RegisterData> filteredList = new ArrayList<>();
            /**如果沒有輸入，則將原本的陣列帶入*/
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrayListFilter);
            } else {
                /**如果有輸入，則照順序濾除相關字串
                 * 如果你有更好的搜尋演算法，就是寫在這邊*/
                for (RegisterData movie: arrayListFilter) {
                    if (movie.date.toLowerCase().contains(constraint.toString().toLowerCase()) ||
                            movie.time.toLowerCase().contains(constraint.toString().toLowerCase())
                    ) {
                        filteredList.add(movie);
                    }
                }
            }
            /**回傳濾除結果*/
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }
        /**將濾除結果推給原先RecyclerView綁定的陣列，並通知RecyclerView刷新*/
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((Collection<? extends  RegisterData>) results.values);
            notifyDataSetChanged();
        }
    };
}
