package com.beyondself.jalen.studyingandroid.domain;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

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
public class InterView extends BmobObject implements Serializable, Comparable {
    Integer id;
    String question;
    Boolean Collected;//是否被收藏
    String Answer;
    String Remark;
    String Command;
    Integer updateCode;
    Boolean updated;

    public Boolean getCollected() {
        return Collected;
    }

    public void setCollected(Boolean collected) {
        Collected = collected;
    }

    public Integer getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(Integer updateCode) {
        this.updateCode = updateCode;
    }

    public Boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    public String getCommand() {
        return Command;
    }

    public void setCommand(String command) {
        Command = command;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "InterView{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", Collected=" + Collected +
                ", Answer='" + Answer + '\'' +
                ", Remark='" + Remark + '\'' +
                ", Command='" + Command + '\'' +
                ", updateCode=" + updateCode +
                ", updated=" + updated +
                '}';
    }

    @Override
    public int compareTo(Object another) {
        InterView other = (InterView) another;
        if (other.getId() != this.getId()) return this.getId() - other.getId();
        return 0;
    }
}
