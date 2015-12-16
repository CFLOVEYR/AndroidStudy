package com.beyondself.jalen.studyingandroid.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.beyondself.jalen.studyingandroid.domain.TestJava;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取数据库的方法
 */
public class BookDao {
    public static final String PATH1 = "data/data/com.beyondself.jalen.studyingandroid/files/book.db";

    /**
     * 查询JAVA基础题目的方法
     *
     * @return
     */
    public static List<TestJava> getAllData() {
        List<TestJava> list = new ArrayList<TestJava>();
        /**
         * 这个方法读取数据库只能读取files目录下的内容所以才写死的内容，需要在Spalash页面来赋值内容到files目录下
         * 第二个参数：当null是默认值，游标工厂 第三个参数：设置为只读的权限，防止修改了原来的文件
         */
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH1, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from TestJava", null);
        while (cursor.moveToNext()) {
            TestJava test = new TestJava();
            String top_question;
            String code_question;
            String bottom_question;
            if (cursor.getString(cursor.getColumnIndex("top_question")) == null) {
                top_question = "";
            } else {
                top_question = cursor.getString(cursor.getColumnIndex("top_question"));
            }


            if (cursor.getString(cursor.getColumnIndex("top_question")) == null) {
                code_question = "";
            } else {
                code_question = cursor.getString(cursor.getColumnIndex("code_question"));
            }


            if (cursor.getString(cursor.getColumnIndex("top_question")) == null) {
                bottom_question = "";
            } else {
                bottom_question = cursor.getString(cursor.getColumnIndex("bottom_question"));
            }
            String selectA = cursor.getString(cursor.getColumnIndex("selectA"));
            String selectB = cursor.getString(cursor.getColumnIndex("selectB"));
            String selectC = cursor.getString(cursor.getColumnIndex("selectC"));
            String selectD = cursor.getString(cursor.getColumnIndex("selectD"));
            test.setTop_question(top_question);
            test.setCode_question(code_question);
            test.setBottom_question(bottom_question);
            test.setSelectA(selectA);
            test.setSelectB(selectB);
            test.setSelectC(selectC);
            test.setSelectD(selectD);
            list.add(test);
        }
        //关闭游标和关闭数据库
        cursor.close();
        db.close();
        return list;
    }


}
