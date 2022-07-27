package com.example.restapitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class updateTask extends AppCompatActivity {
    UserClient userClient;
    SharedPreferences sharedPreferences;
    public static String str;
    EditText et_task2;
    Button bt_update;
    Button bt_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        et_task2=findViewById(R.id.et_task2);
        bt_update=findViewById(R.id.bt_update);
        bt_delete=findViewById(R.id.bt_delete);



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
        Intent intent=this.getIntent();
        String x=intent.getStringExtra("id");
        String y=intent.getStringExtra("title");
        et_task2.setText(y);
        sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        str=sharedPreferences.getString("tk","");
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_task2.getText().toString().matches("")){
                    Toast.makeText(updateTask.this, "Task can't be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    updatePost(x,et_task2.getText().toString());
                    bt_update.setEnabled(false);
                    bt_delete.setEnabled(false);
                }
            }
        });
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePost(x);
                bt_update.setEnabled(false);
                bt_delete.setEnabled(false);
            }
        });


    }

    private void updatePost(String id,String task){
        TodoModel todoModel=new TodoModel(task);
        Call<TodoModel> call=userClient.updateTodo(Integer.parseInt(id),"Token " + str, todoModel);
        call.enqueue(new Callback<TodoModel>() {
            @Override
            public void onResponse(Call<TodoModel> call, Response<TodoModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(updateTask.this, "TASK UPDATED", Toast.LENGTH_SHORT).show();
                    openActivity();
                }
                else{
                    Toast.makeText(updateTask.this, "not sucessful", Toast.LENGTH_SHORT).show();
                    bt_update=findViewById(R.id.bt_update);
                    bt_delete=findViewById(R.id.bt_delete);
                    bt_update.setEnabled(true);
                    bt_delete.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<TodoModel> call, Throwable t) {
                Toast.makeText(updateTask.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                bt_update=findViewById(R.id.bt_update);
                bt_delete=findViewById(R.id.bt_delete);
                bt_update.setEnabled(true);
                bt_delete.setEnabled(true);
            }
        });

    }

    private void deletePost(String id){
        Call<ResponseBody> call=userClient.deleteTodo(Integer.parseInt(id),"Token " + str);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(updateTask.this, "TASK DELETED", Toast.LENGTH_SHORT).show();
                    openActivity();

                }
                else{
                    Toast.makeText(updateTask.this, "not successful", Toast.LENGTH_SHORT).show();
                    bt_update=findViewById(R.id.bt_update);
                    bt_delete=findViewById(R.id.bt_delete);
                    bt_update.setEnabled(true);
                    bt_delete.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(updateTask.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                bt_update=findViewById(R.id.bt_update);
                bt_delete=findViewById(R.id.bt_delete);
                bt_update.setEnabled(true);
                bt_delete.setEnabled(true);
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