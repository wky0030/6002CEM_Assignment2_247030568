package com.example.sign.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.sign.R;
import com.example.sign.bean.Shop;
import com.example.sign.util.CallBack;

import java.util.List;

public class spAdapter extends BaseAdapter {
    private List<Shop> mShops;
    private Context mContext;
    private CallBack callBack;

    public spAdapter(List<Shop> Shops, Context context,CallBack callBack) {
        this.mShops = Shops;
        this.mContext = context;
        this.callBack=callBack;
    }

    @Override
    public int getCount() {
        return mShops.size();
    }

    @Override
    public Object getItem(int i) {
        return mShops.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        view=_LayoutInflater.inflate(R.layout.spinner_item, null);
        if(view!=null)
        {
            TextView textView=(TextView)view.findViewById(R.id.name);
//            ImageView imageView=view.findViewById(R.id.icon);
            textView.setText(mShops.get(position).getName());
//            imageView.setImageResource(mShops.get(position).getIcon());
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    callBack.onDo(position);
//                }
//            });
        }

        return view;
    }

    public void setData(List<Shop> spData) {
        this.mShops=spData;
        notifyDataSetChanged();
    }
}