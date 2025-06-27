package com.example.sign.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.sign.R;
import com.example.sign.net.OnBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // 获取地图片段并异步获取地图
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageView back = findViewById(R.id.back_btn);
        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.back);
        back.setOnClickListener(view -> finish());
        TextView title=findViewById(R.id.title);
        title.setText("Select location");
        ImageView right_btn= findViewById(R.id.right_btn);
        right_btn.setVisibility(View.VISIBLE);
        right_btn.setImageResource(R.drawable.done);
        right_btn.setOnClickListener(v-> {
            onConfirmLocation();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 设置初始位置（例如：旧金山）
        LatLng sanFrancisco = new LatLng(37.7749, -122.4194);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sanFrancisco, 12));

        // 添加地图点击监听器
        mMap.setOnMapClickListener(latLng -> {
            // 清除之前的标记
            mMap.clear();

            // 添加新标记
            mMap.addMarker(new MarkerOptions().position(latLng));
            selectedLocation = latLng;

            // 可以在这里显示地址或保存位置
            showAddress(latLng);
        });
    }

    private void showAddress(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(
                    latLng.latitude, latLng.longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressText = String.format(
                        "%s, %s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getLocality(),
                        address.getCountryName());

                // 显示地址或保存
                Toast.makeText(this, addressText, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onConfirmLocation() {
        if (selectedLocation != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("latitude", selectedLocation.latitude);
            resultIntent.putExtra("longitude", selectedLocation.longitude);
            setResult(RESULT_OK, resultIntent);
            finish();
        }else {
            Util.showErr(this,"Click map select a position first.");
        }
    }
}