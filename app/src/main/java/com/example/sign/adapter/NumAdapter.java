package com.example.sign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sign.R;
import com.example.sign.bean.Good;
import com.example.sign.util.CallBack;
import com.example.sign.util.Util;

import java.util.List;

public class NumAdapter extends RecyclerView.Adapter<NumAdapter.ViewHolder> {
    private final Context conext;
    private List<Good> myData;
    private CallBack myCallback;
    private int selectIndex=-1;
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;

        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.name);
        }
    }

    public NumAdapter(Context conext, List<Good> myData, CallBack cb) {
        this.myData = myData;
        this.conext=conext;
        this.myCallback=cb;
    }
    public void setData(List<Good> myData){
        this.myData = myData;;
        selectIndex=-1;
        this.notifyDataSetChanged();
    }
    public void setIndex(int index){
        selectIndex=index;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_num_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.name.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            selectIndex=position;
            Good good = myData.get(position);
            myCallback.onDo(good);
            notifyDataSetChanged();
           });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Good good = myData.get(position);
        holder.name.setText(good.getName());
    }
    @Override
    public int getItemCount(){
        return myData.size();
    }
}
