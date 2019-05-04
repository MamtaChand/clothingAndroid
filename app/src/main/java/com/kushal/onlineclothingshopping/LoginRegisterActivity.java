package com.kushal.onlineclothingshopping;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kushal.onlineclothingshopping.adapters.ViewPagerAdapter;
import com.kushal.onlineclothingshopping.fragments.LoginFragment;
import com.kushal.onlineclothingshopping.fragments.RegisterFragment;

//login register or view pager activity
public class LoginRegisterActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);


        //check if any extras have been sent
        Bundle extras = getIntent().getExtras();
        String parameter;
        if(extras == null) {
            parameter= null;
        } else {
            //dashboard cannot be accessed without logging in
            if( extras.getBoolean("noLogIn") == true ){
                Commons.alert(getApplicationContext(),"Please login to access dahsboard.");
            }
        }

        viewPager = findViewById(R.id.theViewPager);
        tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new LoginFragment(),"Login");
        viewPagerAdapter.addFragment(new RegisterFragment(),"Register");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
