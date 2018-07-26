package com.beceriklimedya.unikazani.JSON;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ProfileSettingsJSON extends StringRequest {
    private static final String PROFILE_SETTINGS_URL = "http://www.unikazani.com/json/profile_settings.php";

    private Map<String, String> params;

    public ProfileSettingsJSON(String userId,String image, String birthday, String university, String sex,Response.Listener<String> listener) {
        super(Method.POST, PROFILE_SETTINGS_URL, listener, null);
        // İşlemler için kullanılacak veriler hazırlanır.
        params = new HashMap<>();
        params.put("id", userId);
        params.put("image", image);
        params.put("birthday", birthday);
        params.put("university", university);
        params.put("sex", sex);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
