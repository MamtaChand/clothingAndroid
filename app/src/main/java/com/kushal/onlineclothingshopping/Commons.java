package com.kushal.onlineclothingshopping;

import android.content.Context;
import android.widget.Toast;

//holds some common mios used functions
public class Commons {

    public static boolean loggedIn = false;

    public static void alert(Context c, String message){
        Toast.makeText(c,message,Toast.LENGTH_LONG).show();
    }

    public static void logout(){
        loggedIn = false;
    }

}
