package com.example.together.activities.petHotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.together.R;
import com.example.together.adapter.HotelAdapter;
import com.example.together.model.Hotel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class LocationShowActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    JSONObject jobj;
    String castString;
    private GoogleMap mMap;

    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLayoutManager;

    String mName,mAddr,mTime,mcontent,img_path,firstprise,etp_lat,etp_lnt;
    Float starcount,pre_lat,pre_lnt,km;

    String First,Last;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_show);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//recyclerview 선언
        mRecycleView = findViewById(R.id.hotelList);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Bundle Bx = getIntent().getExtras();

        if(Bx != null){
            Log.e("Bx",Bx.getString("locationObject"));
            castString = Bx.getString("locationObject");
            First = Bx.getString("first");
            Last = Bx.getString("last");
        }

        try {

            jobj = new JSONObject(castString);
            JSONArray jsonArray = (JSONArray) jobj.get("result");
            mMap = googleMap;

            ArrayList<Hotel> MapHotel = new ArrayList<Hotel>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                mName = jsonObject.optString("etp_nm");
                mAddr = jsonObject.optString("etp_addr");
                mTime = jsonObject.optString("etp_if_time1") + "~" + jsonObject.optString("etp_if_time2");
                mcontent = jsonObject.optString("etp_cont");
                img_path = jsonObject.optString("etp_img_path");
                starcount = Float.valueOf(jsonObject.optString("avg"));
                firstprise = jsonObject.optString("firstprice");
                etp_lat = jsonObject.optString("etp_lat");
                etp_lnt = jsonObject.optString("etp_lnt");
                pre_lat = Float.valueOf(jsonObject.optString("etp_lat"));
                pre_lnt = Float.valueOf(jsonObject.optString("etp_lnt"));
                km = Float.valueOf(jsonObject.optString("etp_km"));



                MapHotel.add(new Hotel(img_path, mAddr, mName, mTime, mcontent,starcount,firstprise,etp_lat,etp_lnt,km));


                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(pre_lat,pre_lnt))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title(mName)
                        .snippet("최소가 - "+firstprise)
                        ).showInfoWindow();

            }

            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(pre_lat,pre_lnt)));
            if(jsonArray.length() > 0 && jsonArray.length() < 10) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pre_lat, pre_lnt), 14));
            }else if(jsonArray.length() <= 10 && jsonArray.length() > 20){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pre_lat, pre_lnt), 12));
            }else if(jsonArray.length() <= 20 && jsonArray.length() > 30){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pre_lat, pre_lnt), 10));
            }

            mMap.setOnMarkerClickListener(this);

            HotelAdapter mapHotel = new HotelAdapter(MapHotel,context,First,Last);
            mRecycleView.setAdapter(mapHotel);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        //추후 포커싱 개발 예정 (넉넉할때 이미 끝)
        Toast.makeText(this, marker.getTitle(),Toast.LENGTH_LONG).show();
        //Using position get Value from arraylist

        return false;
    }
}
