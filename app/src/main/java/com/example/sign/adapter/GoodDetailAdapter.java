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
import com.example.sign.bean.GoodDetail;
import com.example.sign.util.CallBack;
import com.example.sign.util.Util;

import java.util.List;

public class GoodDetailAdapter extends RecyclerView.Adapter<GoodDetailAdapter.ViewHolder> {
    private final Context conext;
    private List<GoodDetail> myData;
    private CallBack myCallback;
    private int selectIndex=-1;
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;

        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.name);
        }
    }

    public GoodDetailAdapter(Context conext, List<GoodDetail> myData, CallBack cb) {
        this.myData = myData;
        this.conext=conext;
        this.myCallback=cb;
    }
    public void setData(List<GoodDetail> myData){
        this.myData = myData;
        selectIndex=-1;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_good_detail_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.name.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            GoodDetail goodDetail = myData.get(position);
            myCallback.onDo(goodDetail);
            selectIndex=position;
            notifyDataSetChanged();
           });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodDetail good = myData.get(position);
        holder.name.setText(Util.getValue(good.getName(),"","")
                +Util.getValue(good.getPrice()+""," $","")
                +Util.getValue(good.getWeight()+"","\n-","oz"));
        if(position==selectIndex){
            holder.name.setBackground(conext.getDrawable(R.drawable.good_detail_select));
        }else {
            holder.name.setBackground(conext.getDrawable(R.drawable.good_detail_nomarl));
        }
    }
    @Override
    public int getItemCount(){
        return myData.size();
    }
}
