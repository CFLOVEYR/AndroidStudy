package com.beyondself.jalen.studyingandroid.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.beyondself.jalen.studyingandroid.domain.InterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 读取数据库的方法
 */
public class BookDao {
    public static final String PATH1 = "data/data/com.beyondself.jalen.studyingandroid/files/book.db";


    /**
     * 添加题目的方法
     *
     * @return
     */
    public static boolean insert(Map<String, String> map) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        ContentValues values = new ContentValues();
        //添加数据
        values.put("", "");

        long result = db.insert("book", null, values);
        db.close();
        return !(result == -1);
    }

    /**
     * 更新题目的方法
     *
     * @return
     */
    public static boolean update(Map<String, String> map) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        ContentValues values = new ContentValues();
        //添加数据
        values.put("", "");


        long result = db.update("Interview", values, null, new String[]{""});
        db.close();
        return !(result == -1);
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
        List<InterView> list = new ArrayList<InterView>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from Interview limit ? offset ?",
                new String[]{String.valueOf(maxCount), String.valueOf(startIndex)});
        while (cursor.moveToNext()) {
            InterView test = new InterView();
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String question = cursor.getString(cursor.getColumnIndex("Question"));
            int Collected = cursor.getInt(cursor.getColumnIndex("Collected_"));
            String Answer = cursor.getString(cursor.getColumnIndex("Answer"));
//            String remark = cursor.getString(cursor.getColumnIndex("Remark"));
            test.set_id(id);
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

}
