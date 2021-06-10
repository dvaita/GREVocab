package com.example.grevocab;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class FetchDataActivity extends AppCompatActivity {

    ArrayList<Words> map= new ArrayList<>();

    static ArrayList<Words> WordsResource = new ArrayList<>();
    public AsyncHttpClient client;
    public Workbook workbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        ProgressBar progressBar=findViewById(R.id.progress_horizontal);
        TextView textView=findViewById(R.id.textView);

        textView.setText("Welcome");
        progressBar.setVisibility(View.VISIBLE);


        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);


        try {
            map = getResource();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        UserRecord userRecord=new UserRecord();

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String mapResource = gson.toJson(map);
        prefsEditor.putString("wordResource",mapResource);

        String userRecordString=gson.toJson(userRecord);
        prefsEditor.putString("userRecord",userRecordString);

        prefsEditor.apply();
        textView.setText("Finishing Up");



        progressBar.setVisibility(View.INVISIBLE);

        Handler handler=new Handler();
        handler.postDelayed(() -> {
            Intent intent=new Intent(FetchDataActivity.this,MainActivity.class);
            startActivity(intent);
        },12000);

    }

    public ArrayList<Words> getResource() throws JSONException {
        ArrayList<Words> words=new ArrayList<>();

        JSONObject obj = new JSONObject(loadJSONFromAsset());
        JSONArray jsonArray = obj.getJSONArray("words");

        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jb =(JSONObject) jsonArray.get(i);
            Words words1=new Words(jb.getString("words"),jb.getString("definition"),jb.getString("partOfSpeech"),jb.getString("example"));

            words.add(words1);
        }

        return words;
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("words.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }























    public  ArrayList<Words> getResource1() {
        String url = "https://github.com/dvaita/prepgre/blob/main/app_data.xls?raw=true";

        String apiURL = "https://bikashthapa01.github.io/excel-reader-android-app/";




        client = new AsyncHttpClient();


        client.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(FetchDataActivity.this,"Failed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                Toast.makeText(FetchDataActivity.this,"Succeed",Toast.LENGTH_SHORT).show();
                WorkbookSettings ws = new WorkbookSettings();
                ws.setGCDisabled(true);
                if (file != null) {
                    try {
                        workbook= Workbook.getWorkbook(file);

                        Sheet sheet = workbook.getSheet(0);

                        int lastRow = sheet.getRows();

                        for (int i = 0; i < lastRow; i++) {
                            Cell[] row = sheet.getRow(i);
                            Words words = new Words(row[0].getContents(),row[1].getContents(),row[2].getContents(),row[3].getContents());
                           // WordsResource.add(words);
                            WordsResource.add(i,words);
                        }
                    } catch (IOException | BiffException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return WordsResource;
    }
}