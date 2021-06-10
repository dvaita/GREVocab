package com.example.grevocab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class BookmarksActivity extends AppCompatActivity {

    ListView listView;
    String [] bookmarkArray;
    ArrayList<String>bookmarkList;
    ArrayList<Words> WordsResource;
    UserRecord userRecord;

    Button wordsActivity,searchActivity,profileActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        listView=findViewById(R.id.listView);
        wordsActivity=findViewById(R.id.words);
        searchActivity=findViewById(R.id.search);
        profileActivity=findViewById(R.id.orders);


        wordsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookmarksActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        searchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookmarksActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        profileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookmarksActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });


        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        Gson gson = new Gson();

        String json = mPrefs.getString("wordResource", "");
        Type type = new TypeToken<ArrayList<Words>>() {
        }.getType();
        WordsResource=gson.fromJson(json,type);

        String record= mPrefs.getString("userRecord","");
        Type type1=new TypeToken<UserRecord>(){

        }.getType();
        userRecord=gson.fromJson(record,type1);


       bookmarkList=new ArrayList<>();

        try {
            for (int i = 0; i < userRecord.bookmarks.size(); i++) {
                bookmarkList.add(WordsResource.get(userRecord.bookmarks.get(i)).word);
            }
            bookmarkArray=new String[bookmarkList.size()];
            for (int i = 0; i < bookmarkArray.length; i++) {
                bookmarkArray[i] = bookmarkList.get(i);
            }
        }catch (NullPointerException n){

        }

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(BookmarksActivity.this, simple_list_item_1,bookmarkList);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener((parent, view, position, id) -> {
            String word=WordsResource.get(userRecord.bookmarks.get(position)).word;
            String definition=WordsResource.get(userRecord.bookmarks.get(position)).definition;
            String pos=WordsResource.get(userRecord.bookmarks.get(position)).partOfSpeech;
            String example=WordsResource.get(userRecord.bookmarks.get(position)).exampleWord;

            Intent intent=new Intent(BookmarksActivity.this,WordInfoActivity.class);
            intent.putExtra("word",word);
            intent.putExtra("definition",definition);
            intent.putExtra("pos",pos);
            intent.putExtra("example",example);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            bookmarkList.remove(position);
            int pos=userRecord.bookmarks.get(position);
            userRecord.bookmarks.remove(pos);
            return false;
        });
    }

}