package com.example.sign.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sign.R;
import com.example.sign.activity.TalkActivity;
import com.example.sign.bean.School;
import com.example.sign.bean.Msg;
import com.example.sign.util.CallBack;
import com.example.sign.util.Util;

import java.util.List;

public class TalkListAdapter extends RecyclerView.Adapter<TalkListAdapter.ViewHolder> {
    private final Context conext;
    private List<Msg> myData;
    private CallBack myCallback;
    private int selectIndex=-1;
    private School fangZi;

    public void setFangZi(School fangZi) {
        this.fangZi = fangZi;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView des;
        View parent;
        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.name);
            des = view.findViewById(R.id.des);
            parent=view;
        }
    }

    public TalkListAdapter(Context conext, List<Msg> myData, CallBack cb) {
        this.myData = myData;
        this.conext=conext;
        this.myCallback=cb;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Msg> myData){
        this.myData = myData;;
        selectIndex=-1;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_talk_list_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Msg bean= myData.get(position);
                TalkActivity.fid=bean.getFid();
                Intent intent=new Intent(conext,TalkActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("msg",  bean);
                intent.putExtras(bundle);
                conext. startActivity(intent);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                myCallback.onDo(myData.get(position));
                return false;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Msg bean = myData.get(position);
        if(bean!=null){
            Util.l(bean.getRid()+" sid="+bean.getSid());
            holder.name.setText(bean.getName()+"的对话");
            holder.des.setText(Util.formatTime(bean.getCreateTime()));
        }

    }
    @Override
    public int getItemCount(){
        return myData.size();
    }
}
