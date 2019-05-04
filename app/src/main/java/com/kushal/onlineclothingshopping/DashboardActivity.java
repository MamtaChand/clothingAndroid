package com.kushal.onlineclothingshopping;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.kushal.onlineclothingshopping.adapters.ItemsAdapter;
import com.kushal.onlineclothingshopping.models.Item;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private Button addItemOpener,btnLogout;
    private RecyclerView recyclerView;

    private List<Item> allItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //if not logged in go to login activity
        if( !Commons.loggedIn ){
            Intent intent = new Intent(getApplicationContext(),LoginRegisterActivity.class);
            intent.putExtra("noLogIn",true);
            startActivity(intent);
        }

        //check if any extras have been sent
        Bundle extras = getIntent().getExtras();
        String parameter;
        if(extras != null) {
            Commons.alert(getApplicationContext(),extras.getString("msg"));
        }

        init();

    }

    private void init(){
        allItemsList = new ArrayList<>();

        addItemOpener = findViewById(R.id.addItemOpener);
        addItemOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddItemActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        loadItemsIntoList();
        ItemsAdapter itemsAdapter = new ItemsAdapter(this,allItemsList);
        recyclerView.setAdapter(itemsAdapter);

        int spanCount = 1;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
        ){
            spanCount = 2;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this,spanCount));


        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.logout();
                Intent intent = new Intent(getApplicationContext(),LoginRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadItemsIntoList() {
        try {
            FileInputStream fileInputStream = openFileInput("items.txt");
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("->");
                Item item = new Item();
                item.setItemName(parts[0]);
                item.setItemPrice(parts[1]);
                item.setItemImageName(toDrawable(parts[2]));
                item.setItemDescription(parts[3]);
                allItemsList.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //converts file name with string value to drawable int form
    public static int toDrawable(String itemName){

        int drawableInt = 0;
        switch (itemName){
            //10 items
            case "kurtha":
                drawableInt = R.drawable.kurtha;
                break;
            case "pant":
                drawableInt = R.drawable.pant;
                break;
            case "pantkneecut":
                drawableInt = R.drawable.pantkneecut;
                break;
            case "shirt":
                drawableInt = R.drawable.shirt;
                break;
            case "suit":
                drawableInt = R.drawable.suit;
                break;
            case "jacket":
                drawableInt = R.drawable.jacket;
                break;
            case "shoe":
                drawableInt = R.drawable.shoe;
                break;
            case "slipper":
                drawableInt = R.drawable.slipper;
                break;
            case "sweater":
                drawableInt = R.drawable.sweater;
                break;
            case "socks":
                drawableInt = R.drawable.socks;
                break;
        }

        return drawableInt;
    }

}
