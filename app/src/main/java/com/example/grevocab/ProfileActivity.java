package com.example.grevocab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    UserRecord userRecord;

    Button wordsActivity,searchActivity,bookmarkActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        wordsActivity=findViewById(R.id.words);
        bookmarkActivity=findViewById(R.id.bookmark);
        searchActivity=findViewById(R.id.search);


        wordsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        bookmarkActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,BookmarksActivity.class);
                startActivity(intent);
            }
        });

        searchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        TextView completedWords=findViewById(R.id.completedWords);
        TextView bookmarkedWords=findViewById(R.id.bookmarkedWords);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();


        String record= mPrefs.getString("userRecord","");
        Type type1=new TypeToken<UserRecord>(){
        }.getType();
        userRecord=gson.fromJson(record,type1);

        try {
            completedWords.setText(String.valueOf(userRecord.completedWords.size()));
            bookmarkedWords.setText(String.valueOf(userRecord.bookmarks.size()));
        }catch (NullPointerException n){

        }
    }
}