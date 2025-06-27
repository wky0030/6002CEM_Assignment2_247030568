package com.example.sign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sign.R;
import com.example.sign.activity.AddActivity;
import com.example.sign.activity.BaseActivity;
import com.example.sign.activity.MainActivity;
import com.example.sign.activity.MapActivity;
import com.example.sign.adapter.GoodAdapter;
import com.example.sign.adapter.GoodDetailAdapter;
import com.example.sign.bean.School;
import com.example.sign.net.OnBack;
import com.example.sign.util.CallBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.OnClick;
import com.example.sign.util.SchoolDistanceHelper;
import com.example.sign.util.SchoolWithDistance;
import com.example.sign.util.Util;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {
    RecyclerView recycleView ,recycleView2;

    GoodAdapter adapter;
    GoodDetailAdapter goodDetailAdapter;
    List<SchoolWithDistance> data=new ArrayList<>();
    Button add;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_first, container, false);

        initList(root);
        getData();


        return root;

    }


    @Override
    public void onResume() {
        super.onResume();
        if(BaseActivity.user.getUtype()!=null&&BaseActivity.user.getUtype().equals("2")){
            add.setVisibility(View.VISIBLE);
        }else {
            add.setVisibility(View.VISIBLE);
        }

        getData();
    }

    public void getData() {
        NetUitl.getData("", new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                if(obj!=null){
                    List<School> arr = (List<School>) obj;
                    // 计算距离并排序
                    List<SchoolWithDistance> sortedSchools = SchoolDistanceHelper.calculateAndSort(MainActivity.myLocation, arr);
                    adapter.setData(sortedSchools);
                }
            }

            @Override
            public void onFail(Object obj) {

            }
        });
    }


    private void initList(View root) {
        add =root.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActivity.type=1;
                getActivity().startActivity(new Intent(getActivity(), AddActivity.class));
            }
        });

        recycleView= root.findViewById(R.id.list);
        adapter =new GoodAdapter(getActivity(), data, new CallBack() {
            @Override
            public void onDo(Object o) {
                School bean= (School) o;
                Util.showAlert(getActivity(), "是否删除", new OnClick() {
                    @Override
                    public void onDo() {
                        NetUitl.deleteFangZi(bean.getId(), new OnBack() {
                            @Override
                            public void onSuccess(Object obj) {
                                getData();
                            }

                            @Override
                            public void onFail(Object obj) {

                            }
                        });
                    }
                });
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recycleView.setAdapter(adapter);
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recycleView.setItemAnimator(new DefaultItemAnimator());
        com.youth.banner.Banner banner=root.findViewById(R.id.banner);


        List<Integer> data=new ArrayList<>();

        data.add(R.drawable.b1);
        data.add(R.drawable.b2);
        data.add(R.drawable.b3);

        banner.setAdapter(new BannerImageAdapter<Integer>(data) {

                    @Override
                    public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                        Glide.with(getActivity()).load(data).into( holder.imageView);
                    }
                })
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getActivity()));
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}