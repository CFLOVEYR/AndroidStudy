package com.beyondself.jalen.studyingandroid.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.beyondself.jalen.studyingandroid.domain.Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取数据库的方法
 */
public class CollectionDao {
    public static final String PATH1 = "data/data/com.beyondself.jalen.studyingandroid/files/book.db";


    /**
     * Interview表中添加对题目的评论
     */
    public static boolean insertCollection(int _id, String qustion, String answer, String remark, String name) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        //添加数据
        values.put("_id", _id);
        values.put("Question", qustion);
        values.put("Answer", answer);
        values.put("Remark", remark);
        values.put("UserName", name);
        long result = db.insert("Collection", null, values);
        db.close();
        return !(result == -1);
    }

    /**
     * 查询是否收藏
     */
    public static boolean queryExsit(int _id) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from Collection where _id = ?", new String[]{_id + ""});
        if (cursor.moveToNext()) {
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    /**
     * 取消收藏
     */
    public static boolean deleteItem(int _id) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READWRITE);
//        db.execSQL("delete * from Collection where _id = ?", new String[]{_id + ""});
        int result = db.delete("Collection", "_id = ?", new String[]{_id + ""});
        db.close();
        if (result == 0) {
            return false;
        }
        return true;
    }

    /**
     * 查询收藏的题目
     */
    public static List<Collection> queryCommands(String name) {
        List<Collection> list = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from Collection where UserName = ?", new String[]{name});
        while (cursor.moveToNext()) {

            Collection test = new Collection();
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String question = cursor.getString(cursor.getColumnIndex("Question"));
            String answer = cursor.getString(cursor.getColumnIndex("Answer"));
            String remark = cursor.getString(cursor.getColumnIndex("Remark"));
            String userName = cursor.getString(cursor.getColumnIndex("UserName"));
            test.set_id(id);
            test.setQuestion(question);
            test.setAnswer(answer);
            test.setRemark(remark);
            test.setUserName(userName);
            list.add(test);
        }
        //关闭游标和关闭数据库
        cursor.close();
        db.close();
        return list;
    }
}
