package com.beyondself.jalen.studyingandroid.domain;

import cn.bmob.v3.BmobUser;

/**
 * 用户信息类
 */
public class UserInfo extends BmobUser {
    String sex;
    String decription;
    Integer age;
    int Pic;
    Integer experience;

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public int getPic() {
        return Pic;
    }

    public void setPic(int pic) {
        Pic = pic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "sex='" + sex + '\'' +
                ", decription='" + decription + '\'' +
                ", age=" + age +
                '}';
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
