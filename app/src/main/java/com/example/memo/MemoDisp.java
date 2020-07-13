package com.example.memo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

public class MemoDisp extends AppCompatActivity {
    int id;
    private String memoText;
    private String memoImage;
    private String memoDate;
    TextView memoView;
    Switch dateSwitch;
    Switch timeSwitch;
    Switch alertSwitch;
    CalendarView calendarView;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_disp);
        Intent intent = getIntent();
        String memoId = intent.getStringExtra("memoIdStr");
        id = Integer.parseInt(memoId);
        memoText = intent.getStringExtra("memoText");
        memoImage = intent.getStringExtra("memoImage");
        memoDate = intent.getStringExtra("memoDate");

        //戻るボタン
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //テキストビューにメモを表示
        memoView = findViewById(R.id.memoText);
        memoView.setText(memoText);

        //日付指定、時間指定、通知のスウィッチ生成＆リスナに登録
        dateSwitch = findViewById(R.id.memo_edit_switch_date);
        dateSwitch.setOnCheckedChangeListener(new SwitchListener());
        timeSwitch = findViewById(R.id.memo_edit_switch_time);
        timeSwitch.setOnCheckedChangeListener(new SwitchListener());
        alertSwitch = findViewById(R.id.memo_edit_switch_alert);
        alertSwitch.setOnCheckedChangeListener(new SwitchListener());

        calendarView = findViewById(R.id.edit_memo_calendar);
        timePicker = findViewById(R.id.edit_memo_timePicker);
        //デフォルトでは、カレンダー、タイムピッカーは非表示
        calendarView.setVisibility(View.GONE);
        timePicker.setVisibility(View.GONE);
    }
    //アクションバー表示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //アクションバーボタンの処理
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case android.R.id.home:
                finish();
                break;
            case R.id.delete:
                //削除ボタンが押されたときの処理
                //データベースの処理
                DatabaseHelper deleteHelper = new DatabaseHelper(MemoDisp.this);
                sqlMemo deleteMemo = new sqlMemo();
                deleteMemo.delete(deleteHelper, id);
                //DeleteMemodialogへのintentを準備
                Intent intent = new Intent(MemoDisp.this, Memo.class);
                //DeleteMemodialogにintentオブジェクトを渡す
                DeleteMemoDialog dialog = new DeleteMemoDialog(intent);
                //ダイアログを表示
                dialog.show(getSupportFragmentManager(), "DeleteMemoDialog");
                break;
            case R.id.edit:
                //編集ボタンが押さたれた時の処理
              //編集ボタンが押されたときはmemoViewは非表示にする
                memoView.setVisibility(View.GONE);

                EditText editText = findViewById(R.id.memo_edit);
                editText.setText(memoText);
                editText.setEnabled(true);

                //日付指定、時間指定、通知のスウィッチを表示
                dateSwitch.setEnabled(true);
                timeSwitch.setEnabled(true);
                alertSwitch.setEnabled(true);
               break;
            case R.id.save:
                //保存ボタンが押されたときの処理
                EditText editText1 = findViewById(R.id.memo_edit);
                memoText = editText1.getText().toString();
                //それぞれで配列作成
                String[] memoData = {memoText, memoDate, memoImage};
                //データベースの処理
                    DatabaseHelper editHelper = new DatabaseHelper(MemoDisp.this);
                    sqlMemo editMemo = new sqlMemo();
                    boolean editSql = editMemo.upDate(editHelper, id, memoData);
                    //画面遷移のためのIntent作成
                    Intent intent2 = new Intent(MemoDisp.this, Memo.class);
                    //ダイアログ作成
                    EditMemoDialog editDialog = new EditMemoDialog(editSql, intent2, memoText);
                    editDialog.show(getSupportFragmentManager(), "EditMemoDialog");

        }
        return super.onOptionsItemSelected(item);
    }

    //スウィッチのリスナークラス
    private class SwitchListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView == dateSwitch) {
                //スウィッチが日付指定のスウィッチであるときの処理
                if(isChecked) {
                    //日付指定のスウィッチがONであるときカレンダー表示
                    calendarView.setVisibility(View.VISIBLE);
                } else {
                    //OFFであれば非表示
                    calendarView.setVisibility(View.GONE);
                }
            } else if(buttonView == timeSwitch) {
                //スウィッチが時間指定のスウィッチであるときの処理
                if(isChecked) {
                    //時間指定のスウィッチがONであるときタイムピッカー表示
                    timePicker.setVisibility(View.VISIBLE);
                } else {
                    //OFFであれば非表示
                    timePicker.setVisibility(View.GONE);
                }
            } else if(buttonView == alertSwitch) {
                //スウィッチが通知指定のスウィッチであるときの処理
                if(isChecked) {
                    //通知指定のスウィッチがONであるときの処理
                }
            }
        }
    }

}