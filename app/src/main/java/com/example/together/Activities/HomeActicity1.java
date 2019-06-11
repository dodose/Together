//package com.example.together.Activities;
//
//import android.accounts.Account;
//import android.os.Bundle;
//import android.support.design.internal.BottomNavigationItemView;
//import android.support.design.widget.BottomSheetDialog;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//
//import com.example.together.Common.Common;
//import com.example.together.R;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class HomeActicity1 extends AppCompatActivity {
//    @BindView(R.id.bottom_navigation)
//    BottomNavigationItemView bottomNavigationItemView;
//
//    BottomSheetDialog bottomSheetDialog;
//
//    //CollectionReference userRef;
//
//    AlertDialog dialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        ButterKnife.bind(HomeActicity1.this); //뭔가 이거 같은데
//
//        //Init
//        //userRef = FirebaseFirestore.getInstance().collection("User");
//        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
//
//        //Check intent, if is login =true, enable full access
//        //If is login = false, just let user around shopping to view
//
//        /**/if(getInstance()!=null) {
//            boolean isLogin = getIntent().getBooleanExtra(Common.IS_LOGIN, false);
//
//            if(isLogin)
//            {
//                dialog.show();
//
//                //Check if user is exists
//                AccoutKit.getCurrentAccount(new AccountKitCallback<Account>()) {
//
//            }
//            }
//        }
//
//    }
//
//
//
//    protected boolean loadFragment(Fragment fragment) {
//
//    }
//    protected void showUpdateDialog(final String phoneNumber) {
//
//    }
//}
