package com.kushal.onlineclothingshopping.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kushal.onlineclothingshopping.Commons;
import com.kushal.onlineclothingshopping.R;
import com.kushal.onlineclothingshopping.api.UsersAPI;
import com.kushal.onlineclothingshopping.url.URL;

import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText regUsername,regPassword,regFname,regLname;
    private Button btnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        regFname = view.findViewById(R.id.regFname);
        regLname = view.findViewById(R.id.regLname);
        regUsername = view.findViewById(R.id.regUsername);
        regPassword = view.findViewById(R.id.regPassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        return view;
    }

    private void registerUser(){

        String givenUsername,givenPassword,givenFname,givenLname;

        givenFname = regFname.getText().toString();
        givenLname = regLname.getText().toString();
        givenUsername = regUsername.getText().toString();
        givenPassword = regPassword.getText().toString();

        UsersAPI usersAPI= URL.getRetrofitInstance().create(UsersAPI.class);
        Call<Void> voidCall = usersAPI.registerUser(givenFname,givenLname,givenUsername,givenPassword);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if ( !response.isSuccessful() ){
                    Commons.alert(getContext(),"Code: "+response.code());
                    return;
                }
                Commons.alert(getContext(),"You have been registered sucessfully! Login now.");
                regFname.setText("");
                regLname.setText("");
                regUsername.setText("");
                regPassword.setText("");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Commons.alert(getContext(),"An error occured during registration process.");
            }
        });
    }

    @Override
    public void onClick(View v) {

        if( regUsername.getText().toString().length() > 0 && regPassword.getText().toString().length() > 0 ){
            registerUser();
        }else{
            Commons.alert(getContext(),"Please enter username and password to register.");
        }
    }
}
