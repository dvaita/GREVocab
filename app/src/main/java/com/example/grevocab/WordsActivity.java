package com.example.grevocab;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class WordsActivity extends AppCompatActivity {
    private SharedPreferences mPrefs;

    static ArrayList<Words> WordsResource;

    TextView questionText;
    Button option1,option2,option3,option4,option5,skip,info,next;
    ImageView bookmark;

    UserRecord userRecord;

    Random random=new Random();
    Random random1=new Random();
    Words words;

    String answer;
    int indexCW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        WordsResource=new ArrayList<>();
        mPrefs= PreferenceManager.getDefaultSharedPreferences(this);
        getWords();

        questionText=findViewById(R.id.questionText);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        option5=findViewById(R.id.option5);
        info=findViewById(R.id.info);
        skip=findViewById(R.id.skip);
        next=findViewById(R.id.next);
        bookmark=findViewById(R.id.addBookmark);


        next.setVisibility(View.INVISIBLE);

        displayQuestion();

        info.setOnClickListener(v -> {
            Intent intent=new Intent(WordsActivity.this,WordInfoActivity.class);
            intent.putExtra("word",words.word);
            intent.putExtra("definition",words.definition);
            intent.putExtra("pos",words.partOfSpeech);
            intent.putExtra("example",words.exampleWord);
            startActivity(intent);
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRecord.bookmarks.add(indexCW);
                skip.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
                updateUserRecord();
                Toast.makeText(WordsActivity.this,"Added to Bookmarks",Toast.LENGTH_LONG).show();
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip.setVisibility(View.INVISIBLE);
                if(option1.getText().toString().equals(answer)){
                    option1.setTextColor(Color.GREEN);
                    userRecord.completedWords.add(indexCW);
                    updateUserRecord();
                }else{
                    option1.setTextColor(Color.RED);
                }
                changeStatus(false);
                next.setVisibility(View.VISIBLE);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip.setVisibility(View.INVISIBLE);
                if(option2.getText().toString().equals(answer)){
                    option2.setTextColor(Color.GREEN);
                    userRecord.completedWords.add(indexCW);
                    updateUserRecord();
                }else{
                    option2.setTextColor(Color.RED);
                }
                next.setVisibility(View.VISIBLE);
                changeStatus(false);
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip.setVisibility(View.INVISIBLE);
                if(option3.getText().toString().equals(answer)){
                    option3.setTextColor(Color.GREEN);
                    userRecord.completedWords.add(indexCW);
                    updateUserRecord();
                }else{
                    option3.setTextColor(Color.RED);
                } changeStatus(false);
                next.setVisibility(View.VISIBLE);

            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip.setVisibility(View.INVISIBLE);
                if(option4.getText().toString().equals(answer)){
                    option4.setTextColor(Color.GREEN);
                    userRecord.completedWords.add(indexCW);
                    updateUserRecord();
                }else{
                    option4.setTextColor(Color.RED);
                }
                changeStatus(false);
                next.setVisibility(View.VISIBLE);
            }
        });
        option5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip.setVisibility(View.INVISIBLE);
                if(option5.getText().toString().equals(answer)){
                    option5.setTextColor(Color.GREEN);
                    userRecord.completedWords.add(indexCW);
                    updateUserRecord();
                }else{
                    option5.setTextColor(Color.RED);
                }
                changeStatus(false);
                next.setVisibility(View.VISIBLE);
            }
        });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                words=null;
                displayQuestion();
                option1.setTextColor(Color.BLACK);
                option2.setTextColor(Color.BLACK);
                option3.setTextColor(Color.BLACK);
                option4.setTextColor(Color.BLACK);
                option5.setTextColor(Color.BLACK);
                changeStatus(true);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                words=null;
                displayQuestion();
                option1.setTextColor(Color.BLACK);
                option2.setTextColor(Color.BLACK);
                option3.setTextColor(Color.BLACK);
                option4.setTextColor(Color.BLACK);
                option5.setTextColor(Color.BLACK);
                changeStatus(true);
            }
        });
    }

    public void changeStatus(boolean bool){
        option1.setEnabled(bool);
        option2.setEnabled(bool);
        option3.setEnabled(bool);
        option4.setEnabled(bool);
        option5.setEnabled(bool);
    }


    public void displayQuestion(){
        int bound=WordsResource.size();
        int rand=random.nextInt(bound);
        int opRand=random1.nextInt(5);
        if(!checkCompletedList(rand)) {
             words = WordsResource.get(rand);
             indexCW=rand;
             questionText.setText(words.word);
            switch (opRand){
                case 1:option1.setText(words.definition);
                    break;
                case 2:option2.setText(words.definition);
                    break;
                case 3:option3.setText(words.definition);
                    break;
                case 4:option4.setText(words.definition);
                    break;
                case 5:option5.setText(words.definition);
                    break;
            }
            answer=words.definition;
            getOptions(opRand,rand);
        }else{
            displayQuestion();
        }
    }

    public void getOptions(int index,int rand){
        int bound=1000;
        int rand1=random.nextInt(bound);
        String op1 = "",op2 = "",op3 = "",op4 = "";

        if(WordsResource.get(rand1) != WordsResource.get(rand)){
            op1=WordsResource.get(rand1).definition;
        }else{
            rand1=random.nextInt();
            if(WordsResource.get(rand1) != WordsResource.get(rand)){
                op1=WordsResource.get(rand1).definition;
            }
        }

        rand1=random.nextInt(bound);
        if(WordsResource.get(rand1) != WordsResource.get(rand)){
            op2=WordsResource.get(rand1).definition;
        }else{
            rand1=random.nextInt();
            if(WordsResource.get(rand1) != WordsResource.get(rand)){
                op2=WordsResource.get(rand1).definition;
            }
        }

        rand1=random.nextInt(bound);

        if(WordsResource.get(rand1) != WordsResource.get(rand)){
            op3=WordsResource.get(rand1).definition;
        }else{
            rand1=random.nextInt();
            if(WordsResource.get(rand1) != WordsResource.get(rand)){
                op3=WordsResource.get(rand1).definition;
            }
        }
        rand1=random.nextInt(bound);
        if(WordsResource.get(rand1) != WordsResource.get(rand)){
            op4=WordsResource.get(rand1).definition;
        }else{
            rand1=random.nextInt();
            if(WordsResource.get(rand1) != WordsResource.get(rand)){
                op4=WordsResource.get(rand1).definition;
            }
        }

        switch (index){
            case 1:option2.setText(op1);
                option3.setText(op2);
                option4.setText(op3);
                option5.setText(op4);
                break;
            case 2:option1.setText(op1);
                option3.setText(op2);
                option4.setText(op3);
                option5.setText(op4);
                break;
            case 3:option1.setText(op1);
                option2.setText(op2);
                option4.setText(op3);
                option5.setText(op4);

                break;
            case 4:option1.setText(op1);
                option2.setText(op2);
                option3.setText(op3);
                option5.setText(op4);

                break;
            case 5:option1.setText(op1);
                option2.setText(op2);
                option3.setText(op3);
                option4.setText(op4);
                break;
        }
    }


    public boolean checkCompletedList(int index){
        return userRecord.completedWords.contains(index);
    }

    private void updateUserRecord(){
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();

        String userRecordString=gson.toJson(userRecord);
        prefsEditor.putString("userRecord",userRecordString);

        prefsEditor.apply();
    }



    public void getWords(){

        Gson gson = new Gson();
        String json = mPrefs.getString("wordResource", "");
        Type type = new TypeToken<ArrayList<Words>>() {
        }.getType();
        WordsResource=gson.fromJson(json,type);
      //  Toast.makeText(WordsActivity.this,""+WordsResource.get(0).word,Toast.LENGTH_SHORT).show();

        String record=mPrefs.getString("userRecord","");
        Type type1=new TypeToken<UserRecord>(){

        }.getType();
        userRecord=gson.fromJson(record,type1);
    }


}