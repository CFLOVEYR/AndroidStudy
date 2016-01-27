package com.beyondself.jalen.studyingandroid.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.beyondself.jalen.studyingandroid.domain.Command;

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
    public static boolean insertCollection(int _id, String qustion, String answer, String remark) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        ContentValues values = new ContentValues();
        //添加数据
        values.put("_id", _id);
        values.put("Question", qustion);
        values.put("Answer", answer);
        values.put("Remark", remark);
        long result = db.insert("Collection", null, values);
        db.close();
        return !(result == -1);
    }

    /**
     * 查询收藏的题目
     */
    public static List<Command> queryCommands(int _id) {
        List<Command> list = new ArrayList<Command>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from Interview where _id = ?", new String[]{_id + ""});
        while (cursor.moveToNext()) {
            Command test = new Command();
            String name = cursor.getString(cursor.getColumnIndex("UserName"));
            String command = cursor.getString(cursor.getColumnIndex("Command"));
            int zan = cursor.getInt(cursor.getColumnIndex("Zan"));
            int nozan = cursor.getInt(cursor.getColumnIndex("NoZan"));
//            int pic = cursor.getInt(cursor.getColumnIndex("Pic"));
            test.set_id(_id);
            test.setUserName(name);
            test.setCommand(command);
            test.setZan(zan);
            test.setNoZan(nozan);
            list.add(test);
        }
        //关闭游标和关闭数据库
        cursor.close();
        db.close();
        return list;
    }
}
