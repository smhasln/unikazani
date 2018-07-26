package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ProfileJSON extends StringRequest {
    private static final String PROFILE_JSON = "http://www.unikazani.com/json/profile.php";

    private Map<String, String> params;

    public ProfileJSON(String userId, Response.Listener<String> listener) {
        super(Method.POST, PROFILE_JSON, listener, null);
        // İşlemler için kullanılacak veriler hazırlanır.
        params = new HashMap<>();
        params.put("id", userId);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
