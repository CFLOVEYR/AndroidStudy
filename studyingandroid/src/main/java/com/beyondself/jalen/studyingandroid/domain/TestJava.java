package com.beyondself.jalen.studyingandroid.domain;

/**
 * JAVA基础测试题的实体类
 */
public class TestJava {
    String top_question;
    String code_question;
    String bottom_question;
    String selectA;
    String selectB;
    String selectC;
    String selectD;

    @Override
    public String toString() {
        return "TestJava{" +
                "top_question='" + top_question + '\'' +
                ", code_question='" + code_question + '\'' +
                ", bottom_question='" + bottom_question + '\'' +
                ", selectA='" + selectA + '\'' +
                ", selectB='" + selectB + '\'' +
                ", selectC='" + selectC + '\'' +
                ", selectD='" + selectD + '\'' +
                '}';
    }

    public String getTop_question() {
        return top_question;
    }

    public void setTop_question(String top_question) {
        this.top_question = top_question;
    }

    public String getCode_question() {
        return code_question;
    }

    public void setCode_question(String code_question) {
        this.code_question = code_question;
    }

    public String getBottom_question() {
        return bottom_question;
    }

    public void setBottom_question(String bottom_question) {
        this.bottom_question = bottom_question;
    }

    public String getSelectA() {
        return selectA;
    }

    public void setSelectA(String selectA) {
        this.selectA = selectA;
    }

    public String getSelectB() {
        return selectB;
    }

    public void setSelectB(String selectB) {
        this.selectB = selectB;
    }

    public String getSelectC() {
        return selectC;
    }

    public void setSelectC(String selectC) {
        this.selectC = selectC;
    }

    public String getSelectD() {
        return selectD;
    }

    public void setSelectD(String selectD) {
        this.selectD = selectD;
    }
}
