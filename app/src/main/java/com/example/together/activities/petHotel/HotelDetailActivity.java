package com.example.together.activities.petHotel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.together.activities.map.MapPetHotel;
import com.example.together.adapter.ProductAdapter;
import com.example.together.adapter.ReviewAdapter;
import com.example.together.model.Product;
import com.example.together.model.Review;
import com.example.together.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static java.lang.Double.isNaN;

public class HotelDetailActivity extends AppCompatActivity {

    TextView Topname;
    TextView Bigname;
    TextView detail_addr;


    //intent해서 가져온 것들
    String pre_addr;
    String pre_name;
    String pre_first;
    String pre_last;
    String image;
    String pre_lat;
    String pre_lnt;

    //리뷰내용을 넘기기위해 선언
    ArrayList<Review> reviewlist;

    //툴바선언
    Toolbar myToolbar;

    //서블릿 통신을위한 변수 선언
    JSONObject jobj;
    private URL Url;
    private String strUrl;

    //컨텐트 선언
    Context context;

    // 상품 목록 리사이클 뷰 선언
    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLayoutManager;

    // obj에서 받아온 item에넣을 변수 지정

    String name;
    String price;
    String cont;
    String img_path;

    //전화 버튼
    ImageButton ph_Btn;

    //view에잇는 Textview선언
    TextView info;
    TextView intro;
    TextView phNumber;
    TextView etpusernm;
    ImageView etp_image;


    //test
    String test1;
    String test2;

    //리뷰 변수
    String user_id;
    String reviewcontent;
    Float star;
    String cont_dt;
    String user_nm;

    //총평점을 나타내기위한 변수선언
    double avg_star = 0.0;
    double total_avg_star;

    //리뷰 갯수및 별점 선언
    TextView starcount1;
    TextView starcount2;
    TextView reviewcount;

    //호텔 맵 버튼
    ImageButton map_pethotel;


    String re_count;

    //전체후기보기 버튼
    Button showBtn;


    //리뷰 전체페이지로 가기위한 변수선언
    String etp_code;

    //리뷰 리스트를위한 선언
    private ListView reviewListView;

    //최상단 스크롤 이벤트를 위한 변수 선언언
    Button button;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        Topname = findViewById(R.id.HotelNameView);
        Bigname = findViewById(R.id.bigname);
        detail_addr = findViewById(R.id.detail_addr);
        info = findViewById(R.id.info);
        intro = findViewById(R.id.intro);
        phNumber = findViewById(R.id.ph_number);
        ph_Btn = findViewById(R.id.ph_numberBtn);
        etpusernm = findViewById(R.id.etp_user_nm);
        map_pethotel = findViewById(R.id.map_pethotel);


        //이전값 inclue된곳에 넣기
        View imageView = findViewById(R.id.top);
        etp_image =imageView.findViewById(R.id.etpimage);



        //review
        starcount1 = findViewById(R.id.starcount);
        starcount2 = findViewById(R.id.starcount2);
        reviewcount = findViewById(R.id.reviewcount);

        showBtn =findViewById(R.id.total_review_show);

        reviewListView = findViewById(R.id.reviewListveiw);

        Bundle Ex = getIntent().getExtras();

        if (Ex != null) {
            pre_addr = Ex.getString("addr");
            pre_name = Ex.getString("name");
            pre_first = Ex.getString("first");
            pre_last = Ex.getString("last");
            image = Ex.getString("img");
            pre_lat = Ex.getString("lat");
            pre_lnt = Ex.getString("lnt");


        }

        Log.e("해당하는 업체 위도값",pre_lat);
        Log.e("해당하는 업체 경도값",pre_lnt);

        Topname.setText(pre_name);
        Bigname.setText(pre_name);
        detail_addr.setText(pre_addr);
        Picasso.get().load(image).fit().into(etp_image);


        //툴바 선언
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //액션바 왼쪽에 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");



        //recyclerview 선언

        mRecycleView = findViewById(R.id.product_recycle);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);


        //
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        button = (Button) findViewById(R.id.scollBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                scrollView.fullScroll(ScrollView.FOCUS_UP);
                scrollView.scrollTo(0,1000);

            }
        });


        //서블릿 통신
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/detail_product"; //탐색하고 싶은 URL이다.

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

                    wr.write(pre_addr + "=" + pre_name + "=" + pre_first + "=" + pre_last);

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

                ArrayList<Product> Productlist = new ArrayList<>(); // 리사이클 뷰안에 넣기위한 Vo 배열
                reviewlist = new ArrayList<>(); // 리뷰 내용 넣기

                ArrayList<Float> total_star = new ArrayList<>();
                try {

                    JSONObject jsonObject = (JSONObject) jobj.get("result");

                    JSONArray productArray = (JSONArray) jsonObject.get("product"); //상품 배열 캐스트

                    JSONObject infoObj = (JSONObject) jsonObject.get("info"); // 정보 배열 캐스트

                    JSONArray reviewArray = (JSONArray) jsonObject.get("review"); //리뷰 배열 캐스트

//                    Log.e("array", String.valueOf(productArray.length()));
//                    Log.e("array2", String.valueOf(productArray));
//                    Log.e("obj", String.valueOf(infoObj));


                    for (int i = 0; i < productArray.length(); i++) {
                        JSONObject product= productArray.getJSONObject(i);

                        price = product.optString("pd_price");
                        name = product.optString("pd_nm");
                        cont = product.optString("pd_content");
                        img_path = product.optString("pd_img_src");

                        Productlist.add(new Product(img_path,name,price,cont));

                    }


                    for(int i=0; i<reviewArray.length(); i++){
                        JSONObject review= reviewArray.getJSONObject(i);

                        user_id = review.optString("user_id");
                        user_nm = review.optString("user_nm");
                        reviewcontent = review.optString("rb_contents");
                        star = Float.valueOf(review.optString("rb_avg"));
                        cont_dt = review.optString("rb_dt");

                        reviewlist.add(new Review(user_id,reviewcontent,star,cont_dt,user_nm));
//                        Log.e("db result",user_id);
                        total_star.add(star);


                    }


                    //평점 함수

                    for(int i=0; i<total_star.size(); i++){
                        avg_star =  avg_star + total_star.get(i);
                    }

                    total_avg_star = avg_star/total_star.size();
                    re_count = String.valueOf(total_star.size());

                    //db에서 가져온 업체정보 꺼내오는곳
//                    infoObj.optString("etp_user");
//                    infoObj.optString("etp_ph_no");
//                    infoObj.optString("etp_license");
//                    infoObj.optString("etp_email");
//                    infoObj.optString("etp_info");
//                    infoObj.optString("etp_intro");
//                    infoObj.optString("etp_code"); 정보꺼내쓸 미리 만들어둠 햇갈릴까봐

                    info.setText(infoObj.optString("etp_info"));
                    intro.setText(infoObj.optString("etp_intro"));
                    phNumber.setText(infoObj.optString("etp_ph_no"));
                    etpusernm.setText(infoObj.optString("etp_user"));

                    etp_code = infoObj.optString("etp_code");


                    String star_String = String.format("%.1f", total_avg_star);
                    //리뷰 갯수및 카운트
                    if(isNaN(total_avg_star)) {
                        starcount1.setText("0.0");
                        starcount2.setText("☆  " + "0.0");
                    }else{
                        starcount1.setText(star_String);
                        starcount2.setText("★  " + star_String);
                    }
                    reviewcount.setText(re_count);

                    test1 = (String) detail_addr.getText();
                    test2 = (String) phNumber.getText();
//
//                  //상품을 넣은 리스트를 리사이클 뷰를 통해 실행;
                    ProductAdapter myAdapter = new ProductAdapter(Productlist,context,pre_first,pre_last,test1,test2);

                    mRecycleView.setAdapter(myAdapter);


//                    Log.e("총몇개가 넘어가냐?", String.valueOf(reviewlist));
                    //리뷰리스트

                    ReviewAdapter reAdapter = new ReviewAdapter(reviewlist);

                    reviewListView.setAdapter(reAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();

        //버튼색 배경색맞추기
        showBtn.setBackgroundColor(Color.TRANSPARENT);


        //전화걸기 버튼
        phNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phNumber.getText()));
                //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:12345"));
                startActivity(intent);
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(HotelDetailActivity.this,totalreviewActivity.class);
//
                intents.putExtra("array",reviewlist);
//                Log.e("rrrrr", String.valueOf(reviewlist));
                startActivity(intents);


            }
        });


        // 지도 이미지버튼 클릭
        map_pethotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelDetailActivity.this, MapPetHotel.class);
                //위도 latitude
                intent.putExtra("pre_lat",pre_lat);

                //경도 longitude
                intent.putExtra("pre_lnt",pre_lnt);

                //호텔 이름
                intent.putExtra("name",pre_name);

                //이미지
                intent.putExtra("image",image);

                startActivity(intent);


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
            case android.R.id.home:
                //                ((TextView)findViewById(R.id.textView)).setText("SEARCH") ;
                return true;
            case R.id.settings:
                //                ((TextView)findViewById(R.id.textView)).setText("ACCOUNT") ;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
