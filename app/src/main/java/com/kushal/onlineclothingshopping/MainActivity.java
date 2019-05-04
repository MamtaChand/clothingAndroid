package com.kushal.onlineclothingshopping;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kushal.onlineclothingshopping.fragments.LoginFragment;
import com.kushal.onlineclothingshopping.fragments.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    private Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetStarted = findViewById(R.id.btnGetStarted);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !Commons.loggedIn ){
                    Intent intent = new Intent(getApplicationContext(),LoginRegisterActivity.class);
                    intent.putExtra("noLogIn",true);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
                    intent.putExtra("Welcome!",true);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

}
