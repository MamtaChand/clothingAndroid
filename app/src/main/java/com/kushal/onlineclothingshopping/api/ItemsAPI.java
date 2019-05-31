package com.kushal.onlineclothingshopping.api;

import com.kushal.onlineclothingshopping.models.Item;
import com.kushal.onlineclothingshopping.models.ItemResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ItemsAPI {

    @Multipart
    @POST("api/items")
    Call<ItemResponse> addItem(@Part("itemName") RequestBody name, @Part("itemPrice") RequestBody price, @Part MultipartBody.Part itemImage, @Part("itemDescription") RequestBody description);

    @GET("api/items")
    Call<List<Item>> getAllItems();
}
