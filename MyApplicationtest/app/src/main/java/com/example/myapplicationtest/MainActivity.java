package com.example.myapplicationtest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {


    private Button button;
    private TextView textViewResult;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       // listView= (ListView) findViewById(R.id.pagination_list);
        button = (Button) findViewById(R.id.button);
        textViewResult = (TextView) findViewById(R.id.text_view_result);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(final Location location) {
                textViewResult.append("\n"+location.getLatitude()+" "+location.getLongitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent= new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
            },10 );

            return;
        }else {
                configureButton();
            }






        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enq30yps7cbgf.x.pipedream.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        //getPosts();
        createPost();


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                    return;
        }

    }

    private void configureButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);




    }



    });


    }
    private void getPosts() {
        Call<List<Post>> call = jsonPlaceHolderApi.getposts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
             /*   List<Post> posts = response.body();

                for (Post post : posts){
                    String content ="";
                    content += "ID: " + post.getId() + "/n";
                    content += "User ID: " + post.getUserId() + "/n";
                    content += "Title: " + post.getTitle() + "/n";
                    content += "Text: " + post.getText() + "/n/n";

                    textViewResult.append(content);

                }*/
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }




    private void createPost() {

     Post post = new Post(1.5,1.2,1.2);




     Call<Post> call = jsonPlaceHolderApi.createPost(23.1 ,24.2 , "New Text");

     call.enqueue(new Callback<Post>() {
         @Override
         public void onResponse(Call<Post> call, Response<Post> response) {
             if (!response.isSuccessful()) {
                 textViewResult.setText("code: " + response.code());
                 return;
             }
/*
             Post postResponse = response.body();

             String content ="";
             content += "Code: " + response.code() +"/n";
             content += "ID: " + postResponse.getId() + "/n";
             content += "User ID: " + postResponse.getUserId() + "/n";
             content += "Title: " + postResponse.getTitle() + "/n";
             content += "Text: " + postResponse.getText() + "/n/n";

             textViewResult.append(content);



*/
         }
         @Override
         public void onFailure(Call<Post> call, Throwable t) {
             textViewResult.setText(t.getMessage());

         }
     });


    }

}
