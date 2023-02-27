package com.example.task;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    ArrayList<ApiResponse> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.button);
        TextView name = findViewById(R.id.name);
        ImageView image = findViewById(R.id.image);
//
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {


                    apiInterface.getData(10).enqueue(new Callback<ArrayList<ApiResponse>>() {
                        @Override
                        public void onResponse(Call<ArrayList<ApiResponse>> call, Response<ArrayList<ApiResponse>> response) {

                            if (response.isSuccessful()) {
                                list = response.body();
                                name.setText(list.get(0).author);
                                Picasso.get().load(list.get(0).download_url).placeholder(R.drawable.loading).into(image);

                                for (int i = 0; i < list.size(); i++) {
                                    Log.d("Response-->>",i+") Author: "+list.get(i).author );
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<ArrayList<ApiResponse>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}