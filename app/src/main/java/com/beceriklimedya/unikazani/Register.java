package com.beceriklimedya.unikazani;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText registerName = findViewById(R.id.register_name);
        EditText registerUsername = findViewById(R.id.register_username);
        EditText registerPassword = findViewById(R.id.register_password);
        EditText registerMail = findViewById(R.id.register_email);
        Button btnRegister = findViewById(R.id.register_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,MainScreen.class));
            }
        });
    }
}
