package com.example.together.Activities.Petching;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.together.R;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class FindFriend_Fragment extends Fragment {

    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    private int i;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_find_friend, container, false);
        return view;

//
//        al = new ArrayList<>();
//        al.add("php");
//        al.add("c");
//        al.add("python");
//        al.add("java");
//        al.add("html");
//        al.add("c++");
//        al.add("css");
//        al.add("javascript");
//
//        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );
//
//        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);
//
//        flingContainer.setAdapter(arrayAdapter);
//        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
//            @Override
//            public void removeFirstObjectInAdapter() {
//                // this is the simplest way to delete an object from the Adapter (/AdapterView)
//                Log.d("LIST", "removed object!");
//                al.remove(0);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onLeftCardExit(Object dataObject) {
//                //Do something on the left!
//                //You also have access to the original object.
//                //If you want to use it just cast it (String) dataObject
//               Toast.makeText(FindFriend_Fragment.this, "left", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRightCardExit(Object dataObject) {
//                makeToast(MyActivity.this, "Right!");
//            }
//
//            @Override
//            public void onAdapterAboutToEmpty(int itemsInAdapter) {
//                // Ask for more data here
//                al.add("XML ".concat(String.valueOf(i)));
//                arrayAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
//            }
//
//            @Override
//            public void onScroll(float scrollProgressPercent) {
//                View view = flingContainer.getSelectedView();
//                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
//                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
//            }
//        });
//
//
//        // Optionally add an OnItemClickListener
//        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClicked(int itemPosition, Object dataObject) {
//                makeToast(MyActivity.this, "Clicked!");
//            }
//        });
//
//    }
//
//    static void makeToast(Context ctx, String s){
//        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
//    }
//
//
//    @OnClick(R.id.right)
//    public void right() {
//        /**
//         * Trigger the right event manually.
//         */
//        flingContainer.getTopCardListener().selectRight();
//    }
//
//    @OnClick(R.id.left)
//    public void left() {
//        flingContainer.getTopCardListener().selectLeft();
//    }
    }



}


