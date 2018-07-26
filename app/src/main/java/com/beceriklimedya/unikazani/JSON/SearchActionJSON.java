package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class SearchActionJSON extends StringRequest {
    private static final String SEARCH_URL = "http://www.unikazani.com/json/follow.php";

    private Map<String, String> params;

    public SearchActionJSON(String userId,String universityId, Response.Listener<String> listener) {
        super(Method.POST, SEARCH_URL, listener, null);
        // İşlemler için kullanılacak veriler hazırlanır.
        params = new HashMap<>();
        params.put("university_id", universityId);
        params.put("id", userId);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
