package com.example.locationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewResult= (TextView) findViewById(R.id.text_view_result);
        Retrofit.Builder builder= new Retrofit.Builder()
                .baseUrl("https://enq30yps7cbgf.x.pipedream.net")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();

       GutHubClient  client = retrofit.create(GutHubClient.class);
       Call<List<GitHubRepo>> call =client.reposForUser("fs-opensource");

       call.enqueue(new Callback<List<GitHubRepo>>() {
           @Override
           public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                List<GitHubRepo> repos= response.body();
               textViewResult.setText("Hello");

           }

           @Override
           public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
               Toast.makeText(MainActivity.this,"error :(", Toast.LENGTH_LONG).show();
           }
       });
    }
}
