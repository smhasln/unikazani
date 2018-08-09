package com.beceriklimedya.unikazani;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.JSON.ProfileSetJSON;
import com.beceriklimedya.unikazani.JSON.ProfileSettingsJSON;
import com.beceriklimedya.unikazani.JSON.SearchJSON;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

public class ProfileSettings extends AppCompatActivity {

    private ArrayList<String> SearchUniName = new ArrayList<>();
    private ArrayList<String> SearchUniId = new ArrayList<>();

    private FloatingActionButton settingsApply;
    private TextView settingsUni;
    private RadioButton settingsMale;
    private RadioButton settingsFemale;
    private EditText settingsBirthday;
    private Button settingsChange;
    private CircleImageView settingsPhoto;

    private ImageButton settingsBack;

    private String sex;

    private String selected_id;
    private String userId;
    private String profile;

    private String img64 = "0";
    private String uniName;

    private int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        Button logout = findViewById(R.id.logout);
        settingsApply = findViewById(R.id.settingsApply);
        settingsUni = findViewById(R.id.settingsUni);
        settingsMale = findViewById(R.id.settingsMale);
        settingsFemale = findViewById(R.id.settingsFemale);
        settingsBirthday = findViewById(R.id.settingsBirthday);
        settingsChange = findViewById(R.id.settingsChange);
        settingsPhoto = findViewById(R.id.settingsPhoto);
        settingsBack = findViewById(R.id.settingsBack);
        settingsChange = findViewById(R.id.settingsChange);

        getUni();
        fill();

        settingsChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(ProfileSettings.this);
                builder1.setTitle("Fotoğraf Ekleyin");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Galeri",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                img64 = "0";

                                showFileChooser();
                            }
                        });
                builder1.setNegativeButton("Kamera",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                img64 = "0";

                                RxImagePicker.with(ProfileSettings.this).requestImage(Sources.CAMERA).subscribe(new Consumer<Uri>() {
                                    @Override
                                    public void accept(@NonNull Uri uri) throws Exception {

                                        final InputStream imageStream = getContentResolver().openInputStream(uri);
                                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                                        Bitmap btmp = Bitmap.createScaledBitmap(selectedImage, 500, 500, false);

                                        settingsPhoto.setImageBitmap(btmp);
                                        img64 = encodeImage(btmp);

                                    }
                                });
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        settingsMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sex = "1";
                    settingsFemale.setChecked(false);
                }
            }
        });
        settingsFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sex = "2";
                    settingsMale.setChecked(false);
                }
            }
        });
        settingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileSettings.super.onBackPressed();

                overridePendingTransition(R.anim.geri1, R.anim.geri2);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("userId", "");
                editor.remove("remember");
                editor.putString("remember", "0");
                editor.putString("auth", "");
                editor.putString("username", "");
                editor.putString("password", "");
                editor.commit();

                startActivity(new Intent(ProfileSettings.this, MainScreen.class));
                overridePendingTransition(R.anim.ileri1, R.anim.ileri2);
            }
        });

        settingsApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply();
            }
        });


        settingsUni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Filtrele
                final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettings.this);
                View mView = getLayoutInflater().inflate(R.layout.callspinner, null);
                builder.setTitle("Okuduğum üniversite");

                final Spinner spinner = mView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileSettings.this, android.R.layout.simple_spinner_dropdown_item, SearchUniName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);


                //Hangi opsiyon seçildiyse tespit edilir işlemler yapılır.
                builder.setPositiveButton("Seç", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected_id = SearchUniId.get(spinner.getSelectedItemPosition());
                        settingsUni.setText(SearchUniName.get(spinner.getSelectedItemPosition()));

                    }
                });
                builder.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Dialog penceresi oluşturulur.
                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }


    private String encodeImage(Bitmap bm)
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,80,baos);

        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Resim ekleyin"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView

                Bitmap btmp = Bitmap.createScaledBitmap(bitmap, 500, 500, false);

                settingsPhoto.setImageBitmap(bitmap);
                img64 = encodeImage(btmp);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void fill()
    {

        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Yükleniyor")
                .setDetailsLabel("Bilgiler getiriliyor...");

        hud.show();

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    String birthday = jsonresponse.getString("birthday");
                    String sex = jsonresponse.getString("sex");
                    selected_id = jsonresponse.getString("university_id");
                    uniName = jsonresponse.getString("university_name");
                    profile = jsonresponse.getString("photo");

                    if (uniName.equals("null"))
                    {

                        settingsUni.setText("Belirtilmemiş");
                    }
                    else
                    {

                        settingsUni.setText(uniName);
                    }
                    settingsBirthday.setText(birthday);

                    if (sex.equals("1"))
                    {
                        settingsMale.setChecked(true);
                    }
                    else
                    {
                        settingsFemale.setChecked(true);
                    }

                    Picasso.get()
                            .load("http://www.unikazani.com/json/upload/" + profile + ".jpg")
                            .into(settingsPhoto);

                    img64 = profile;

                    hud.dismiss();

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        ProfileSetJSON loginrequest = new ProfileSetJSON(userId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(ProfileSettings.this);
        queue.add(loginrequest);
    }

    private void apply()
    {

        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Güncelleniyor");

        hud.show();

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    Integer error = jsonresponse.getInt("error");

                    if (error == 0)
                    {

                        String photo = jsonresponse.getString("photo");

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("profile", photo);

                        editor.commit();

                        hud.dismiss();
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        ProfileSettingsJSON loginrequest = new ProfileSettingsJSON(userId,img64,settingsBirthday.getText().toString(),selected_id,sex,responselistener);
        RequestQueue queue = Volley.newRequestQueue(ProfileSettings.this);
        queue.add(loginrequest);
    }

    private void getUni()
    {

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    JSONArray university_id = jsonresponse.getJSONArray("university_id");
                    JSONArray university_name = jsonresponse.getJSONArray("university_name");

                    for (int i = 0; i < university_id.length(); i++){
                        SearchUniId.add(i, university_id.get(i).toString());
                        SearchUniName.add(i, university_name.get(i).toString());
                    }


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        SearchJSON loginrequest = new SearchJSON(userId,"10","99",responselistener);
        RequestQueue queue = Volley.newRequestQueue(ProfileSettings.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
