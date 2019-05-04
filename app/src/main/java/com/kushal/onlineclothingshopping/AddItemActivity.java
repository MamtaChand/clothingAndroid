package com.kushal.onlineclothingshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.PrintStream;

public class AddItemActivity extends AppCompatActivity {

    private EditText itemName,itemPrice,itemDescription;
    private Button btnAddItem,openDashboard;
    private Spinner itemImageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = findViewById(R.id.itemName);
        itemDescription = findViewById(R.id.itemDescription);
        itemPrice = findViewById(R.id.itemPrice);
        btnAddItem = findViewById(R.id.btnAddItem);
        itemImageName = findViewById(R.id.itemImageName);
        initSpinner();

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                        itemName.getText().toString().length() > 0
                        && itemPrice.getText().toString().length() > 0
                        && itemDescription.getText().toString().length() > 0

                ){
                    addItem();
                }else {
                    Commons.alert(getApplicationContext(),"Please enter all item properties to add item.");
                }
            }
        });

        openDashboard = findViewById(R.id.openDashboard);
        openDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItemActivity.this,DashboardActivity.class);
                startActivity(intent);
            }
        });

    }


    //initialize spinner with image names dropdown
    private void initSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.itemImageName);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.clothesImageArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void addItem(){
        try{
            PrintStream printStream = new PrintStream(openFileOutput("items.txt",MODE_PRIVATE | MODE_APPEND));
            printStream.println(getEncodedItemString());
            Commons.alert(getApplicationContext(),"Item saved successfully.");
            clearTextFields();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getEncodedItemString(){
        String imageName = itemImageName.getSelectedItem().toString();
        String itemDetail = itemName.getText().toString()+"->"+itemPrice.getText().toString()+"->"+
                            imageName+"->"+itemDescription.getText().toString();
        return itemDetail;
    }

    private void clearTextFields(){
        itemName.setText("");
        itemDescription.setText("");
        itemPrice.setText("");
    }
}
