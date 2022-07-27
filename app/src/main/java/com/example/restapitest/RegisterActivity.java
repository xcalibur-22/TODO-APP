package com.example.restapitest;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {
    UserClient userClient;
    Token token;
    SharedPreferences sharedPreferences;
    EditText et_name;
    EditText et_email;
    EditText et_username;
    EditText et_password;
    Button signupbt;
    TextView tvlogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        signupbt = findViewById(R.id.signupbt);
        tvlogin = findViewById(R.id.tvlogin);

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

        progressBar=findViewById(R.id.spinner2);
        progressBar.setVisibility(View.GONE);
        signupbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
                signupbt.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
    }

    private void openLogin() {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void register() {
        RegisterModel registerModel = new RegisterModel(et_name.getText().toString(), et_email.getText().toString(), et_username.getText().toString(), et_password.getText().toString());

        Call<Token> call = userClient.registerProfile(registerModel);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    token = response.body();
//                    Toast.makeText(RegisterActivity.this, token.getToken(), Toast.LENGTH_SHORT).show();
                    openActivity();

                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("tk", token.getToken());
                    myEdit.apply();
                } else {
                    Toast.makeText(RegisterActivity.this, "invalid input", Toast.LENGTH_SHORT).show();
                    signupbt = findViewById(R.id.signupbt);
                    signupbt.setEnabled(true);
                    progressBar=findViewById(R.id.spinner2);
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage() ,Toast.LENGTH_SHORT).show();
                signupbt = findViewById(R.id.signupbt);
                signupbt.setEnabled(true);
                progressBar=findViewById(R.id.spinner2);
                progressBar.setVisibility(View.GONE);

            }
        });

    }

    public void openActivity() {
        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra(key,token.getToken());
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        openLogin();
    }
}