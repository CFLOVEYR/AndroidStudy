package com.beyondself.jalen.studyingandroid.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.beyondself.jalen.studyingandroid.domain.InterView;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取数据库的方法
 */
public class InterViewDao {
    public static final String PATH1 = "data/data/com.beyondself.jalen.studyingandroid/files/book.db";


    /**
     *   添加面试题的方法
     * @param qustion
     * @param answer
     * @return
     */
    public static  boolean insert(String qustion, String answer) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("Question", qustion);
        values.put("Answer", answer);
        long result = db.insert("InterView", null, values);
        return !(result == -1);
    }

    /**
     *  删除某道面试题的方法
     * @param _id
     * @return
     */
    public static  boolean delete(int _id) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READWRITE);

        int result = db.delete("InterView", "_id = ?", new String[]{_id + ""});
        return !(result == 0);
    }

    /**
     * 查询面试题的的方法
     *
     * @return
     */
    public static List<InterView> getInterViewData() {
        List<InterView> list = new ArrayList<InterView>();
        /**
         * 这个方法读取数据库只能读取files目录下的内容所以才写死的内容，需要在Spalash页面来赋值内容到files目录下
         * 第二个参数：当null是默认值，游标工厂 第三个参数：设置为只读的权限，防止修改了原来的文件
         */
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from Interview", null);
        while (cursor.moveToNext()) {
            InterView test = new InterView();
            String question = cursor.getString(cursor.getColumnIndex("Question"));
            int Collected = cursor.getInt(cursor.getColumnIndex("Collected_"));
            String Answer = cursor.getString(cursor.getColumnIndex("Answer"));
//            String remark = cursor.getString(cursor.getColumnIndex("Remark"));

            test.setQuestion(question);
            test.setCollected(Collected == 1);
            test.setAnswer(Answer);
//            test.setRemark(remark);
            list.add(test);
        }
        //关闭游标和关闭数据库
        cursor.close();
        db.close();
        return list;
    }

}
