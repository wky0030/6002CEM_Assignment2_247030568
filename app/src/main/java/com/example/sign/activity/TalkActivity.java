package com.example.sign.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sign.R;
import com.example.sign.adapter.TalkAdapter;
import com.example.sign.bean.School;
import com.example.sign.bean.Msg;
import com.example.sign.net.OnBack;
import com.example.sign.util.CallBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class TalkActivity extends BaseActivity {
    private ArrayList<Msg> data=new ArrayList<Msg>();
    RecyclerView recycleView ;

    TalkAdapter adapter;

    LinearLayout header_in;
    ImageView back,right_btn;
    EditText et;
    View bt;

    public static Integer fid=null;
    School school =new School();
    Msg msg=null;
    Timer t;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

         msg = (Msg) getIntent().getSerializableExtra("msg");
        TextView title=findViewById(R.id.title);
        header_in=findViewById(R.id.header_in);
        header_in.setBackgroundColor(getResources().getColor(R.color.transparent));
        title.setText("FeedBack");
        back= findViewById(R.id.back_btn);
        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.back);
        back.setOnClickListener(view -> finish());

//        right_btn= findViewById(R.id.right_btn);
//        right_btn.setVisibility(View.VISIBLE);
//        right_btn.setImageResource(R.drawable.shop);
//        right_btn.setOnClickListener(v -> {
//            Intent intent=new Intent(this,ShopActivity.class);
//            intent.putExtra("fid",fid);
//            startActivity(intent);
//        });
//        if(fangZi!=null&&user!=null&&
//                user.getId()==fangZi.getUid()){
//            right_btn.setVisibility(View.GONE);
//        }

        initList();



        et= findViewById(R.id.et);
        bt= findViewById(R.id.bt);
        bt.setOnClickListener(v->{
            String keyword=et.getText().toString();
            if(!TextUtils.isEmpty(keyword)){
                sendMsg(keyword,et);
            }
        });
        t=new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                getData();
            }
        },0,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(t!=null){
            t.cancel();
            t=null;
        }
    }

    private void sendMsg(String keyword, EditText et) {
        Msg bean=new Msg();
        bean.setCreateTime(new Date().getTime());
        bean.setDes(keyword);
        bean.setFid(1);
        bean.setSid(user.getId());
        bean.setSurl(user.getImg());
        bean.setRid(10);
        bean.setName(user.getUsername()+"' feedback");
        data.add(bean);
        adapter.notifyDataSetChanged();

        NetUitl.addMsg(bean, new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                et.setText("");
            }

            @Override
            public void onFail(Object obj) {

            }
        });
    }

    private void initList() {
        recycleView= findViewById(R.id.list);
        adapter=new TalkAdapter(this, data, new CallBack() {
            @Override
            public void onDo(Object o) {
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycleView.setItemAnimator(new DefaultItemAnimator());
    }
    private void getData() {
        Msg bean=new Msg();
        bean.setCreateTime(new Date().getTime());
        bean.setFid(1);
        bean.setSid(user.getId());
        bean.setRid(10);
        if(msg!=null){
            Util.l(bean.getRid()+" 11sid="+bean.getSid());
            bean=msg;
        }
        Util.l(bean.getRid()+" 22sid="+bean.getSid());
        NetUitl.getAllMsgByFid(bean, new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                if(obj!=null){
                    data= (ArrayList<Msg>) obj;
                    adapter.setData(data);
                    if(data.size()==0){
                        Util.showErr(TalkActivity.this,"暂无数据");
                    }
                }
            }

            @Override
            public void onFail(Object obj) {

            }
        });
    }
}
