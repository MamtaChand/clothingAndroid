package com.kushal.onlineclothingshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class DescriptionActivity extends AppCompatActivity {

    CircleImageView circleImg;
    TextView txtName,txtPrice,txtDescription;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        btnBack = findViewById(R.id.backBtn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(in);
            }
        });

        circleImg = findViewById(R.id.imageCircle);
        txtName = findViewById(R.id.name);
        txtPrice = findViewById(R.id.price);
        txtDescription = findViewById(R.id.description);

        //check if any extras have been sent
        Bundle extras = getIntent().getExtras();
        String parameter;
        if(extras != null) {
            String name = extras.getString("name");
            int image = extras.getInt("image");
            String price = extras.getString("price");
            String description = extras.getString("description");

            circleImg.setImageResource(image);
            txtName.setText("Name: "+name);
            txtPrice.setText("Price: £"+price);
            txtDescription.setText("Description: \n"+description);
        }

    }
}
