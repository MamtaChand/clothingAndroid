package com.kushal.onlineclothingshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.kushal.onlineclothingshopping.adapters.ItemsAdapter;
import com.kushal.onlineclothingshopping.api.ItemsAPI;
import com.kushal.onlineclothingshopping.models.Item;
import com.kushal.onlineclothingshopping.url.URL;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private Button addItemOpener,btnLogout;
    private RecyclerView allItemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


//        //if not logged in go to login activity
//        if( !Commons.loggedIn ){
//            Intent intent = new Intent(getApplicationContext(),LoginRegisterActivity.class);
//            intent.putExtra("noLogIn",true);
//            startActivity(intent);
//        }
//
//        //check if any extras have been sent
//        Bundle extras = getIntent().getExtras();
//        String parameter;
//        if(extras != null) {
//            Commons.alert(getApplicationContext(),extras.getString("msg"));
//        }

        init();

    }

    private void init(){

        addItemOpener = findViewById(R.id.addItemOpener);
        addItemOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddItemActivity.class);
                startActivity(intent);
            }
        });

        allItemsView = findViewById(R.id.recyclerView);
        allItemsView.setLayoutManager(new LinearLayoutManager(this));

        ItemsAPI itemsAPI = URL.getRetrofitInstance().create(ItemsAPI.class);
        Call<List<Item>> listCall = itemsAPI.getAllItems();
        listCall.enqueue(new Callback<List<Item>>() {

            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                List<Item> itemsAll = response.body();
                ItemsAdapter itemsAdapter= new ItemsAdapter(DashboardActivity.this,itemsAll);
                allItemsView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));
                allItemsView.setAdapter(itemsAdapter);
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Commons.alert(DashboardActivity.this,"Something went wrong while fetching items.");
                Log.d("mesage", "onFailure: "+t.getMessage());
            }
        });


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
}
