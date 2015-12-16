package com.beyondself.jalen.studyingandroid.domain;

import cn.bmob.v3.BmobObject;

/**
 * 用户实体类
 */
public class Studyer extends BmobObject {
    private String name;//用户名
    private Integer score;//用户积分
    private Integer progress;//用户学习进度

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Studyer{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", progress=" + progress +
                '}';
    }
}
