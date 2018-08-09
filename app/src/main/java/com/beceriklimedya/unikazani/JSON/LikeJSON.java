package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class LikeJSON extends StringRequest {
    private static final String LOGIN_URL = "http://www.unikazani.com/json/like.php";

    private Map<String, String> params;

    public LikeJSON(String userId, String data, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_URL, listener, null);
        // İşlemler için kullanılacak veriler hazırlanır.
        params = new HashMap<>();
        params.put("id", userId);
        params.put("share_id", data);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
