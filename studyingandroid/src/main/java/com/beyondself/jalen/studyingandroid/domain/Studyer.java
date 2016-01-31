package com.beyondself.jalen.studyingandroid.domain;

import cn.bmob.v3.BmobObject;

/**
 * 用户实体类
 */
public class Studyer extends BmobObject {
    private Integer score;//用户积分
    private Integer progress;//用户等级  青铜,白银,黄金,铂金,钻石,大师,王者
    private String UserName;

    @Override
    public String toString() {
        return "Studyer{" +
                "score=" + score +
                ", progress=" + progress +
                ", UserName='" + UserName + '\'' +
                '}';
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
