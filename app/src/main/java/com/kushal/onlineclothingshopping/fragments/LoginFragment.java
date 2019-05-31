package com.kushal.onlineclothingshopping.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.kushal.onlineclothingshopping.Commons;
import com.kushal.onlineclothingshopping.DashboardActivity;
import com.kushal.onlineclothingshopping.R;
import com.kushal.onlineclothingshopping.api.UsersAPI;
import com.kushal.onlineclothingshopping.models.User;
import com.kushal.onlineclothingshopping.url.URL;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText loginUsername,loginPassword;
    private Button btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginUsername = view.findViewById(R.id.loginUsername);
        loginPassword = view.findViewById(R.id.loginPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        return  view;
    }

    private void loginUser(){
        final String givenUsername,givenPassword,dbUsername,dbPassword;
        givenUsername = loginUsername.getText().toString();
        givenPassword = loginPassword.getText().toString();

        UsersAPI usersAPI = URL.getRetrofitInstance().create(UsersAPI.class);
        final Call<List<User>> userCall = usersAPI.getUsers();

        userCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> dbUsers = response.body();
                User currentDbUser = null;
                for (int i = 0; i < dbUsers.size(); i++) {
                    User currentUser = dbUsers.get(i);
                    if (currentUser.getUsername().equals(givenUsername)) {
                        currentDbUser = currentUser;
                    }
                }

                if (currentDbUser.getUsername().equals(givenUsername) && currentDbUser.getPassword().equals(givenPassword)) {
                    Intent intent = new Intent(getContext(), DashboardActivity.class);
                    intent.putExtra("msg","Welcome! "+currentDbUser.getUsername());
                    Commons.loggedIn = true;
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Commons.alert(getContext(), "Invalid login details!");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("", "onResponse: "+t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if( loginUsername.getText().toString().length() > 0 && loginPassword.getText().toString().length() > 0 ) {
            loginUser();
        }else{
            Commons.alert(getContext(),"Please enter username and password to login.");
        }
    }
}
