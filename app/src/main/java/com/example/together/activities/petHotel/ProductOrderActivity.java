package com.example.together.activities.petHotel;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.activities.map.MapPetHotel;
import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ProductOrderActivity extends AppCompatActivity {


    //접속중인 아이디 값 가져오기
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


    // 지도 버튼
    ImageButton etp_addrBtn;



    //db 접속 구문
    private URL Url;
    private String strUrl;

    //상품 툴바
    Toolbar myToolbar;

    //컨텍스트 선언
    Context mcontext;


    //이전 값 받아오기
    String prefirst;
    String prelast;
    String prename;
    String preprise;
    String precont;
    String preaddr;
    String preph;
    String preimage;

    //아이디값을 설정해둘 String 값
    String user;


    //예약페이지 텍스트및 버튼 뷰 설정
    TextView or_pronm;
    TextView or_addr;
    TextView or_ph;
    TextView or_first;
    TextView or_last;
    TextView or_cont;
    TextView or_price;
    ImageView or_image;
    TextView or_total_price;

    Button orBtn;

    //결과값 변수를 담을 String 선언
    String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_order);



        // 지도 띄우기
        etp_addrBtn = findViewById(R.id.etp_addrBtn);

        etp_addrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductOrderActivity.this, MapPetHotel.class);
                //위도 latitude
//                intent.putExtra("pre_lat",pre_lat);
//
//                //경도 longitude
//                intent.putExtra("pre_lnt",pre_lnt);
//
//                //호텔 이름
//                intent.putExtra("name",pre_name);
//
//                //이미지
//                intent.putExtra("image",image);

                startActivity(intent);
            }
        });



        //문자를위한 권한체크
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "SMS 수신권한 주어져있음", Toast.LENGTH_LONG).show();

        } else {          // 권한 X 경우
            Toast.makeText(this, "SMS 수신권한 없음", Toast.LENGTH_LONG).show();

            //권한 설명이 필요한지 안필요한지 여부
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {//
                Toast.makeText(this, "SMS 권한 설명 필요함", Toast.LENGTH_LONG).show();
                //권한은 시스템에서 처리 한다 이것을 시스템에 요청해야함
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.SEND_SMS         //권한 부여를 시스템에게 요청한다.
                }, 1);

            }
        }

        //툴바 선언
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //액션바 왼쪽에 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");



        //이전 뷰에서 값 가져오기
        Bundle Ex = getIntent().getExtras();

        prefirst = Ex.getString("first");
        prelast = Ex.getString("last");
        prename = Ex.getString("proname");
        preprise = Ex.getString("proprise");
        precont = Ex.getString("procont");
        preaddr = Ex.getString("addr");
        preph = Ex.getString("ph");
        preimage = Ex.getString("img");


        //텍스트 뷰 아이디값 잡아주기
        or_pronm = findViewById(R.id.proName);
        or_addr = findViewById(R.id.etp_addr);
        or_ph = findViewById(R.id.etp_ph);
        or_first = findViewById(R.id.first_day);
        or_last = findViewById(R.id.last_day);
        or_cont = findViewById(R.id.procontent);
        or_price = findViewById(R.id.proprice);
        or_image = findViewById(R.id.productimage);
        or_total_price = findViewById(R.id.total_price);

        //버튼 아이디값 잡기
        orBtn = findViewById(R.id.orderbtn);


        int sum = Integer.parseInt(prelast.substring(2)) - Integer.parseInt(prefirst.substring(2));



        Log.e("총일수",sum+"");

        //이전값과 텍스트 뷰 값 채워주기

        or_pronm.setText(prename);
        or_addr.setText(preaddr);
        or_ph.setText(preph);
        or_first.setText(prefirst);
        or_last.setText(prelast);
        or_cont.setText(precont);
        or_price.setText(preprise);
        Picasso.get().load(preimage).fit().into(or_image);

        String[] aa = preprise.split("원");

        int sum2 = Integer.parseInt(aa[0]) * sum;

        or_total_price.setText(sum2 + "원");



        user =  firebaseUser.getEmail();




        //디비접속후 예약 추가

        orBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클릭시 디비접속후 값 받아와서 넣고빼기;



                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        strUrl = "http://39.127.7.80:8080/order_pro"; //탐색하고 싶은 URL이다.

                    }

                    @Override
                    protected Void doInBackground(Void... voids) {


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

                            wr.write( user +"="+ prename + "=" + preaddr + "=" + preph + "=" + prefirst + "=" + prelast + "=" + precont + "=" +preprise);

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

                            result = rbuffer.toString().trim();
                            is.close();
                            conn.disconnect();


                            Log.e("result", result);


                        } catch (MalformedURLException | ProtocolException exception) {
                            exception.printStackTrace();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);



                    }
                }.execute();


                String mess = "호텔의" + prefirst+"일" +"~"+prelast+"일의" +"상품 : "+prename + "이 예약이완료되었습니다 "+ ", 예약에는 1~3일정도 소요될수있습니다.";
                //예약완료시 문자보내는것
                sendSMS("01055059374",mess);


                //완료시 alert 창
                    AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ProductOrderActivity.this);
                    alert_confirm.setMessage("예약이 완료되었습니다, 리뷰작성을 하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 'YES'
                                    Intent intent = new Intent(ProductOrderActivity.this,reViewActivity.class);
                                    intent.putExtra("etpcode",result);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 'No'
                                    return;
                                }
                            });
                    AlertDialog alert = alert_confirm.create();
                    alert.show();



            }
        });




    }

    //액션바 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hotel_menu, menu) ;

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case  android.R.id.home :
//                Intent intent = new Intent(ProductOrderActivity.this,HotelDetailActivity.class);
//                startActivity(intent);
                return true ;
            case R.id.settings :
//                ((TextView)findViewById(R.id.textView)).setText("ACCOUNT") ;
                return true ;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }


    //문자전송 이벤트
    public void sendSMS(String phoneNumber, String message){
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
        PendingIntent deliverdPI = PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);

        registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK :
                    break;
                }

            }
        },new IntentFilter(SENT));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null,message,sentPI,deliverdPI);

    }


}
