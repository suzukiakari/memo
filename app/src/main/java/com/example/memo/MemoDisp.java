package com.example.memo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MemoDisp extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_disp);
        Intent intent = getIntent();
        String memoId = intent.getStringExtra("memoIdStr");
        id = Integer.parseInt(memoId);
        System.out.println("めも" + id);
        String memoText = intent.getStringExtra("memoText");
        String memoImage = intent.getStringExtra("memoImage");
        String memoDate = intent.getStringExtra("memoDate");

        //戻るボタン
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //テキストビューにメモを表示
        TextView memoView = findViewById(R.id.memoText);
        memoView.setText(memoText);
    }
    //アクションバー表示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case android.R.id.home:
                finish();
                break;
            case R.id.delete:
                DatabaseHelper helper = new DatabaseHelper(MemoDisp.this);
                DeleteMemo memo = new DeleteMemo();
                memo.delete(helper, id);
        }
        return super.onOptionsItemSelected(item);
    }
}