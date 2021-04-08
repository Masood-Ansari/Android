package com.example.apisdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText edFilm;
    TextView txttitle,txtdir;
    ImageView imgposter;
    Button btnDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edFilm=findViewById(R.id.edfilm);
        txttitle=findViewById(R.id.txtTitle);
        txtdir=findViewById(R.id.txtDirector);
        imgposter=findViewById(R.id.imagePoster);
        btnDisplay=findViewById(R.id.btnDisplay);

        if (Build.VERSION.SDK_INT >9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://www.omdbapi.com/?apikey=10300b14&t="+edFilm.getText().toString();
                JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            txttitle.setText(response.getString("Title"));
                            txtdir.setText(response.getString("Director"));

                            URL url =new URL(response.getString("Poster"));
                            HttpURLConnection con= (HttpURLConnection) url.openConnection();
                            con.setDoInput(true);
                            con.connect();
                            InputStream input=con.getInputStream();
                            Bitmap imgBitmap= BitmapFactory.decodeStream(input);

                            imgposter.setImageBitmap(imgBitmap);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue req= Volley.newRequestQueue(MainActivity.this);
                req.add(request);
            }
        });


    }
}