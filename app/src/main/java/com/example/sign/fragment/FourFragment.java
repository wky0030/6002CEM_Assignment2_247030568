package com.example.sign.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sign.R;
import com.example.sign.activity.BaseActivity;
import com.example.sign.activity.ListActivity;
import com.example.sign.activity.LogActivity;
import com.example.sign.activity.RegistActivity;
import com.example.sign.activity.TalkActivity;
import com.example.sign.net.Api;
import com.example.sign.util.SharedPreferencesUtils;
import com.example.sign.util.Util;

public class FourFragment extends Fragment {

    private SharedPreferencesUtils sp= SharedPreferencesUtils.getInstance("isLogin");
    ImageView header=null;
    TextView userName=null;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.setting_main, container, false);

        initView(root);
        return root;

    }
    private void initView(View root) {
        userName = root.findViewById(R.id.deviceName);

        root.findViewById(R.id.exit).setOnClickListener(o -> {
            sp.put("isLogin", "false");
            RegistActivity.url=null;
            getActivity().finish();
        });
        root.findViewById(R.id.mySave).setOnClickListener(o -> {
            getActivity().startActivity(new Intent(getActivity(), ListActivity.class));
        });
        root.findViewById(R.id.modify).setOnClickListener(o -> {
            RegistActivity.type=2;
            RegistActivity.url=BaseActivity.user.getImg();
            getActivity().startActivity(new Intent(getActivity(), RegistActivity.class));
        });

        header=root.findViewById(R.id.header);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.user);
        Bitmap bitmap2=this.makeBitmapSquare(bitmap1,300);
        RoundedBitmapDrawable roundImg1= RoundedBitmapDrawableFactory.create(getResources(),bitmap2);
        roundImg1.setAntiAlias(true);
        roundImg1.setCornerRadius(bitmap2.getWidth()/2);
        header.setImageDrawable(roundImg1);


        root.findViewById(R.id.new_ll).setOnClickListener(o -> {
            LogActivity.titleS="Notice";
            getActivity().startActivity(new Intent(getActivity(), LogActivity.class));
        });
        root.findViewById(R.id.about_ll).setOnClickListener(o -> {
            LogActivity.titleS="About";
            getActivity().startActivity(new Intent(getActivity(), LogActivity.class));
        });

        root.findViewById(R.id.feedback_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),TalkActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Util.isNotEmpty(BaseActivity.user.getImg())){
            //圆角
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.user)
                    .circleCropTransform();
            Glide.with(getActivity())
                    .load(Api.getImgUrl(BaseActivity.user.getImg()))
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .apply(options)
                    .into(header);
        }
        if(Util.isNotEmpty(BaseActivity.user.getUsername())){
            userName.setText(BaseActivity.user.getUsername());
        }

    }

    public static Bitmap makeBitmapSquare(Bitmap oldbitmap, int newWidth){
        Bitmap newbitmap=null;
        if (oldbitmap.getWidth()>oldbitmap.getHeight()){
            newbitmap=Bitmap.createBitmap(oldbitmap,oldbitmap.getWidth()/2-oldbitmap.getHeight()/2,0,oldbitmap.getHeight(),oldbitmap.getHeight());
        }else{
            newbitmap=Bitmap.createBitmap(oldbitmap,0,oldbitmap.getHeight()/2-oldbitmap.getWidth()/2,oldbitmap.getWidth(),oldbitmap.getWidth());
        }
        int width=newbitmap.getWidth();
        float scaleWidth=((float)newWidth)/width;
        Matrix matrix=new Matrix();
        matrix.postScale(scaleWidth,scaleWidth);
        newbitmap= Bitmap.createBitmap(newbitmap,0,0,width,width,matrix,true);
        newbitmap=getAlplaBitmap(newbitmap,60);
        return newbitmap;
    }

    public static Bitmap getAlplaBitmap(Bitmap sourceImg, int number) {

        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight());

        number = number * 255 / 100;

        for (int i = 0; i < argb.length; i++) {

            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);

        }

        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Bitmap.Config.ARGB_8888);

        return sourceImg;

    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}