package com.example.sign.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sign.R;
import com.example.sign.bean.BlueDevice;
import com.example.sign.bean.Device;
import com.example.sign.util.CallBack;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StringListAdapter extends RecyclerView.Adapter<StringListAdapter.ViewHolder> {
    private final Context conext;
    private List<BlueDevice> mMyFileList;
    private int type=0;
    private CallBack myOnCLick;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    static class ViewHolder extends RecyclerView.ViewHolder{
        View MyFileView;
        TextView tv_device_name;

        public ViewHolder(@NonNull View view) {
            super(view);
            MyFileView = view;
            tv_device_name = view.findViewById(R.id.tv_device_name);

        }
    }
    public void setData( List<BlueDevice> mMyFileList) {
        this.mMyFileList = mMyFileList;
    }
    public StringListAdapter(Context conext, List<BlueDevice> mMyFileList, int type, CallBack myOnCLick) {
        this.mMyFileList = mMyFileList;
        this.conext=conext;
        this.type=type;
        this.myOnCLick=myOnCLick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item_device_bluetooth_list,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.MyFileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                BlueDevice device = mMyFileList.get(position);
//                Intent i=new Intent(conext, Tab2Activity.class);
//                i.putExtra("path",MyFile.getPath());
//                conext.startActivity(i);
                myOnCLick.onDo(device);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BlueDevice myFile = mMyFileList.get(position);
        holder.tv_device_name.setText(myFile.getName()+"("+myFile.getAddr()+")");

    }
    @Override
    public int getItemCount(){
        return mMyFileList.size();
    }
}
