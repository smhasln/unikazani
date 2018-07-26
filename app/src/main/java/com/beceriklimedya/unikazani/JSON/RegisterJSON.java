package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegisterJSON extends StringRequest {
    private static final String REGISTER_URL = "http://www.unikazani.com/json/register.php";

    private Map<String, String> params;

    public RegisterJSON(String name, String userName, String password, String mail, String token, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_URL, listener, null);
        // İşlemler için kullanılacak veriler hazırlanır.
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", userName);
        params.put("mail", mail);
        params.put("password", password);
        params.put("token", token);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
