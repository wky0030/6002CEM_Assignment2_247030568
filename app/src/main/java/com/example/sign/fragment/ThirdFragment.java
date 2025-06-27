package com.example.sign.fragment;

import android.os.Bundle;
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
import com.example.sign.adapter.TalkListAdapter;
import com.example.sign.bean.Msg;
import com.example.sign.net.OnBack;
import com.example.sign.util.CallBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.OnClick;
import com.example.sign.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ThirdFragment extends Fragment {
    RecyclerView recycleView ,recycleView2;

    TalkListAdapter goodAdapter;
    List<Msg> data=new ArrayList<>();
    EditText et;
    TextView bt;

    Timer t;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_four, container, false);

        initList(root);
        initView(root);
        t=new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                getData("");
            }
        },0,1000);
        return root;

    }

    private void initView(View root) {
        bt= root.findViewById(R.id.bt);
        et= root.findViewById(R.id.et);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData("");
    }

    private void getData(String  keyword) {
        NetUitl.getAllMsg(new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                if(obj!=null){
                    data= (List<Msg>) obj;
                    goodAdapter.setData(data);
                }
            }

            @Override
            public void onFail(Object obj) {

            }
        });
    }

    private void initList(View root) {

        recycleView= root.findViewById(R.id.list);
        goodAdapter=new TalkListAdapter(getActivity(), data, new CallBack() {
            @Override
            public void onDo(Object o) {
                Msg bean= (Msg) o;
                Util.showAlert(getActivity(), "Do delete", new OnClick() {
                    @Override
                    public void onDo() {
                        NetUitl.deleteMsg(bean, new OnBack() {
                            @Override
                            public void onSuccess(Object obj) {
                                getData("");
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

        recycleView.setAdapter(goodAdapter);
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recycleView.setItemAnimator(new DefaultItemAnimator());


    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        if(t!=null){
            t.cancel();
            t=null;
        }
        super.onDestroyView();
    }



}