package com.example.grevocab;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    Button wordsActivity,bookmarkActivity,searchActivity,infoActivity;
    ArrayList<Words> WordsResource;
    UserRecord userRecord;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)                 //First Use
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
           Intent intent=new Intent(MainActivity.this,FetchDataActivity.class);
           startActivity(intent);
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();



        mPrefs= PreferenceManager.getDefaultSharedPreferences(this);

        bookmarkActivity=findViewById(R.id.bookmark);
        searchActivity=findViewById(R.id.search);
        wordsActivity=findViewById(R.id.wordButton);
        infoActivity=findViewById(R.id.orders);

        Gson gson = new Gson();

        String json = mPrefs.getString("wordResource", "");
        Type type = new TypeToken<ArrayList<Words>>() {
        }.getType();
        WordsResource=gson.fromJson(json,type);

        String record=mPrefs.getString("userRecord","");
        Type type1=new TypeToken<UserRecord>(){
        }.getType();
        userRecord=gson.fromJson(record,type1);


        bookmarkActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,BookmarksActivity.class);
                startActivity(intent);
            }
        });

        wordsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,WordsActivity.class);
                startActivity(intent);
            }
        });

        searchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        infoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}