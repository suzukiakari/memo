package com.example.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void newMemoMoveClick(View view) {
        Intent intentNewMemo = new Intent(MainActivity.this, NewMemo.class);
        startActivity(intentNewMemo);
    }
    public void memoMoveClick(View view) {
        Intent intentMemo = new Intent(MainActivity.this, Memo.class);
        startActivity(intentMemo);
    }
}