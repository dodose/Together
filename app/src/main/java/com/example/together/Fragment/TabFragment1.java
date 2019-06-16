package com.example.together.Fragment;

import android.content.Intent;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        final String etp_cd= getArguments().getString("code"); // 업체코드 가져오기

        Log.e("frgment1",etp_cd);

        btn = view.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodbyepetReservationResultActivity.class);
                startActivity(intent);
            }
        });

        setupReferences(view);
        return view;
    }

    private void setupReferences(View view) {

        lvCategory = view.findViewById(R.id.lvCategory);
        arCategory = new ArrayList<>(); // 큰 상품들의 array 배열

        arSubCategory = new ArrayList<>();
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();


        // 1
        DataItem dataItem = new DataItem();
        dataItem.setCategoryId("1");
        dataItem.setCategoryName("수의");
        arSubCategory = new ArrayList<>();
        for (int i = 1; i < 6; i++) {

            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(i));
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName("수의 상품들의 array" + i);
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