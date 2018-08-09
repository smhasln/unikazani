package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.beceriklimedya.unikazani.R;

import java.util.HashMap;
import java.util.Map;


public class LoginJSON extends StringRequest {

    private static final String LOGIN_URL = "http://www.unikazani.com/json/login.php";

    private Map<String, String> params;

    public LoginJSON(String userName, String password, String token, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_URL, listener, null);
        // İşlemler için kullanılacak veriler hazırlanır.
        params = new HashMap<>();
        params.put("username", userName);
        params.put("password", password);
        params.put("token", token);
        params.put("mobile","1");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
