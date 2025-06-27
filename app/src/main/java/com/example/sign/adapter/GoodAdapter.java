package com.example.sign.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.sign.activity.AddActivity;
import com.example.sign.activity.MainActivity;
import com.example.sign.bean.School;
import com.example.sign.net.Api;
import com.example.sign.net.OnBack;
import com.example.sign.util.CallBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.SchoolWithDistance;
import com.example.sign.util.Util;

import java.util.List;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder> {
    private final Context conext;
    private List<SchoolWithDistance> myData;
    private CallBack myCallback;
    private int selectIndex = -1;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img, star;
        TextView name;
        TextView des;
        TextView distance;
        View parent;

        public ViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img);
            name = view.findViewById(R.id.name);
            des = view.findViewById(R.id.des);
            star = view.findViewById(R.id.star);
            distance = view.findViewById(R.id.distance);
            parent = view;
        }
    }

    public GoodAdapter(Context conext, List<SchoolWithDistance> myData, CallBack cb) {
        this.myData = myData;
        this.conext = conext;
        this.myCallback = cb;
    }

    public void setData(List<SchoolWithDistance> myData) {
        this.myData = myData;
        ;
        selectIndex = -1;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_good_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                SchoolWithDistance good = myData.get(position);

                myCallback.onDo(good);

                return false;
            }
        });
        viewHolder.parent.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            SchoolWithDistance good = myData.get(position);
            AddActivity.type = 3;
            AddActivity.fid = good.getSchool().getId() + "";
            if (Util.userId == good.getSchool().getUid()) {
                AddActivity.type = 2;
            }
            Intent intent = new Intent(conext, AddActivity.class);
            conext.startActivity(intent);
            Util.l("cc");
        });
        viewHolder.star.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            SchoolWithDistance schoolWithDistance = myData.get(position);
            School school=schoolWithDistance.getSchool();
            NetUitl.addSave(school.getId(), !school.isSave, new OnBack() {
                @Override
                public void onSuccess(Object obj) {
                    school.setSave(!school.isSave);
                    notifyDataSetChanged();
                }

                @Override
                public void onFail(Object obj) {

                }
            });
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SchoolWithDistance schoolWithDistance = myData.get(position);
        School school=schoolWithDistance.getSchool();
        String dis=schoolWithDistance.getFormattedDistance();
        holder.name.setText(school.getName());
        holder.des.setText(school.getDes());
        holder.distance.setText(dis);
        Glide.with(conext).load(school.getImgs()).into(holder.img);
        if (school.isSave) {
            holder.star.setImageResource(R.drawable.stars);
        } else {
            holder.star.setImageResource(R.drawable.star);
        }
        if (position == selectIndex) {
            holder.parent.setBackground(conext.getDrawable(R.drawable.good_select));
        } else {
            holder.parent.setBackground(conext.getDrawable(R.drawable.good_nomarl));
        }

        //圆角
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.b1)
                .centerCrop();
        Glide.with(conext)
                .load(Api.getImgUrl(school.getImgs()))
                .apply(options)
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return myData.size();
    }
}
