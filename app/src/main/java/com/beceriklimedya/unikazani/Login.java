package com.beceriklimedya.unikazani;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.JSON.LoginJSON;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;

    private String token = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.login_username);
        txtPassword = findViewById(R.id.login_password);
        Button btnLogin = findViewById(R.id.login_login);

        SharedPreferences token_at = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = token_at.getString("token", "N/A");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(txtUsername.getText().toString().trim(), txtPassword.getText().toString().trim(), token);
            }
        });

    }

    private void login(final String userName, final String password, String token)
    {

        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Yükleniyor")
                .setDetailsLabel("Giriş yapılıyor...");

        hud.show();

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);
                    hud.dismiss();

                    Integer error = jsonresponse.getInt("error");

                    if (error == 0) {

                        String userId = jsonresponse.getString("id");
                        String auth = jsonresponse.getString("auth");
                        String profile = jsonresponse.getString("photo");

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("userId", userId);
                        editor.putString("remember", "1");
                        editor.putString("auth", auth);
                        editor.putString("profile", profile);
                        editor.putString("username", userName);
                        editor.putString("password", password);
                        editor.commit();

                        startActivity(new Intent(Login.this,MainScreen.class));
                        overridePendingTransition(R.anim.ileri1,R.anim.ileri2);

                    }
                    else {
                        Alerter.create(Login.this)
                                .setBackgroundColorRes(android.R.color.holo_red_light)
                                .setTitle("Hata!")
                                .setText("Kullanıcı bilgileri hatalı!")
                                .enableProgress(true)
                                .setProgressColorRes(android.R.color.white)
                                .show();
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        LoginJSON loginrequest = new LoginJSON(userName,password,token,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
