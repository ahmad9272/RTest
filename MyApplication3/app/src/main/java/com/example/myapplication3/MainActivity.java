package com.example.myapplication3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    EditText e1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            URL url = new URL("https://www.selfsolve.apple.com/wcResults.do");


            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            String urlParameters ="https://enb6o7qflmfb7.x.pipedream.net/";



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //e1= (EditText)findViewById(R.id.editText);
    }

    public void send(View v)

      {
        MessageSender messageSender = new MessageSender ();
        messageSender.execute(e1.getText().toString());
      }


}
