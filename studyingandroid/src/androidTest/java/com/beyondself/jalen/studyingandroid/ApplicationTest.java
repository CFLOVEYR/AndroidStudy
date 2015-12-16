package com.beyondself.jalen.studyingandroid;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.beyondself.jalen.studyingandroid.dao.BookDao;
import com.beyondself.jalen.studyingandroid.domain.TestJava;
import com.beyondself.jalen.studyingandroid.utils.LogUtils;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    public void testDbDAO(){
        List<TestJava> list = BookDao.getAllData();
        LogUtils.i("Test", list.size()+"");
    }
}