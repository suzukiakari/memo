package com.example.memo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class NewMemo extends AppCompatActivity {
    EditText memo_input;
    CheckBox checkBox;
    ImageView imageView;
    Spinner month;
    Spinner date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);
        memo_input = findViewById(R.id.new_memo_input);
        checkBox = findViewById(R.id.doDate);
        month = findViewById(R.id.month);
        date = findViewById(R.id.date);

        //戻るボタンを新規メモ画面に設置
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public void onNewMemoClick(View view) {
        Button button = findViewById(R.id.new_memo_button);
        button.setOnClickListener(new ClickListener());
    }

    //戻るボタン
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //データベース用のデータをそれぞれ文字列にして変数に代入
            String newMemo = memo_input.getText().toString();
            //monthデータとimgはあとから設定する
            String monthDate = "null";
            String img = "null";

            if(newMemo.equals("")) {
                //もし、メモが空であれば注意喚起のダイアログを表示
                NewMemoDialog dialog = new NewMemoDialog(memo_input);
                dialog.show(getSupportFragmentManager(), "NewMemoDailog");
            } else {
                //メモが空でなければデータベースに接続
                DatabaseHelper helper = new DatabaseHelper(NewMemo.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                //sql
                try {
                    String sqlInsert = "INSERT INTO memodata (text, image, date) VALUES(?,?,?)";
                    SQLiteStatement stmt = db.compileStatement(sqlInsert);
                    stmt.bindString(1, newMemo);
                    stmt.bindString(2, monthDate);
                    stmt.bindString(3, img);
                    stmt.executeInsert();
                } finally {
                    db.close();
                }
                //追加成功であれば成功のダイアログ表示
                NewMemoDialog dialog = new NewMemoDialog(memo_input);
                dialog.show(getSupportFragmentManager(), "NewMemoDailog");
            }
        }
    }
}