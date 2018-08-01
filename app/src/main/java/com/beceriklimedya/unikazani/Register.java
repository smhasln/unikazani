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
import com.beceriklimedya.unikazani.JSON.RegisterJSON;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText registerName = findViewById(R.id.register_name);
        final EditText registerUsername = findViewById(R.id.register_username);
        final EditText registerPassword = findViewById(R.id.register_password);
        final EditText registerMail = findViewById(R.id.register_email);
        Button btnRegister = findViewById(R.id.register_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(registerName.getText().toString().trim(),
                        registerUsername.getText().toString().trim(),
                        registerPassword.getText().toString().trim(),
                        registerMail.getText().toString().trim(),
                        token);
            }
        });
    }


    private void register(String name, final String userName, final String password, String mail, String token)
    {

        final ProgressDialog progress = ProgressDialog.show(Register.this, "Oturum açma", "Lütfen bekleyiniz.", true);

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonresponse = new JSONObject(response);
                    progress.dismiss();

                    Integer error = jsonresponse.getInt("error");
                    Log.i("yaz",response);
                    if (error == 0)
                    {

                        String userId = jsonresponse.getString("id");
                        String auth = jsonresponse.getString("auth");
                        String profile = jsonresponse.getString("profile");

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("userId", userId);
                        editor.putString("remember", "1");
                        editor.putString("auth", auth);
                        editor.putString("profile", profile);
                        editor.putString("username", userName);
                        editor.putString("password", password);
                        editor.commit();

                        startActivity(new Intent(Register.this,MainScreen.class));
                        overridePendingTransition(R.anim.ileri1,R.anim.ileri2);
                    }
                    else
                    {
                        Alerter.create(Register.this)
                                .setBackgroundColorRes(android.R.color.holo_red_light)
                                .setTitle("Hata!")
                                .setText("Kullanıcı mevcut!")
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

        RegisterJSON loginrequest = new RegisterJSON(name,userName,password,mail,token,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Register.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
