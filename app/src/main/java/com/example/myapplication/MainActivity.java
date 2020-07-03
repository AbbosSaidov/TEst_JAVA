package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.Dbase.AppDatabase;
import com.example.myapplication.Dbase.User;
import com.example.myapplication.retrofit.JsonPlaceHolderApi;
import com.example.myapplication.retrofit.Post;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.asd);
        textView.setText("Ok2=");
//dataase
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        List<User> lisde= db.userDao().getAll();
        Log.i("qwerty","sad="+lisde.size());
//Retrofit
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call =jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    Log.i("qwerty","Vadde=");
                    return;
                }
                List<Post> posts=response.body();
                Log.i("qwerty","sad="+posts.get(0).getId());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });



        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
}