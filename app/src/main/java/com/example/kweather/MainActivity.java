package com.example.kweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageView imageView;
    TextView country , city , temp ,time ;
    TextView latitude , longitude,sunrise,sunset,pressure,wind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        temp = findViewById(R.id.temp);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.Longitude);
        sunrise = findViewById(R.id.Sunrise);
        sunset = findViewById(R.id.Sunset);
        pressure = findViewById(R.id.Pressure);
        wind = findViewById(R.id.Wind);
        time = findViewById(R.id.time);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findweather();
            }
        });
        
    }

    private void findweather() {
        final String city_name = editText.getText().toString();
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+city_name+"&appid=ee42eea1fac738cf9be4d19521acdc09";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //find country
                    JSONObject object1 = jsonObject.getJSONObject("sys");
                    String Country_Find = object1.getString("country");
                    country.setText(Country_Find);

                    //find city
                    String City_Find = jsonObject.getString("name");
                    city.setText(City_Find);

                    //find temprature
                    JSONObject object2 = jsonObject.getJSONObject("main");
                    int Temp_Find = object2.getInt("temp");
                    temp.setText(Temp_Find-273+"°C");

                    //find image
                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject obj = jsonArray.getJSONObject(0);
                    String img = obj.getString("icon");
                    Picasso.get().load("https://openweathermap.org/img/wn/"+img+"@2x.png").into(imageView);


                    //find latitude
                    JSONObject object3= jsonObject.getJSONObject("coord");
                    double lat_Find = object3.getDouble("lat");
                    latitude.setText(lat_Find+"°  N");

                    //find longitude
                    JSONObject object4= jsonObject.getJSONObject("coord");
                    double long_Find = object4.getDouble("lon");
                    longitude.setText(long_Find+"°  E");

                    //find Sunrise
                    JSONObject object5= jsonObject.getJSONObject("sys");
                    String sunr_Find = object5.getString("sunrise");
                    sunrise.setText(sunr_Find);


                    //find Sunset
                    JSONObject object6= jsonObject.getJSONObject("sys");
                    String suns_Find = object6.getString("sunset");
                    sunset.setText(suns_Find);


                    //find Pressure
                    JSONObject object7= jsonObject.getJSONObject("main");
                    String pr_Find = object7.getString("pressure");
                    pressure.setText(pr_Find+"  hPa");

                    //find wind
                    JSONObject object8= jsonObject.getJSONObject("wind");
                    String w_Find = object8.getString("speed");
                    wind.setText(w_Find+"  Km/h");

                    // find Calender
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat std = new SimpleDateFormat("dd/MM/yyyy \nHH:mm:ss");
                    String date = std.format(calendar.getTime());
                    time.setText(date);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}