package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class CommentAddJSON extends StringRequest {
    private static final String LOGIN_URL = "http://www.unikazani.com/json/comment_insert.php";

    private Map<String, String> params;

    public CommentAddJSON(String share_id, String id, String comment, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_URL, listener, null);
        // İşlemler için kullanılacak veriler hazırlanır.
        params = new HashMap<>();
        params.put("share_id", share_id);
        params.put("id", id);
        params.put("comment", comment);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
