package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class NotificationSend extends StringRequest {
    private static final String LOGIN_URL = "http://www.unikazani.com/json/notification_send.php";

    private Map <String, String> params;

    public NotificationSend(String userId,String title,String desc, Response.Listener<String> listener) {

        super(Method.POST, LOGIN_URL, listener, null);

        params = new HashMap<>();
        params.put("id",userId);
        params.put("title", title);
        params.put("desc", desc);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
