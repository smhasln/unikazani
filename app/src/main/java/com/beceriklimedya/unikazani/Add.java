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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.SearchAdapter;
import com.beceriklimedya.unikazani.JSON.SearchJSON;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

public class Add extends AppCompatActivity {

    private ArrayList<String> SearchUniName = new ArrayList<>();
    private ArrayList<String> SearchUniId = new ArrayList<>();

    private CircleImageView addProfile;
    private EditText addText;
    private Spinner addUni;
    private CheckBox addAnonim;
    private ImageView addImage;
    private Button addBack;
    private Button addApply;
    private FloatingActionButton addPick;
    private ImageButton addCategory1;
    private ImageButton addCategory2;
    private ImageButton addCategory3;
    private ImageButton addDelete;

    private String imgControl = "0";
    private String img64;

    private String userId;

    private int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        addProfile = findViewById(R.id.addProfile);
        addText = findViewById(R.id.addText);
        addUni = findViewById(R.id.addUniversity);
        addAnonim = findViewById(R.id.addAnonim);
        addImage = findViewById(R.id.addPhoto);
        addBack = findViewById(R.id.addBack);
        addApply = findViewById(R.id.addApply);
        addPick = findViewById(R.id.addPhotoPick);
        addCategory1 = findViewById(R.id.addCategory1);
        addCategory2 = findViewById(R.id.addCategory2);
        addCategory3 = findViewById(R.id.addCategory3);
        addDelete = findViewById(R.id.addDelete);

        addDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgControl = "0";
                img64 = "";

                addImage.setVisibility(View.GONE);
                addDelete.setVisibility(View.GONE);
            }
        });

        getUni();

        addBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add.super.onBackPressed();

                overridePendingTransition(R.anim.geri1,R.anim.geri2);
            }
        });

        addPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Add.this);
                builder1.setTitle("Fotoğraf Ekleyin");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Galeri",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                imgControl = "0";
                                img64 = "";

                                addImage.setVisibility(View.GONE);
                                addDelete.setVisibility(View.GONE);
                                showFileChooser();
                            }
                        });
                builder1.setNegativeButton("Kamera",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                imgControl = "0";
                                img64 = "";

                                addImage.setVisibility(View.GONE);
                                addDelete.setVisibility(View.GONE);
                                RxImagePicker.with(Add.this).requestImage(Sources.CAMERA).subscribe(new Consumer<Uri>() {
                                    @Override
                                    public void accept(@NonNull Uri uri) throws Exception {

                                        final InputStream imageStream = getContentResolver().openInputStream(uri);
                                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                                        Bitmap btmp = Bitmap.createScaledBitmap(selectedImage, 750, 750, false);

                                        addImage.setImageBitmap(btmp);
                                        img64 = encodeImage(btmp);

                                        imgControl = "1";
                                        addImage.setVisibility(View.VISIBLE);
                                        addDelete.setVisibility(View.VISIBLE);

                                    }
                                });
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add.this, android.R.layout.simple_spinner_dropdown_item, SearchUniName);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    addUni.setAdapter(adapter);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        SearchJSON loginrequest = new SearchJSON(userId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Add.this);
        queue.add(loginrequest);
    }

    private String encodeImage(Bitmap bm)
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);

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

                Bitmap btmp = Bitmap.createScaledBitmap(bitmap, 750, 750, false);

                addImage.setImageBitmap(bitmap);
                img64 = encodeImage(btmp);

                imgControl = "1";
                addImage.setVisibility(View.VISIBLE);
                addDelete.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
