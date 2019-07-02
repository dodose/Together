package com.example.together.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.together.activities.goodbyePet.ConstantManager;
import com.example.together.activities.goodbyePet.GoodbyepetReservationResultActivity;
import com.example.together.activities.goodbyePet.MyCategoriesExpandableListAdapter;
import com.example.together.model.DataItem;
import com.example.together.model.SubCategoryItem;
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




    private ArrayList<HashMap<String, String>> parentItems;
    private ArrayList<ArrayList<HashMap<String, String>>> childItems;
    private MyCategoriesExpandableListAdapter myCategoriesExpandableListAdapter;

    //서블릿 변수선언

    private URL Url;
    private String strUrl;

    JSONObject jobj;

    int count =  0;


    ArrayList<SubCategoryItem> f_s; //수의 담는배열
    ArrayList<SubCategoryItem> f_h; //함 담는 배열
    ArrayList<SubCategoryItem> f_g; //관 담는 배열
    ArrayList<SubCategoryItem> f_f; //화장비 담는 배열

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        final String etp_cd= getArguments().getString("code"); // 업체코드 가져오기
        final String day= getArguments().getString("day"); // 이전에선택햇던 날짜값
        final String time= getArguments().getString("time"); // 이전에 선택했던 시간값






    //서블릿 통신
    new AsyncTask<Void, Void, JSONObject>() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            strUrl = "http://39.127.7.80:8080/Funeral_Detail"; //탐색하고 싶은 URL이다.

        }

        @Override
        protected JSONObject doInBackground(Void... voids) {


            f_s = new ArrayList<>();
            f_h = new ArrayList<>();
            f_g = new ArrayList<>();
            f_f = new ArrayList<>();


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


                Log.e("result", jobj + "");


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

            JSONObject jsonObject = null;
            try {

                JSONArray jsonArray = (JSONArray) jobj.get("result");

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    if (i == 0) {
                        String pd_nm;
                        String pd_img;
                        String pd_content;
                        String pd_price;
                        JSONArray sarray = (JSONArray) jsonObject.get("수의");
                        for (int s = 0; s < sarray.length(); s++) {
                            JSONObject sjobj = sarray.getJSONObject(s);
                            pd_nm = sjobj.getString("etp_pd_nm");
                            pd_img = sjobj.getString("etp_img_path");
                            pd_content = sjobj.getString("etp_pd_content");
                            pd_price = sjobj.getString("etp_pd_price");
                            Log.e("수의 imageURL", pd_img);

                            f_s.add(new SubCategoryItem(pd_nm, pd_img, pd_content, pd_price)); //array 배열에 넣음
                        }


                    } else if (i == 1) {

                        String pd_nm;
                        String pd_img;
                        String pd_content;
                        String pd_price;
                        JSONArray sarray = (JSONArray) jsonObject.get("함");
                        //                            Log.e("sarray",sarray+"");
                        for (int s = 0; s < sarray.length(); s++) {
                            JSONObject sjobj = sarray.getJSONObject(s);
                            pd_nm = sjobj.getString("etp_pd_nm");
                            pd_img = sjobj.getString("etp_img_path");
                            pd_content = sjobj.getString("etp_pd_content");
                            pd_price = sjobj.getString("etp_pd_price");

//                                            Log.e("수의",pd_nm + "//" + pd_price);

                            f_h.add(new SubCategoryItem(pd_nm, pd_img, pd_content, pd_price)); //함 배열 add
                        }
                    } else if (i == 2) {

                        String pd_nm;
                        String pd_img;
                        String pd_content;
                        String pd_price;
                        JSONArray sarray = (JSONArray) jsonObject.get("관");

                        for (int s = 0; s < sarray.length(); s++) {
                            JSONObject sjobj = sarray.getJSONObject(s);
                            pd_nm = sjobj.getString("etp_pd_nm");
                            pd_img = sjobj.getString("etp_img_path");
                            pd_content = sjobj.getString("etp_pd_content");
                            pd_price = sjobj.getString("etp_pd_price");


                            f_g.add(new SubCategoryItem(pd_nm, pd_img, pd_content, pd_price)); //관 배열 add
                        }
                    } else {

                        String pd_nm;
                        String pd_img;
                        String pd_content;
                        String pd_price;

                        JSONArray farray = (JSONArray) jsonObject.get("화장");
                        for (int f = 0; f < farray.length(); f++) {
                            JSONObject sjobj = farray.getJSONObject(f);
                            pd_nm = sjobj.getString("etp_pd_nm");
                            pd_img = sjobj.getString("etp_img_path");
                            pd_content = sjobj.getString("etp_pd_content");
                            pd_price = sjobj.getString("etp_pd_price");

                            //                                Log.e("수의",pd_nm + "//" + pd_price);
                            //해당 모델 객체에 add하기
                            f_f.add(new SubCategoryItem(pd_nm, pd_img, pd_content, pd_price));
                        }
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(f_s != null) {

                setupReferences(view, f_s, f_h, f_g, f_f);
            }else{
                return;
            }
        }

    }.execute();



        btn = view.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodbyepetReservationResultActivity.class);
                intent.putExtra("day",day);
                intent.putExtra("time",time);
                intent.putExtra("code",etp_cd);
                startActivity(intent);
            }
        });



        return view;
    }

    public void setupReferences(View view, ArrayList<SubCategoryItem> f_s, ArrayList<SubCategoryItem> f_h, ArrayList<SubCategoryItem> f_g, ArrayList<SubCategoryItem> f_f) {

        lvCategory = view.findViewById(R.id.lvCategory);
        arCategory = new ArrayList<>(); // 큰 상품들의 array 배열

        arSubCategory = new ArrayList<>();
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();


        // 1
        DataItem dataItem = new DataItem();
        dataItem.setCategoryId("1");
        dataItem.setCategoryName("수의");
//        arSubCategory = new ArrayList<>();
        for (int i = 0; i <f_s.size(); i++) {
            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(i));
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName(f_s.get(i).getPd_nm());
            subCategoryItem.setPd_price(f_s.get(i).getPd_price());
            subCategoryItem.setPd_img(f_s.get(i).getPd_img());
            arSubCategory.add(subCategoryItem);
        }
        dataItem.setSubCategory(arSubCategory);
        arCategory.add(dataItem);



        // 2
        dataItem = new DataItem();
        dataItem.setCategoryId("2");
        dataItem.setCategoryName("함");
        arSubCategory = new ArrayList<>();
        for (int j = 0; j < f_h.size(); j++) {

            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(j));
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName(f_h.get(j).getPd_nm());
            subCategoryItem.setPd_price(f_h.get(j).getPd_price());
            subCategoryItem.setPd_img(f_h.get(j).getPd_img());
            arSubCategory.add(subCategoryItem);
        }
        dataItem.setSubCategory(arSubCategory);
        arCategory.add(dataItem);


        // 3
        dataItem = new DataItem();
        dataItem.setCategoryId("3");
        dataItem.setCategoryName("관");
        arSubCategory = new ArrayList<>();
        for (int k = 0; k < f_g.size(); k++) {

            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(k));
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName(f_g.get(k).getPd_nm());
            subCategoryItem.setPd_price(f_g.get(k).getPd_price());
            subCategoryItem.setPd_img(f_g.get(k).getPd_img());
            arSubCategory.add(subCategoryItem);
        }
        dataItem.setSubCategory(arSubCategory);
        arCategory.add(dataItem);



        //4
        dataItem = new DataItem();
        dataItem.setCategoryId("4");
        dataItem.setCategoryName("화장비");
        arSubCategory = new ArrayList<>();
        for (int o = 0; o < f_f.size(); o++) {

            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(o));
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName(f_f.get(o).getPd_nm());
            subCategoryItem.setPd_img(f_f.get(o).getPd_img());
            subCategoryItem.setPd_price(f_f.get(o).getPd_price());
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
                mapChild.put(ConstantManager.Parameter.SUB_CATEGORY_NAME, subCategoryItem.getSubCategoryName());
                mapChild.put(ConstantManager.Parameter.SUB_CATEGORY_PRICE, subCategoryItem.getPd_price());
                mapChild.put(ConstantManager.Parameter.SUB_CATEGORY_IMAGE, subCategoryItem.getPd_img());
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