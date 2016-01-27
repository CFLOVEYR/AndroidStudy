package com.beyondself.jalen.studyingandroid.domain;

/**
 * =========================================================
 * <p/>
 * 版权: 个人开发Mr.Jalen  版权所有(c) 2016
 * <p/>
 * 作者:Jalen
 * <p/>
 * 版本: 1.0
 * <p/>
 * <p/>
 * 创建日期 : 2016/1/27  10:39
 * <p/>
 * <p/>
 * 邮箱：Studylifetime@sina.com
 * <p/>
 * 描述:
 * <p/>
 * <p/>
 * 修订历史:
 * <p/>
 * =========================================================
 */
public class Command {
    int _id;
    String command;
    int Zan;
    int NoZan;
    String UserName;
    int Pic;

    @Override
    public String toString() {
        return "Command{" +
                "_id=" + _id +
                ", command='" + command + '\'' +
                ", Zan=" + Zan +
                ", NoZan=" + NoZan +
                ", UserName='" + UserName + '\'' +
                ", Pic=" + Pic +
                '}';
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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
