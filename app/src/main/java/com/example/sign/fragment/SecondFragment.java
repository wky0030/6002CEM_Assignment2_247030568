package com.example.sign.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sign.R;
import com.example.sign.activity.MainActivity;
import com.example.sign.adapter.GoodAdapter;
import com.example.sign.adapter.GoodDetailAdapter;
import com.example.sign.bean.School;
import com.example.sign.net.OnBack;
import com.example.sign.util.CallBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.SchoolDistanceHelper;
import com.example.sign.util.SchoolWithDistance;
import com.example.sign.util.Util;


import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {
    RecyclerView recycleView ,recycleView2;

    GoodAdapter goodAdapter;
    GoodDetailAdapter goodDetailAdapter;
    List<SchoolWithDistance> data=new ArrayList<>();
    EditText et;
    TextView bt;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_second, container, false);

        initList(root);
        initView(root);

        return root;

    }

    private void initView(View root) {
        bt= root.findViewById(R.id.bt);
        et= root.findViewById(R.id.et);
        bt.setOnClickListener(v->{
            String keyword=et.getText().toString();
            if(!TextUtils.isEmpty(keyword)){
                getData(keyword);
            }else {
                data.clear();
                goodAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getData(String  keyword) {
        NetUitl.getData(keyword, new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                if(obj!=null){
                    List<School> arr = (List<School>) obj;
                    // 计算距离并排序
                    List<SchoolWithDistance> sortedSchools = SchoolDistanceHelper.calculateAndSort(MainActivity.myLocation, arr);
                    goodAdapter.setData(sortedSchools);


                    if(data.size()==0){
                        Util.showErr(getActivity(),"No data");
                    }
                }
            }

            @Override
            public void onFail(Object obj) {

            }
        });
    }

    private void initList(View root) {

        recycleView= root.findViewById(R.id.list);
        goodAdapter=new GoodAdapter(getActivity(), data, new CallBack() {
            @Override
            public void onDo(Object o) {
                School caoYao= (School) o;
                if(caoYao!=null&&caoYao.getId()!=null){
                }
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recycleView.setAdapter(goodAdapter);
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recycleView.setItemAnimator(new DefaultItemAnimator());


    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}