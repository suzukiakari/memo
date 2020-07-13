package com.example.memo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;

public class NewMemo extends AppCompatActivity {
    EditText memo_input;
    Switch dateSwitch;
    Switch timeSwitch;
    Switch alertSwitch;
    ImageView imageView;
    CalendarView calendarView;
    TimePicker timePicker;
    String date = "null"; //カレンダーの日付を格納するフィールド
    String time = "null";

    //それぞれのスウィッチのON/OFFを判断するためのフィールド
    private boolean dateResult;
    private boolean timeResult;
    private boolean alarmResult;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);
        memo_input = findViewById(R.id.new_memo_input);
        //スウィッチの設置
        dateSwitch = findViewById(R.id.doDate);
        dateSwitch.setOnCheckedChangeListener(new doSwitchChangedListener());
        timeSwitch = findViewById(R.id.doTime);
        timeSwitch.setOnCheckedChangeListener(new doSwitchChangedListener());
        alertSwitch = findViewById(R.id.doAlert);
        alertSwitch.setOnCheckedChangeListener(new doSwitchChangedListener());

        calendarView = findViewById(R.id.calender);
        //デフォルトの状態ではスウィッチはOFFなのでカレンダーは非表示にしておく
        calendarView.setVisibility(View.GONE);
        timePicker = findViewById(R.id.timePicker);
        //デフォルトの状態ではスウィッチはOFFなのでタイムピッカーは非表示にしておく
        timePicker.setVisibility(View.GONE);
        //戻るボタンを新規メモ画面に設置
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setDate();
    }
    //アクションバー作成
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_menu_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //アクションバーの各アイコンの処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home) {
            finish();
        } else if(itemId == R.id.save) {
            onSave();

        }
        return super.onOptionsItemSelected(item);
    }
    //アクションバーの保存のメソッド
    public void onSave() {
            //データベース用のデータをそれぞれ文字列にして変数に代入
            String newMemo = memo_input.getText().toString();
            //monthデータとimgはあとから設定する
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
                    String dateTime = date + time;
                    System.out.println("dateの中身" + dateTime);
                    stmt.bindString(2, dateTime);
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
        //指定日時の通知処理
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void setDate() {
        Intent intent = new Intent(NewMemo.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 6, 10, 17, 05, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if(alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        }

    //スウィッチのリスナークラスの作成
    private class doSwitchChangedListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView == dateSwitch) {
                //スウィッチが日付指定のスウィッチである場合の処理
                if (isChecked) {
                    //カレンダースウィッチの状態をONにしておく
                    dateResult = true;
                    //スウィッチがONであればカレンダーを表示
                    calendarView.setVisibility(View.VISIBLE);
                    //カレンダーのリスナーの呼び出し
                    calendarView.setOnDateChangeListener(new DateChooseListener());
                } else {
                    //カレンダースウィッチの状態をOFFにしておく
                    dateResult = false;
                    //スウィッチがOFFであればカレンダーを非表示
                    calendarView.setVisibility(View.GONE);
                    System.out.println("スィッチOFF");
                    date = "null";
                }
            } else if(buttonView == timeSwitch) {
                //タイムピッカーの状態をONにしておく
                timeResult = true;
                //スウィッチが時間指定のスウィッチである場合の処理
                if(isChecked) {
                    //スウィッチがおONであればタイムピッカー表示
                    timePicker.setVisibility(View.VISIBLE);
                    //タイムピッカーの時刻が選択されて保存を押された場合のリスナ呼び出し
                    timePicker.setOnTimeChangedListener(new TimeChooseListener());
                } else {
                    //タイムピッカーの状態をOFFにしておく
                    timeResult = false;
                    //スウィッチがOFFであればタイムピッカー非表示
                    timePicker.setVisibility(View.GONE);
                    time = "null";
                }

            } else if(buttonView == alertSwitch) {
                //スウィッチが通知指定のスウィッチである場合の処理
                if(isChecked) {
                    //通知のスウィッチの状態をONにしておく
                    alarmResult = true;
                } else {
                    //通知のスウィッチの状態をOFFにしておく
                    alarmResult = false;
                }
            }
        }
    }
    //カレンダーの日付をクリックしたときのリスナークラス
    private class DateChooseListener implements CalendarView.OnDateChangeListener {
        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            date = year + "/" + month + "/" + dayOfMonth;
        }
    }
    //タイムピッカーのリスナクラス
    private class TimeChooseListener implements TimePicker.OnTimeChangedListener {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            time = " " + hourOfDay + ":" + minute;
        }

    }

}