package com.example.retrofitexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewRsesult;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewRsesult = findViewById(R.id.text_view_result);

        Gson gson =new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enq30yps7cbgf.x.pipedream.net/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

       JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPosts();
        // getComments();
       // createPost();
         UpdatePost();
       // deletePost();
    }

    private void getPosts() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("userId" , "1");
        parameters.put("_sort" , "id");
        parameters.put("_order" , "desc");

      Call<List<Post>> call= jsonPlaceHolderApi.getposts(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()) {
                    textViewRsesult.setText("code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();

                for (Post post : posts){
                    String content ="";
                    content += "ID: " + post.getId() + "/n";
                    content += "User ID: " + post.getUserId() + "/n";
                    content += "Title: " + post.getTitle() + "/n";
                    content += "Text: " + post.getText() + "/n/n";

                    textViewRsesult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewRsesult.setText(t.getMessage());
            }
        });
    }

    private void getComments(){
        Call<List<Comment>> call= jsonPlaceHolderApi.getComments( 3 ) ;

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {
                    textViewRsesult.setText("Code: " + response.code());
                    return;
                }

            /*
               List<Comment> comments = response.body();

                for (Comment comment : comments) {
                    String content ="";
                    content += "ID: " + comment.getId() + "/n";
                    content += "Post ID: " + comment.getPostId() + "/n";
                    content += "Name: " + comment.getName() + "/n";
                    content += "Email: " + comment.getEmail() + "/n";
                    content += "Text: " + comment.getText() + "/n/n";

                    textViewRsesult.append(content);
                }
                */
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewRsesult.setText(t.getMessage());
            }
        });
    }
    private void createPost() {
        Post post = new Post(23, "New Title", "New Text");

        Map<String, String> fields = new HashMap<>();
        fields.put("userId","25");
        fields.put("title","New Title");

        Call<Post> call = jsonPlaceHolderApi.createPost(23,"New Title", "New Text");

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewRsesult.setText("code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content ="";
                content += "Code: " + response.code() +"/n";
                content += "ID: " + postResponse.getId() + "/n";
                content += "User ID: " + postResponse.getUserId() + "/n";
                content += "Title: " + postResponse.getTitle() + "/n";
                content += "Text: " + postResponse.getText() + "/n/n";

                textViewRsesult.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewRsesult.setText(t.getMessage());

            }
        });
    }

    private void UpdatePost() {
        Post post = new Post(12,null,"New Text");
        Map<String, String> headers = new HashMap<>();
        headers.put("Map-Header1", "def");
        headers.put("Map-Header2", "ghi");
        Call<Post> call = jsonPlaceHolderApi.putPost("abc",5, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewRsesult.setText("code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content ="";
                content += "Code: " + response.code() +"/n";
                content += "ID: " + postResponse.getId() + "/n";
                content += "User ID: " + postResponse.getUserId() + "/n";
                content += "Title: " + postResponse.getTitle() + "/n";
                content += "Text: " + postResponse.getText() + "/n/n";

                textViewRsesult.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewRsesult.setText(t.getMessage());

            }
        });

    }

    private void deletePost(){
        Call<Void> call= jsonPlaceHolderApi.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewRsesult.setText("code: "+ response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewRsesult.setText(t.getMessage());

            }
        });
    }
}
 /*     Post post = new Post(textViewResult);
        Call<Post> call = jsonPlaceHolderApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
           public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("code: " + response.code());
                    return;
                }
                Post postResponde = response.body();

                String content = "";
                content += "Latitude: " + postResponde.getLatitude()+ "\n";
                content += "Longitude: " + postResponde.getLongitude()+ "\n";

                textViewResult.setText(content);
            }


            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });*/
