package com.example.together.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.example.together.activities.LoginActivity;
import com.example.together.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    Toolbar myToolbar;

    AppCompatActivity activity;

    MypageFragment1 fragment1;
    MypageFragment2 fragment2;
    MypageFragment3 fragment3;

    TextView username;

    private DrawerLayout mDrawerLayout;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        //툴바 선언
        myToolbar = view.findViewById(R.id.toolbar);
        username = view.findViewById(R.id.username);



        activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(myToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        //액션바 왼쪽에 버튼
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");


        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.bar);

        fragment1 = new MypageFragment1(username);
        fragment2 = new MypageFragment2(username);
        fragment3 = new MypageFragment3();

        activity.getSupportFragmentManager().beginTransaction().add(R.id.setlayoutfrag,fragment1).commit();

        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                Log.e("d", id + "");

                switch (id) {
                    case R.id.navigation_item_attachment:
                        String A = "1";
                        switchFragment(A);
                        break;

                    case R.id.navigation_item_order:
                        String B = "2";
                        switchFragment(B);
                        break;

                    case R.id.navigation_item_customer:
                        String C = "3";
                        switchFragment(C);
                        break;

                    case R.id.navigation_item_logout:

                        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(activity);
                        alert_confirm.setMessage("정말로 로그아웃 하시겠어요?").setCancelable(false).setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 'YES'
                                   FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(activity, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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

                        break;


                }

                return false;
            }
        });



        return view;
    }




    public void switchFragment(String value){
        Fragment selected = null;

        if (value == "1") {
            selected = new MypageFragment1(username);
        } else if(value == "2") {
            selected = new MypageFragment2(username);
        } else if (value == "3"){
            selected = fragment3;
        }

        activity.getSupportFragmentManager().beginTransaction().replace(R.id.setlayoutfrag, selected).commit();
    }



    //액션바 등록
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.hotel_menu, menu) ;
        super.onCreateOptionsMenu(menu,inflater);
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
