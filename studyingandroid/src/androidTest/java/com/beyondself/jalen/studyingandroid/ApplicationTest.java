package com.beyondself.jalen.studyingandroid;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.beyondself.jalen.studyingandroid.dao.CommandDao;
import com.beyondself.jalen.studyingandroid.domain.Command;

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
            CommandDao.insertCommandToCommand(6, "fdsagl", 1, "jalen");
        }

    }

    public void testDbDAO() {
        List<Command> commands = CommandDao.queryCommands(6);
        for (Command command : commands) {
            Log.e("----------", "------------" + command.toString());
        }

    }
}