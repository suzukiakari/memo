package com.example.memo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Memo extends AppCompatActivity {
    ListView memoListView;
    List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        //メモリストの表示
        memoListView = findViewById(R.id.memoList);

        //戻るボタンの表示
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //リストビューのメモリストのためのMAP配列準備
        list = new ArrayList<>();
        //データベースの準備
        DatabaseHelper helper = new DatabaseHelper(Memo.this);
        sqlMemo memo = new sqlMemo();
        list = memo.select(helper);

        String[] from = {"memo"};
        int[] to = {android.R.id.text1};
        SimpleAdapter adapter = new SimpleAdapter(Memo.this, list,
                android.R.layout.simple_list_item_2, from, to);
        memoListView.setAdapter(adapter);
        memoListView.setOnItemClickListener(new ListItemClickListener());
    }

    //アクションバーボタンのリスナ
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, Object> memoList = (Map<String, Object>) parent.getItemAtPosition(position);
            int memoId = (int) memoList.get("id");
            String memoIdStr = String.valueOf(memoId);
            System.out.println("Memo" + memoIdStr);
            String memoText = (String) memoList.get("memo");
            String memoImage = (String) memoList.get("image");
            String memoDate = (String) memoList.get("date");
            Intent intent = new Intent(Memo.this, MemoDisp.class);
            intent.putExtra("memoIdStr", memoIdStr);
            intent.putExtra("memoText", memoText);
            intent.putExtra("memoImage", memoImage);
            intent.putExtra("memoDate", memoDate);
            startActivity(intent);
        }
    }
}