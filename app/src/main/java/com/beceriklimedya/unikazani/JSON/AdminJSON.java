package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class AdminJSON extends StringRequest {
    private static final String LOGIN_URL = "http://www.unikazani.com/json/admin.php";
    public AdminJSON(Response.Listener<String> listener) {
        super(Method.GET, LOGIN_URL, listener, null);
    }
}
