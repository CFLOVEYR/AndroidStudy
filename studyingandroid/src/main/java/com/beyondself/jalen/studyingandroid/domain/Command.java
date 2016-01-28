package com.beyondself.jalen.studyingandroid.domain;

import cn.bmob.v3.BmobObject;

/**
 * 评论
 */
public class Command extends BmobObject {
    Integer id;
    String command;
    Integer Zan;
    Integer NoZan;
    String UserName;
    Integer Pic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getZan() {
        return Zan;
    }

    public void setZan(int zan) {
        Zan = zan;
    }

    public int getNoZan() {
        return NoZan;
    }

    public void setNoZan(int noZan) {
        NoZan = noZan;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getPic() {
        return Pic;
    }

    public void setPic(int pic) {
        Pic = pic;
    }
}
