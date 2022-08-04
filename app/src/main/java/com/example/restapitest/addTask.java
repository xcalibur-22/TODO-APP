package com.example.restapitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class addTask extends AppCompatActivity {
    UserClient userClient;
    SharedPreferences sharedPreferences;
    public static String str;
    Button bt_add;
    EditText et_task;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        bt_add=findViewById(R.id.bt_add);
        et_task=findViewById(R.id.et_task1);
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
        progressBar=findViewById(R.id.progress_circular2);
        progressBar.setVisibility(View.GONE);

        sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        str=sharedPreferences.getString("tk","");

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_task.getText().toString().matches("")){
                    Toast.makeText(addTask.this, "Task can't be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    createPost(et_task.getText().toString());
                    bt_add.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void createPost(String task) {
        TodoModel todoModel = new TodoModel(task);
        Call<ResponseBody> call = userClient.createTodo("Token " + str, todoModel);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(addTask.this, "TASK ADDED", Toast.LENGTH_SHORT).show();
                    openActivity();
                }
                else{
                    Toast.makeText(addTask.this, "not successful", Toast.LENGTH_SHORT).show();
                    bt_add=findViewById(R.id.bt_add);
                    bt_add.setEnabled(true);
                    progressBar=findViewById(R.id.progress_circular2);
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(addTask.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                bt_add=findViewById(R.id.bt_add);
                bt_add.setEnabled(true);
                progressBar=findViewById(R.id.progress_circular2);
                progressBar.setVisibility(View.GONE);
            }
        });

    }
    public void openActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}