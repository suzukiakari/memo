package com.example.memo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteMemo {
    List<Map<String, Object>> list = new ArrayList<>();
    Map<String, Object> memoList;

    public List<Map<String, Object>> select(DatabaseHelper helper) {
        //データベースの準備
        SQLiteDatabase db = helper.getWritableDatabase();
        //sql
        try {
            String sql = "SELECT * FROM memodata";
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String memo = cursor.getString(1);
                String image = cursor.getString(2);
                String date = cursor.getString(3);
                memoList = new HashMap<>();
                memoList.put("id", id);
                memoList.put("memo", memo);
                memoList.put("image", image);
                memoList.put("date", date);
                list.add(memoList);
            }
        } finally {
            db.close();
        }
        return list;
    }

    public void delete(DatabaseHelper helper, int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String sql = "DELETE FROM memodata WHERE id = ?";
            SQLiteStatement stmt = db.compileStatement(sql);
            stmt.bindLong(1, id);
            stmt.executeUpdateDelete();
        }finally{
            db.close();
        }
    }
}