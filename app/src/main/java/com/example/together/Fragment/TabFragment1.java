package com.example.together.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.together.Activities.GoodbyePet.CheckedActivity;
import com.example.together.Activities.GoodbyePet.ConstantManager;
import com.example.together.Activities.GoodbyePet.GoodbyepetReservationResultActivity;
import com.example.together.Activities.GoodbyePet.MyCategoriesExpandableListAdapter;
import com.example.together.Model.DataItem;
import com.example.together.Model.SubCategoryItem;
import com.example.together.R;

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
import java.util.HashMap;

public class TabFragment1 extends Fragment {

    private Button btn;
    private ExpandableListView lvCategory;

    private ArrayList<DataItem> arCategory;
    private ArrayList<SubCategoryItem> arSubCategory;
    private ArrayList<ArrayList<SubCategoryItem>> arSubCategoryFinal;




    private ArrayList<HashMap<String, String>> parentItems;
    private ArrayList<ArrayList<HashMap<String, String>>> childItems;
    private MyCategoriesExpandableListAdapter myCategoriesExpandableListAdapter;

    //서블릿 변수선언

    private URL Url;
    private String strUrl;

    JSONObject jobj;


    ArrayList<SubCategoryItem> suit = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        final String etp_cd= getArguments().getString("code"); // 업체코드 가져오기

//        Log.e("frgment1",etp_cd);




        //서블릿 통신
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/Funeral_Detail"; //탐색하고 싶은 URL이다.

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

                    wr.write(etp_cd);

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
//                    result = rbuffer.toString().trim();
                    conn.disconnect();


                    Log.e("result", jobj+"");


                } catch (MalformedURLException | ProtocolException exception) {
                    exception.printStackTrace();
                } catch (IOException io) {
                    io.printStackTrace();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                return jobj;
            }

            @Override
            protected void onPostExecute(JSONObject aVoid) {
                super.onPostExecute(aVoid);


//                Log.e("넘어왓는지 확인", jobj + "");


//

                JSONObject jsonObject = null;
                try {

                    JSONArray jsonArray = (JSONArray) jobj.get("result");

//                    Log.e("jsonarray",jsonArray.length()+"");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);

                                    if(i==0) {
                                        String pd_nm;
                                        String pd_img;
                                        String pd_content;
                                        String pd_price;
                                        JSONArray sarray = (JSONArray) jsonObject.get("수의");
            //                            Log.e("sarray",sarray+"");
                                        for (int s=0; s<sarray.length(); s++) {
                                         JSONObject sjobj =  sarray.getJSONObject(s);
                                            pd_nm = sjobj.getString("etp_pd_nm");
                                            pd_img = sjobj.getString("etp_img_path");
                                            pd_content = sjobj.getString("etp_pd_content");
                                            pd_price = sjobj.getString("etp_pd_price");

            //                                Log.e("수의",pd_nm + "//" + pd_price);

            //                                suit.add(new SubCategoryItem(pd_nm,pd_img,pd_content,pd_price));
                                        }

            //                            Log.e("subcategory",suit+"");
            //

                                    } else if(i==1){
            //                            Log.e("함", jsonObject.getString("함"));
                                    } else if(i==2){
            //                            Log.e("관", jsonObject.getString("관"));
                                    } else {

                                        String pd_nm;
                                        String pd_img;
                                        String pd_content;
                                        String pd_price;

                                        JSONArray farray = (JSONArray) jsonObject.get("화장");
            //                            Log.e("farray",farray+"");
                                        for (int f=0; f<farray.length(); f++) {
                                         JSONObject sjobj =  farray.getJSONObject(f);
                                            pd_nm = sjobj.getString("etp_pd_nm");
                                            pd_img = sjobj.getString("etp_img_path");
                                            pd_content = sjobj.getString("etp_pd_content");
                                            pd_price = sjobj.getString("etp_pd_price");

            //                                Log.e("수의",pd_nm + "//" + pd_price);
                                            //해당 모델 객체에 add하기
                                            suit.add(new SubCategoryItem(pd_nm,pd_img,pd_content,pd_price));
                                        }
                                        Log.e("sub",suit+"");
                                    }
                                }


                    setupReferences(view,suit);

                    }catch (JSONException e) {
                     e.printStackTrace();
                  }
            }
        }.execute();


        btn = view.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodbyepetReservationResultActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }

    private void setupReferences(View view, ArrayList<SubCategoryItem> suit) {

        lvCategory = view.findViewById(R.id.lvCategory);
        arCategory = new ArrayList<>(); // 큰 상품들의 array 배열

        arSubCategory = new ArrayList<>();
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();


        Log.e("suit 0 번지", this.suit +"");
//        ArrayList<SubCategoryItem> list = suit;

        // 1
        DataItem dataItem = new DataItem();
        dataItem.setCategoryId("1");
        dataItem.setCategoryName("수의");
//        arSubCategory = new ArrayList<>();
        for (int i = 0; i <suit.size(); i++) {
            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(i));
            subCategoryItem.setPd_img(suit.get(i).getPd_img());
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName(suit.get(i).getPd_nm());
            arSubCategory.add(subCategoryItem);
        }
        dataItem.setSubCategory(arSubCategory);
        arCategory.add(dataItem);



        // 2
        dataItem = new DataItem();
        dataItem.setCategoryId("2");
        dataItem.setCategoryName("함");
        arSubCategory = new ArrayList<>();
        for (int j = 1; j < 6; j++) {

            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(j));
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName("함 상품들의 array " + j);
            arSubCategory.add(subCategoryItem);
        }
        dataItem.setSubCategory(arSubCategory);
        arCategory.add(dataItem);


        // 3
        dataItem = new DataItem();
        dataItem.setCategoryId("3");
        dataItem.setCategoryName("관");
        arSubCategory = new ArrayList<>();
        for (int k = 1; k < 6; k++) {

            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(k));
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName("관 상품들의 array " + k);
            arSubCategory.add(subCategoryItem);
        }
        dataItem.setSubCategory(arSubCategory);
        arCategory.add(dataItem);



        //4
        dataItem = new DataItem();
        dataItem.setCategoryId("4");
        dataItem.setCategoryName("화장비");
        arSubCategory = new ArrayList<>();
        for (int o = 1; o < 6; o++) {

            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(o));
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName("화장 상품들의 array " + o);
            arSubCategory.add(subCategoryItem);
        }

        dataItem.setSubCategory(arSubCategory);
        arCategory.add(dataItem);




        Log.d("TAG", "setupReferences: " + arCategory.size());

        for (DataItem data : arCategory) {
//                        Log.i("Item id",item.id);
            ArrayList<HashMap<String, String>> childArrayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> mapParent = new HashMap<String, String>();

            mapParent.put(ConstantManager.Parameter.CATEGORY_ID, data.getCategoryId());
            mapParent.put(ConstantManager.Parameter.CATEGORY_NAME, data.getCategoryName());

            int countIsChecked = 0;
            for (SubCategoryItem subCategoryItem : data.getSubCategory()) {

                HashMap<String, String> mapChild = new HashMap<String, String>();
                mapChild.put(ConstantManager.Parameter.SUB_ID, subCategoryItem.getSubId());
                mapChild
                        .put(ConstantManager.Parameter.SUB_CATEGORY_NAME, subCategoryItem.getSubCategoryName());
                mapChild.put(ConstantManager.Parameter.CATEGORY_ID, subCategoryItem.getCategoryId());
                mapChild.put(ConstantManager.Parameter.IS_CHECKED, subCategoryItem.getIsChecked());

                if (subCategoryItem.getIsChecked()
                        .equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {

                    countIsChecked++;
                }
                childArrayList.add(mapChild);
            }

            if (countIsChecked == data.getSubCategory().size()) {

                data.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
            } else {
                data.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            }

            mapParent.put(ConstantManager.Parameter.IS_CHECKED, data.getIsChecked());
            childItems.add(childArrayList);
            parentItems.add(mapParent);

        }

        ConstantManager.parentItems = parentItems;
        ConstantManager.childItems = childItems;

        myCategoriesExpandableListAdapter = new MyCategoriesExpandableListAdapter(getActivity(), parentItems, childItems, false);
        lvCategory.setAdapter(myCategoriesExpandableListAdapter);
    }
}