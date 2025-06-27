package com.example.sign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sign.R;
import com.example.sign.bean.Dictionary;
import com.example.sign.bean.Good;
import com.example.sign.util.CallBack;
import com.example.sign.util.Util;

import java.util.List;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.ViewHolder> {
    private final Context conext;
    private List<Dictionary> myData;
    private CallBack myCallback;

    public int getSelectIndex() {
        return selectIndex;
    }

    private int selectIndex=-1;
    private boolean isMuti=false;
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        RadioButton cb;

        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.name);
            cb = view.findViewById(R.id.cb);
        }
    }

    public CheckAdapter(Context conext, List<Dictionary> myData, CallBack cb) {
        this.myData = myData;
        this.conext=conext;
        this.myCallback=cb;
    }
    public void setData(List<Dictionary> myData){
        this.myData = myData;
        selectIndex=-1;
        this.notifyDataSetChanged();
    }
    public List<Dictionary> getData(){
        return  this.myData ;
    }
    public void setMuti(boolean isMuti){
        this.isMuti = isMuti;
    }
    public boolean isMuti(){
       return this.isMuti;
    }
    public void setIndex(int index){
        selectIndex=index;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_check_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.cb.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            selectIndex=position;
            Dictionary good = myData.get(position);
            if(isMuti){
                if(good.getShopId()==1){
                    good.setShopId(0);
                }else {
                    good.setShopId(1);
                }
                myCallback.onDo(myData);
            }else {
                myCallback.onDo(selectIndex);
            }
            notifyDataSetChanged();
           });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dictionary good = myData.get(position);
        holder.name.setText(good.getName());
        if(isMuti){
            holder.cb.setChecked(good.getShopId()==1);
        }else {
            holder.cb.setChecked(position==selectIndex);
        }

    }
    @Override
    public int getItemCount(){
        return myData.size();
    }
}
