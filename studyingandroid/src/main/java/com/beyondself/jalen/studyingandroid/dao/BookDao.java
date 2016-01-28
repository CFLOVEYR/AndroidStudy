package com.beyondself.jalen.studyingandroid.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.beyondself.jalen.studyingandroid.domain.InterView;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取数据库的方法
 */
public class BookDao {
    public static final String PATH1 = "data/data/com.beyondself.jalen.studyingandroid/files/book.db";


    /**
     * 通过_id查询得到InterView
     *
     * @return
     */
    public static InterView queryInterViewById(int _id) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from Interview where _id = ?", new String[]{"" + _id});
        if (cursor.moveToNext()) {
            InterView test = new InterView();
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String question = cursor.getString(cursor.getColumnIndex("Question"));
            int Collected = cursor.getInt(cursor.getColumnIndex("Collected_"));
            String Answer = cursor.getString(cursor.getColumnIndex("Answer"));
//            String remark = cursor.getString(cursor.getColumnIndex("Remark"));
            test.setId(id);
            test.setQuestion(question);
            test.setCollected(Collected == 1);
            test.setAnswer(Answer);
            return test;
        }
        cursor.close();
        db.close();
        return null;
    }

    /**
     * 查询一共有多少条数据的方法
     *
     * @return 返回的是数据的数目
     */
    public static int queryCount() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select count(*) from Interview", null);
        cursor.moveToNext();
        int num = cursor.getInt(0);
        cursor.close();
        db.close();
        return num;
    }

    /**
     * 分批加载查询数据的方法
     *
     * @param maxCount   每次查询多少条数据
     * @param startIndex 每次查询的起始位置
     * @return
     */
    public static List<InterView> queryInterViewData(int maxCount, int startIndex) {
        List<InterView> list = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = db.rawQuery("select * from Interview limit ? offset ?",
                new String[]{String.valueOf(maxCount), String.valueOf(startIndex)});
        while (cursor.moveToNext()) {
            InterView test = new InterView();
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String question = cursor.getString(cursor.getColumnIndex("Question"));
            int Collected = cursor.getInt(cursor.getColumnIndex("Collected_"));
            String Answer = cursor.getString(cursor.getColumnIndex("Answer"));
//            String remark = cursor.getString(cursor.getColumnIndex("Remark"));
            test.setId(id);
            test.setQuestion(question);
            test.setCollected(Collected == 1);
            test.setAnswer(Answer);
//            test.setRemark(remark);
            list.add(test);
        }
        cursor.close();
        db.close();
        SystemClock.sleep(1000);
        return list;
    }

    /***
     * 获得最大的_id
     *
     * @return
     */
    public static int queryMaxCount() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select _id from Interview", null);
        int max = 0;
        while (cursor.moveToNext()) {
            int sub = cursor.getInt(cursor.getColumnIndex("_id"));
            if (max < sub) {
                max = sub;
            }
        }
        cursor.close();
        db.close();
        return max;
    }
}
