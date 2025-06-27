package com.example.sign.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sign.R;
import com.example.sign.activity.BaseActivity;
import com.example.sign.bean.School;
import com.example.sign.bean.Msg;
import com.example.sign.net.Api;
import com.example.sign.util.CallBack;
import com.example.sign.util.Util;

import java.util.List;

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.ViewHolder> {
    private final Context conext;
    private List<Msg> myData;
    private CallBack myCallback;
    private int selectIndex=-1;
    private School fangZi;

    public void setFangZi(School fangZi) {
        this.fangZi = fangZi;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name,name1;
        TextView des,des1;
        View parent ,left,right;

        public ViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img);
            name = view.findViewById(R.id.name);
            des = view.findViewById(R.id.des);
            left = view.findViewById(R.id.left);
            right = view.findViewById(R.id.right);
            name1 = view.findViewById(R.id.name1);
            des1 = view.findViewById(R.id.des1);
            parent=view;
        }
    }

    public TalkAdapter(Context conext, List<Msg> myData, CallBack cb) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_talk_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

   
      

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Msg bean = myData.get(position);
        if(bean.getSid()==Util.userId){
            holder.left.setVisibility(View.VISIBLE);
            holder.right.setVisibility(View.GONE);
            holder.name1.setText(Util.formatTime(bean.getCreateTime()));
            holder.des1.setText(bean.getDes());
            //圆角
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.user)
                    .centerCrop();
            if(fangZi!=null){
                Glide.with(conext)
                        .load(Api.getImgUrl(BaseActivity.user.getImg()))
                        .apply(options)
                        .into(holder.img);
            }
        }else {
            holder.left.setVisibility(View.GONE);
            holder.right.setVisibility(View.VISIBLE);
            holder.name.setText(Util.formatTime(bean.getCreateTime()));
            holder.des.setText(bean.getDes());
            //圆角
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.user)
                    .centerCrop();
            String url=bean.getRurl();
            if(Util.userId==bean.getRid()){
                url=bean.getSurl();
            }
            Glide.with(conext)
                    .load(Api.getImgUrl(url))
                    .apply(options)
                    .into(holder.img);
        }
    }
    @Override
    public int getItemCount(){
        return myData.size();
    }
}
