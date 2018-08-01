package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class Top10JSON extends StringRequest {
    private static final String LOGIN_URL = "http://www.unikazani.com/json/top10.php";

    private Map <String, String> params;

    public Top10JSON(String userId, Response.Listener<String> listener) {

        super(Method.POST, LOGIN_URL, listener, null);
        params = new HashMap<>();
        params.put("id",userId);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
