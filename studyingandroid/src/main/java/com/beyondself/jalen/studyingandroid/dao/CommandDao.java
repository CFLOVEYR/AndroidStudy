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
public class CommandDao {
    public static final String PATH1 = "data/data/com.beyondself.jalen.studyingandroid/files/book.db";

    /**
     * Interview表中添加对题目的评论
     */
    public static boolean updateCommandToInterView(int _id, String command) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        ContentValues values = new ContentValues();
        //添加数据
        values.put("Command", command);
        int result = db.update("Interview", values, "_id = ?", new String[]{_id + ""});
        db.close();
        return !(result == -1);
    }

    /**
     * Interview表中添加对题目的评论
     */
    public static boolean insertCommandToCommand(int _id, String command, int pic, String username) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        ContentValues values = new ContentValues();
        //添加数据
        values.put("Command", command);
        values.put("_id", _id);
        values.put("Pic", pic);
        values.put("UserName", username);
        long result = db.insert("Commands", null, values);
        db.close();
        return !(result == -1);
    }

    /**
     * 查询对应的题目评论
     */
    public static List<Command> queryCommands(int _id) {
        List<Command> list = new ArrayList<Command>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from Commands where _id = ?", new String[]{_id + ""});

        while (cursor.moveToNext()) {
            Command test = new Command();
            String name = cursor.getString(cursor.getColumnIndex("UserName"));
            String command = cursor.getString(cursor.getColumnIndex("Command"));
            int zan = cursor.getInt(cursor.getColumnIndex("Zan"));
            int nozan = cursor.getInt(cursor.getColumnIndex("NoZan"));
//            int pic = cursor.getInt(cursor.getColumnIndex("Pic"));
            test.setId(_id);
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


    /**
     * Interview表中添加对赞的修改
     */
    public static boolean updateZan(int _id, int count) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        ContentValues values = new ContentValues();
        //添加数据
        values.put("Zan", count);
        long result = db.update("Commands", values, "_id = ?", new String[]{_id + ""});
        db.close();
        return !(result == -1);
    }

    /**
     * Interview表中添加对赞的数目的查询
     */
    public static int queryZan(int _id, int count) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        ContentValues values = new ContentValues();
        //添加数据
        values.put("Zan", count);
        Cursor cursor = db.rawQuery("select Zan from Commands whrere _id = ?", new String[]{_id + ""});
        int zan = 0;
        if (cursor != null) {
            zan = cursor.getInt(cursor.getColumnIndex("Zan"));
        }
        db.close();
        return zan;
    }

    /**
     * Interview表中添加对赞的修改
     */
    public static boolean updateNoZan(int _id, int count) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        ContentValues values = new ContentValues();
        //添加数据
        values.put("NoZan", count);
        long result = db.update("Commands", values, "_id = ?", new String[]{_id + ""});
        db.close();
        return !(result == -1);
    }

    /**
     * Interview表中添加对赞的数目的查询
     */
    public static int queryNoZan(int _id, int count) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        ContentValues values = new ContentValues();
        //添加数据
        values.put("NoZan", count);
        Cursor cursor = db.rawQuery("select NoZan from Commands whrere _id = ?", new String[]{_id + ""});
        int nozan = 0;
        if (cursor != null) {
            nozan = cursor.getInt(cursor.getColumnIndex("NoZan"));
        }
        db.close();
        return nozan;
    }

}
