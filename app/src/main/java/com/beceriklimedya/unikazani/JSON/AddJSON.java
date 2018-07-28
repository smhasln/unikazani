package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class AddJSON extends StringRequest {
    private static final String LOGIN_URL = "http://www.unikazani.com/json/share_insert.php";

    private Map <String, String> params;

    public AddJSON(String userId, String message, String photo, String university_id, String category, String anonim, Response.Listener<String> listener) {

        super(Method.POST, LOGIN_URL, listener, null);

        params = new HashMap<>();
        params.put("id",userId);
        params.put("message", message);
        params.put("photo", photo);
        params.put("university_id", university_id);
        params.put("category", category);
        params.put("anonim", anonim);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
