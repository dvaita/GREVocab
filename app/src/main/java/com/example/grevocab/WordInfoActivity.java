package com.example.grevocab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class WordInfoActivity extends AppCompatActivity {

    String word,pos, definition,example;
    Button exit;
    TextView wordView,posView,definitionView,exampleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_info);

        posView=findViewById(R.id.info1);
        definitionView=findViewById(R.id.info2);
        exampleView=findViewById(R.id.info3);
        wordView=findViewById(R.id.questionText);

        word=getIntent().getStringExtra("word");
        definition=getIntent().getStringExtra("definition");
        pos=getIntent().getStringExtra("pos");
        example=getIntent().getStringExtra("example");

        wordView.setText(word);
        posView.setText(pos);
        definitionView.setText(definition);
        exampleView.setText(example);

        exit=findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}