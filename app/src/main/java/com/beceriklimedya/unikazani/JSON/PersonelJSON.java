package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class PersonelJSON extends StringRequest {
    private static final String SEARCH_URL = "http://www.unikazani.com/json/personel.php";

    public PersonelJSON(Response.Listener<String> listener) {
        super(Method.GET, SEARCH_URL, listener, null);
    }

}
