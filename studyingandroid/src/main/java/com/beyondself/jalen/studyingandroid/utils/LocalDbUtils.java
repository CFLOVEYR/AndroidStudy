package com.beyondself.jalen.studyingandroid.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jalen on 2015/12/16.
 */
public class LocalDbUtils {
    /**
     * 复制数据库到本地文件中
     * @param context
     * @param dbname
     */
    public static  void copyDb(Context context,String dbname) {
        InputStream in = null;
        File destfile = new File(context.getFilesDir() + "/" + dbname);
        if (destfile.exists()) {
            return;
        }
        System.out.println("---" + context.getFilesDir());
        FileOutputStream out = null;
        try {
            in = context.getAssets().open(dbname);
            out = new FileOutputStream(destfile);
            int len = 0;
            byte[] data = new byte[1024];
            while ((len = in.read(data)) != -1) {
                out.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
