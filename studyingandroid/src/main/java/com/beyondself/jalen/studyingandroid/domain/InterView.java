package com.beyondself.jalen.studyingandroid.domain;

import java.io.Serializable;

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
 * 创建日期 : 2016/1/26  16:27
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
public class InterView implements Serializable{
    int _id;
    String question;
    boolean Collected;//是否被收藏
    String Answer;
    String Remark;

    @Override
    public String toString() {
        return "InterView{" +
                "_id=" + _id +
                ", question='" + question + '\'' +
                ", Collected=" + Collected +
                ", Answer='" + Answer + '\'' +
                ", Remark='" + Remark + '\'' +
                '}';
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isCollected() {
        return Collected;
    }

    public void setCollected(boolean collected) {
        Collected = collected;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
