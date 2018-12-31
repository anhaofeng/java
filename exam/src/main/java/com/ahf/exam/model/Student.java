package com.ahf.exam.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 学生表
 * */
@Component
@Entity
public class Student {
    @Id
    @GeneratedValue
    private int s_id;//学生id
    private int s_role;//学生角色
    private String s_name;//学生姓名
    private String s_uname;//用户名
    private String s_pwd;//登录密码
    private int s_grade;//学生成绩
    private int s_gender;//学生性别
    private int s_error;//错题号
    private int s_state;//是否可用
    public Student() {
        super();
    }

    public Student(int s_id, String s_name, int s_role, String s_uname, String s_pwd, int s_grade, int s_gender,
                   int s_error, int s_state) {
        super();
        this.s_id = s_id;
        this.s_name = s_name;
        this.s_role = s_role;
        this.s_uname = s_uname;
        this.s_pwd = s_pwd;
        this.s_grade = s_grade;
        this.s_gender = s_gender;
        this.s_error = s_error;
        this.s_state = s_state;
    }
    public int getS_id() {
        return s_id;
    }
    public void setS_id(int s_id) {
        this.s_id = s_id;
    }
    public int getS_role() {
        return s_role;
    }
    public void setS_role(int s_role) {
        this.s_role = s_role;
    }
    public String getS_name() {
        return s_name;
    }
    public void setS_name(String s_name) {
        this.s_name = s_name;
    }
    public String getS_uname() {
        return s_uname;
    }
    public void setS_uname(String s_uname) {
        this.s_uname = s_uname;
    }
    public String getS_pwd() {
        return s_pwd;
    }
    public void setS_pwd(String s_pwd) {
        this.s_pwd = s_pwd;
    }
    public int getS_grade() {
        return s_grade;
    }
    public void setS_grade(int s_grade) {
        this.s_grade = s_grade;
    }
    public int getS_gender() {
        return s_gender;
    }
    public void setS_gender(int s_gender) {
        this.s_gender = s_gender;
    }
    public int getS_error() {
        return s_error;
    }
    public void setS_error(int s_error) {
        this.s_error = s_error;
    }
    public int getS_state() {
        return s_state;
    }
    public void setS_state(int s_state) {
        this.s_state = s_state;
    }

    @Override
    public String toString() {
        return "Student{" +
                "s_id=" + s_id +
                ", s_role=" + s_role +
                ", s_name='" + s_name + '\'' +
                ", s_uname='" + s_uname + '\'' +
                ", s_pwd='" + s_pwd + '\'' +
                ", s_grade=" + s_grade +
                ", s_gender=" + s_gender +
                ", s_error=" + s_error +
                ", s_state=" + s_state +
                '}';
    }
}
