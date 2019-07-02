package com.example.together.activities.map;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.together.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPetHotel extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_pet_hotel);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Intent intent = getIntent();
        float pre_lat = Float.valueOf(intent.getExtras().getString("pre_lat"));
        float pre_lnt = Float.valueOf(intent.getExtras().getString("pre_lnt"));
        String name = intent.getExtras().getString("name");
        String image = intent.getExtras().getString("image");


        LatLng hotel = new LatLng(pre_lat, pre_lnt);

        Log.e("내맵",pre_lat+"");
        Log.e("좌표",pre_lnt+"");




        mMap = googleMap;
        mMap.addMarker(new MarkerOptions()
                .position(hotel)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title(name));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(hotel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hotel,16));




    }
}
