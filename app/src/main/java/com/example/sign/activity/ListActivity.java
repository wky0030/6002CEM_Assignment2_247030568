package com.example.sign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sign.R;
import com.example.sign.adapter.GoodAdapter;
import com.example.sign.bean.School;
import com.example.sign.net.OnBack;
import com.example.sign.util.CallBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.SchoolDistanceHelper;
import com.example.sign.util.SchoolWithDistance;
import com.example.sign.util.SharedPreferencesUtils;
import com.example.sign.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {
    RecyclerView recycleView;
    ImageView img;
    GoodAdapter mAdapter;
    List<SchoolWithDistance> data=new ArrayList<>();
    private SharedPreferencesUtils sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        TextView title=findViewById(R.id.title);
        ImageView back=  findViewById(R.id.back_btn);
        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.back);
        back.setOnClickListener(v->{
            finish();
        });
        title.setText("My favor");
        findViewById(R.id.title).setVisibility(View.VISIBLE);
        recycleView= findViewById(R.id.list);
        mAdapter=new GoodAdapter(this, data, new CallBack() {
            @Override
            public void onDo(Object o) {

            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        recycleView.setAdapter(mAdapter);
        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycleView.setItemAnimator(new DefaultItemAnimator());

        getData();

    }

    private void getData() {
        NetUitl.getDataSave( new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                if(obj!=null){

                    List<School> arr = (List<School>) obj;
                    // 计算距离并排序
                    List<SchoolWithDistance> sortedSchools = SchoolDistanceHelper.calculateAndSort(MainActivity.myLocation, arr);
                    mAdapter.setData(sortedSchools);
                }
                if(data.size()==0){
                    Util.showErr(ListActivity.this,"No data");
                }
            }

            @Override
            public void onFail(Object obj) {

            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}
