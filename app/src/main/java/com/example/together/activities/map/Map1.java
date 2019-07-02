package com.example.together.activities.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.together.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class Map1 extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1);

        fragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment1);

        fragment.getMapAsync(this);
    }


    //비동기적인 방식으로 구글맵을 생성
    @Override
    public void onMapReady(GoogleMap map) {

        //지도 종류
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //현재 위치 표현 옵션
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);


        //줌컨트롤 표시 여부 ( 1 ~ 21 )
        map.getUiSettings().setZoomControlsEnabled(true);
    }



}
