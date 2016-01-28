package com.beyondself.jalen.studyingandroid.domain;

import cn.bmob.v3.BmobObject;

/**
 * =========================================================
 * <p>
 * 版权: 个人开发Mr.Jalen  版权所有(c) 2016
 * <p>
 * 作者:Jalen
 * <p>
 * 版本: 1.0
 * <p>
 * <p>
 * 创建日期 : 2016/1/27  15:56
 * <p>
 * <p>
 * 邮箱：Studylifetime@sina.com
 * <p>
 * 描述:
 * <p>
 * <p>
 * 修订历史:
 * <p>
 * =========================================================
 */
public class Collection extends BmobObject {
    String UserName;
    Integer _id;
    String Question;
    String Answer;
    String Remark;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "UserName='" + UserName + '\'' +
                ", _id=" + _id +
                ", Question='" + Question + '\'' +
                ", Answer='" + Answer + '\'' +
                ", Remark='" + Remark + '\'' +
                '}';
    }
}
