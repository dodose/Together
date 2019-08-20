package com.example.together.activities.petHotel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.activities.goodbyePet.GoodbyepetMenuSelectActivity;
import com.example.together.adapter.HotelAdapter;
import com.example.together.model.Hotel;
import com.example.together.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HotelListDataActivity extends AppCompatActivity {


    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    String first_day;
    String Last_day;
    String Address;
    private URL Url;
    private String strUrl;
    JSONObject jobj;
    Context context;

    Button daysBtn;

    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLayoutManager;

    String fillter = "없음";

    //넣을 변수들
    String mName;
    String mAddr;
    String mTime;
    String mcontent;
    String img_path;
    Float starcount;
    String firstprise;
    String etp_lat; //위도
    String etp_lnt; // 경도
    Float km;

    double latitude;
    double longitude;

    //toolbar
    Toolbar mToolbar;

    ImageButton openMap,filter_search;

    HotelFilterDialog cd;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list_data);


        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        gpsTracker = new GpsTracker(HotelListDataActivity.this);

        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();

        //현재위치 주소값을 반환한다
//        String address = getCurrentAddress(latitude, longitude);
//        textview_address.setText(address);
    Log.e("아","위도 : "+latitude+"\n" +"경도 : "+longitude);
//        Toast.makeText(HotelListDataActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();


        mToolbar = findViewById(R.id.toolbar);

        //툴바 액션바 왼쪽에 버튼
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");


        //recyclerview 선언
        mRecycleView = findViewById(R.id.recycler_view);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        //이전값에서 선언한 intent의 Extras 값을 넘겨주고 변수에 저장
        Bundle Ex = getIntent().getExtras();
        if (Ex != null) {
            first_day = Ex.getString("First_day");
            Last_day = Ex.getString("Last_day");
            Address = Ex.getString("AddVal");
        }


        String f[] = first_day.split("-");
        String l[] = Last_day.split("-");

        int fr = Integer.parseInt(f[2]);
        int la = Integer.parseInt(l[2]);
        int Sum = la - fr + 1;

        daysBtn = findViewById(R.id.dayBtn);
        daysBtn.setText(Address+" / "+first_day+" ~ "+Last_day);


        openMap = findViewById(R.id.open_map);


        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelListDataActivity.this,LocationShowActivity.class);
                intent.putExtra("locationObject", String.valueOf(jobj));
                intent.putExtra("first",first_day);
                intent.putExtra("last",Last_day);
                startActivity(intent);
            }
        });

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels; //디바이스 화면 너비
        int height = dm.heightPixels; //디바이스 화면 높이

        filter_search = findViewById(R.id.filter_search);
        cd = new HotelFilterDialog(this);
        WindowManager.LayoutParams wm = cd.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
        wm.copyFrom(cd.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
        wm.width = width;  //화면 너비의 절반
        wm.height = (int) (height / 1.5);  //화면 높이의 절반
        filter_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cd.setDialogListener(new HotelFilterDialog.MyDialogListener() {  // MyDialogListener 를 구현
                    @Override
                    public void onPositiveClicked(String fillter) {

                        setHotelList(fillter);

                    }

                });

                cd.show();  //다이얼로그
            }
        });

        setHotelList(fillter);


    }



   public void setHotelList(String fillter){

       new AsyncTask<Void, Void, JSONObject>() {
           @Override
           protected void onPreExecute() {
               super.onPreExecute();
               strUrl = "http://13.209.25.83:8080/test"; //탐색하고 싶은 URL이다.

           }

           @Override
           protected JSONObject doInBackground(Void... voids) {


               jobj = new JSONObject();

               try {
                   //서버 연결
                   Url = new URL(strUrl);  // URL화 한다.
                   HttpURLConnection conn = (HttpURLConnection) Url.openConnection(); // URL을 연결한 객체 생성.
                   conn.setRequestMethod("POST"); // post방식 통신
                   conn.setDoOutput(true);       // 쓰기모드 지정
                   conn.setDoInput(true);        // 읽기모드 지정

                   conn.setRequestProperty("Content-Type", "application/json; utf-8");
                   conn.setRequestProperty("Accept", "application/json; utf-8");
                   conn.connect();

                   //데이터 전달 하는곳
                   OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                   if(fillter.equals("없음")) {
                       wr.write(first_day + "=" + Last_day + "=" + Address + "=" + fillter + "=" + latitude + "=" + longitude);
                   }else{
                       wr.write(first_day + "=" + Last_day + "=" + Address + "=" + fillter + "=" + latitude + "=" + longitude);
                   }
                   wr.flush();

                   wr.close(); //전달후 닫아준다.


                   // 데이터 받아오는 곳
                   InputStream is = null;        //input스트림 개방
                   BufferedReader reader = null;

                   is = conn.getInputStream();
                   reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));  //문자열 셋 세팅
                   StringBuffer rbuffer = new StringBuffer();   //문자열을 담기 위한 객체
                   String line = null;

                   rbuffer.append(reader.readLine());

                   jobj = new JSONObject(rbuffer.toString().trim());
                   is.close();
                   conn.disconnect();


                   Log.e("result", String.valueOf(jobj));


               } catch (MalformedURLException | ProtocolException exception) {
                   exception.printStackTrace();
               } catch (IOException io) {
                   io.printStackTrace();
               } catch (JSONException e) {
                   e.printStackTrace();
               }
               return jobj;
           }

           @Override
           protected void onPostExecute(JSONObject aVoid) {
               super.onPostExecute(aVoid);

               ArrayList<Hotel> HotelList = new ArrayList<>(); // 리사이클 뷰안에 넣기위한 Vo 배열
               try {

                   JSONArray jsonArray = (JSONArray) jobj.get("result");
//                    System.out.println(jsonArray.length());


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
                       km = Float.valueOf(jsonObject.optString("etp_km"));

                    Log.e("km",km+"");
                       HotelList.add(new Hotel(img_path, mAddr, mName, mTime, mcontent,starcount,firstprise,etp_lat,etp_lnt,km));


                   }

                   //넣은 리스트를 리사이클 뷰를 통해 실행;
                   HotelAdapter myAdapter = new HotelAdapter(HotelList,context,first_day,Last_day);

                   mRecycleView.setAdapter(myAdapter);



               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
       }.execute();
    }

    //액션바 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_manu, menu) ;

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(HotelListDataActivity.this,PetHotelActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            case R.id.search:

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }



    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(HotelListDataActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(HotelListDataActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(HotelListDataActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(HotelListDataActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(HotelListDataActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(HotelListDataActivity.this, "호텔 서비스를 이용하기위해 위치정보가 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(HotelListDataActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(HotelListDataActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<android.location.Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HotelListDataActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사re
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



}