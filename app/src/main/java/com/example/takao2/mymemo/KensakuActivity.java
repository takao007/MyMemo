package com.example.takao2.mymemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class KensakuActivity extends AppCompatActivity {
    EditText mSearchTitleEdit;
    EditText mSearchNaiyoEdit;
    CheckBox mSearchCheck;    //保護
    Button mSearchExcuteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kensaku);
        setTitle("Ｍｅｍｏの検索");
        //各ビューのインスタンスの取得
        mSearchTitleEdit = (EditText)findViewById(R.id.SearchTitle);
        mSearchNaiyoEdit  = (EditText)findViewById(R.id.SearchNaiyo);
        mSearchCheck   = (CheckBox) findViewById(R.id.SearchCheck);

    }

    public void onSearchExcuteTapped(View view){
        //情報を渡す
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("title",mSearchTitleEdit.getText().toString());
        intent.putExtra("naiyo",mSearchNaiyoEdit.getText().toString());
        intent.putExtra("hogo",mSearchCheck .isChecked());
        startActivity(intent);
    }

    @Override       //Menu初期表示
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override       //Menu選択時
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item3:    //検索メニュークリック時
                //startActivity(new Intent(MainActivity.this,KensakuActivity.class));
        }
        return true;
    }



}
