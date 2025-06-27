package com.example.sign.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sign.R;
import com.example.sign.fragment.FirstFragment;
import com.example.sign.fragment.FourFragment;
import com.example.sign.fragment.SecondFragment;
import com.example.sign.fragment.ThirdAIFragment;
import com.example.sign.fragment.ThirdFragment;
import com.example.sign.util.Util;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback {
    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragmentList;
    private static final int INDEX_HOME_FRAGMENT = 0;
    private static final int INDEX_ORDER_FRAGMENT = 1;
    private static final int INDEX_MY_FRAGMENT = 2;
    private FragmentTransaction transaction;
    private FirstFragment fragment=null;

    public Location getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

   public static Location myLocation =null;

    private static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        initToolbar();
        initFragment();
        initNavigationBottom();

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Util.showErr(this,"no location permission");

            Util.l("my loc= no permission");

            return;
        }
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                myLocation=location;
                if(location!=null){
                    Util.l("my loc="+location.getLatitude()+" "+location.getLongitude());
                    if(fragment!=null){
                        fragment.getData();
                    }
                }

            }
        });
        client.getLastLocation().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Util.l("my loc= fail msg="+e.getMessage());

            }
        });
    }






    @SuppressLint("ResourceAsColor")
    private void initNavigationBottom() {
        bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void initFragment() {
//        FirstFragment diariesFragment = getFragment();
//        if (diariesFragment == null) {
//            diariesFragment = new FirstFragment();
//            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), diariesFragment, R.id.content);
//        }

        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
            fragment=new FirstFragment();
            fragmentList.add(fragment);
            fragmentList.add(new SecondFragment());
            fragmentList.add(new ThirdAIFragment());
            fragmentList.add(new FourFragment());

            FragmentManager fragmentManager = getSupportFragmentManager();
             transaction = fragmentManager.beginTransaction();
            for (int i = 0; i < fragmentList.size(); i++) {
                Fragment fragment = fragmentList.get(i);
                transaction.add(R.id.content, fragment);
                transaction.hide(fragment);
            }
            transaction.show(fragmentList.get(INDEX_HOME_FRAGMENT));
            transaction.commit();
        }
    }

    private FirstFragment getFragment() {
        return (FirstFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }

    private void initToolbar() {
        //设置顶部状态栏为透明
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = item -> {
        Util.l("id="+item.getTitle());
        if(item.getTitle().equals("Home")){
            changeFragment(0);
        }else  if(item.getTitle().equals("Search")){
            changeFragment(1);
        }else if(item.getTitle().equals("Chat")){
            changeFragment(2);
        }else if(item.getTitle().equals("My")){
            changeFragment(3);
        }
        return true;
    };

    private void changeFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
         transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (index == i) {
                transaction.show(fragmentList.get(i));
            } else {
                transaction.hide(fragmentList.get(i));
            }
        }
        transaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Util.l("my loc= onMapReady");
    }
}