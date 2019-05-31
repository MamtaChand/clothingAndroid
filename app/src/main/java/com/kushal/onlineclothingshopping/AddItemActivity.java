package com.kushal.onlineclothingshopping;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kushal.onlineclothingshopping.api.ItemsAPI;
import com.kushal.onlineclothingshopping.models.ItemResponse;
import com.kushal.onlineclothingshopping.url.URL;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class AddItemActivity extends AppCompatActivity {

    private EditText itemName,itemPrice,itemDescription;
    private Button btnAddItem,openDashboard;
    private ImageView itemImageName;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = findViewById(R.id.itemName);
        itemDescription = findViewById(R.id.itemDescription);
        itemPrice = findViewById(R.id.itemPrice);
        btnAddItem = findViewById(R.id.btnAddItem);
        itemImageName = findViewById(R.id.itemImageName);

        itemImageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

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

    private void BrowseImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode == RESULT_OK ){
            if (data == null){
                Commons.alert(this,"Please select an image!");
                return;
            }
        }

        Uri uri = data.getData();
        imagePath = getRealPathFromUri(uri);
        previewImage(imagePath);

    }

    private void previewImage(String imagePath) {

        File imgFile = new File(imagePath);
        if (imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            itemImageName.setImageBitmap(bitmap);
        }

    }
    private String getRealPathFromUri(Uri uri) {
        String[] projection ={MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(getApplicationContext(),uri,projection, null,null,null);
        Cursor cursor= loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result =cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void StrictMode(){
        StrictMode.ThreadPolicy policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void clearTextFields(){
        itemName.setText("");
        itemDescription.setText("");
        itemPrice.setText("");
    }

    private void addItem(){
        MultipartBody.Part imageBodyPart = getItemImageMultiPartBody();

        //item values
        RequestBody itemNameReq = RequestBody.create(MediaType.parse("multipart/form-data"),itemName.getText().toString());
        RequestBody itemPriceReq = RequestBody.create(MediaType.parse("multipart/form-data"),itemPrice.getText().toString());
        RequestBody itemDescriptionReq = RequestBody.create(MediaType.parse("multipart/form-data"),itemDescription.getText().toString());

        ItemsAPI itemsAPI = URL.getRetrofitInstance().create(ItemsAPI.class);
        Call<ItemResponse> itemCall = itemsAPI.addItem(
                itemNameReq,
                itemPriceReq,
                imageBodyPart,
                itemDescriptionReq
        );

        itemCall.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if ( !response.isSuccessful() ){
                    Commons.alert(AddItemActivity.this,"Code: "+response.code());
                    return;
                }
                Commons.alert(AddItemActivity.this,"Item added!");
                clearTextFields();
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Commons.alert(AddItemActivity.this,"Call failure");
                Log.d("Message : ", "onFailure: "+t.getMessage());
            }
        });

    }

    private MultipartBody.Part getItemImageMultiPartBody(){
        File file = new File(imagePath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("itemImage",file.getName(),requestBody);

        return body;
    }
}
