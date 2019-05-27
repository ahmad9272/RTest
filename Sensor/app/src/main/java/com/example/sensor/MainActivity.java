package com.example.sensor;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LocationListener {


    private static final String TAG = "LocationApp";
    private LocationManager locationManager;
    private TextView tvLat,tvLong;
    private final int MY_PERMISSIONS_REQUEST= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLat=(TextView) findViewById(R.id.textViewLatitud);
        tvLong=(TextView) findViewById(R.id.textViewLongtud);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


    }

    public void onClickGo(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "MainAcivity No Permisos!!");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
            return;
        }
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        }
    }
    private void buildAlertMessageNoGps(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("your GGPS seens to Be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(@SuppressWarnings("unused") DialogInterface dialog,@SuppressWarnings("unused") final int id) {
                        startActivities(new Intent[]{new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)});
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    public void onClickStop(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG,"Faltan permisos");
            return;
        }
        locationManager.removeUpdates(this);
    }

    //
    @Override
    protected void onDestroy() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG,"Faltan permisos");
            return;
        }
        locationManager.removeUpdates(this);
        super.onDestroy();
    }

     @Override
     public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(TAG, "MainActivity onRequestPermissionsResult requestCode" + requestCode);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "???", Toast.LENGTH_SHORT).show();
                }
                return;
        }
        }

    }

    @Override
    public void onLocationChanged(final Location location) {

        String json = "{" +
                "\"tvLat\":" + location.getLatitude() + "," +
                "\"tvLong\":" + location.getLongitude() +
                "}";
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post("https://enq30yps7cbgf.x.pipedream.net")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .setContentType("application/json")
                .addByteBody(json.getBytes())
                .build();

        /*
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {



                        try {
                            JSONArray Location = response.getJSONArray("Location");
                            for (int i = 0; i < Location.length() ; i++){
                                tvLat.setText(location.getLatitude()+"");
                                tvLong.setText(location.getLongitude()+"");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });*/







/*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enq30yps7cbgf.x.pipedream.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        createPost(location);

    }
    private void createPost(Location location) {
        tvLat.setText(location.getLatitude()+"");
        tvLong.setText(location.getLongitude()+"");
*/
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
