package com.kushal.onlineclothingshopping.api;

import com.kushal.onlineclothingshopping.models.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsersAPI {

    @GET("api/users")
    Call<List<User>> getUsers();

    @FormUrlEncoded
    @POST("api/users")
    Call<Void> registerUser(
            @Field("userFname") String userFname,
            @Field("userLname") String userLname,
            @Field("username") String username,
            @Field("password") String password
    );

}
