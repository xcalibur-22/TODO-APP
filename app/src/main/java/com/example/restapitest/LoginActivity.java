package com.example.restapitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    UserClient userClient;
    Token token;
    EditText et1;
    EditText et2;
    Button button;
    TextView tvregister;
    public static final String key="";
    ProgressBar progressBar;

    SharedPreferences sharedPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        button=findViewById(R.id.bt);
        tvregister=findViewById(R.id.tvregister);
        progressBar=findViewById(R.id.spinner);
        progressBar.setVisibility(View.GONE);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://todo-app-csoc.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        userClient =retrofit.create(UserClient.class);
        sharedPreferences= getSharedPreferences("MySharedPref",MODE_PRIVATE);
        if(sharedPreferences.contains("tk")){
            openActivity();
        }
//        Toast.makeText(this, , Toast.LENGTH_SHORT).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToken();
                button.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });

    }

    private void openRegister() {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void getToken() {
        Login login=new Login(et1.getText().toString(),et2.getText().toString());

        Call<Token> call=userClient.getToken(login);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    token= response.body();
//                    Toast.makeText(LoginActivity.this, token.getToken(), Toast.LENGTH_SHORT).show();
                    openActivity();

                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("tk",token.getToken());
                    myEdit.apply();
                }
                else{
                    Toast.makeText(LoginActivity.this, "invalid credentials", Toast.LENGTH_SHORT).show();
                    button=findViewById(R.id.bt);
                    button.setEnabled(true);
                    progressBar=findViewById(R.id.spinner);
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage() ,Toast.LENGTH_SHORT).show();
                button=findViewById(R.id.bt);
                button.setEnabled(true);
                progressBar=findViewById(R.id.spinner);
                progressBar.setVisibility(View.GONE);

            }
        });
    }
    public void openActivity(){
        Intent intent=new Intent(this,MainActivity.class);
//        intent.putExtra(key,token.getToken());
        startActivity(intent);
        this.finish();
    }
}