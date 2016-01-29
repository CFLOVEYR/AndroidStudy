package com.beyondself.jalen.studyingandroid;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.beyondself.jalen.studyingandroid.dao.CollectionDao;
import com.beyondself.jalen.studyingandroid.domain.Collection;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            CollectionDao.insertCollection("34235",i+1,"423423","dfnaf","fdsaga","tom");
        }

    }

    public void testDbDAO() {
        List<Collection> collections = CollectionDao.queryCommands("tom");
        for (Collection command : collections) {
            Log.e("----------", "------------" + command.toString());
        }
    }
    public  void testDelete(){
        CollectionDao.deleteItem(5);
    }
    public void testExsit(){
        boolean exsit = CollectionDao.queryExsit(5);
        if (exsit) {
            Log.e("----------", "------------存在");
        }else {
            Log.e("----------", "------------不存在");
        }


    }
}