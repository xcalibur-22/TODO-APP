package com.example.restapitest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    List<TodoModel> todoModelList;
    Token token;
    ListView listView;
    UserClient userClient;
    public static String str;
    ProgressBar spinner;

    SharedPreferences sharedPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lv);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://todo-app-csoc.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        userClient = retrofit.create(UserClient.class);
        spinner=findViewById(R.id.spinner_main);


        sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        str=sharedPreferences.getString("tk","");
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this,updateTask.class);
                intent.putExtra("id",Integer.toString(todoModelList.get(i).getId()));
                intent.putExtra("title",todoModelList.get(i).getTitle());
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        getList();

    }


    private void checkToken() {

        Call<User> call = userClient.getProfile("Token " + str);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    String content = "";
//                    content += "ID: " + user.getId() + "\nNAME: " + user.getName() + "\nEmail: " + user.getEmail() +
//                            "\nUsername: " + user.getUsername();
//                    tv.setText(content);
                    Log.d("ritu", "done");
                } else {
                    Toast.makeText(MainActivity.this, "not success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getList() {
        Call<List<TodoModel>> call = userClient.getTodoList("Token " + str);
        call.enqueue(new Callback<List<TodoModel>>() {
            @Override
            public void onResponse(Call<List<TodoModel>> call, Response<List<TodoModel>> response) {
                if (response.isSuccessful()) {
                    todoModelList = response.body();
                    createList();
                    spinner.setVisibility(View.GONE);

                } else {
                    Toast.makeText(MainActivity.this, "not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TodoModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void createList() {
        RituAdapter adapter = new RituAdapter(this, todoModelList);
        listView.setAdapter(adapter);
        Log.d("damn","ok");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("tk");
        editor.apply();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void openActivity(View view){
        Intent intent=new Intent(this,addTask.class);
        startActivity(intent);
        this.finish();
    }



}

