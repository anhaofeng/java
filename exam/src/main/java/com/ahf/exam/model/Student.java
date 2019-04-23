package com.ahf.exam.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

/**
 * 学生表
 * */
@Component
@Entity
@Data
@ToString
public class Student {
    @Id
    @GeneratedValue
    private int s_id;//学生id
    @OneToOne(fetch = FetchType.EAGER)
    private Role role;
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

    public Student(int s_id, String s_name, String s_uname, String s_pwd, int s_grade, int s_gender,
                   int s_error, int s_state) {
        super();
        this.s_id = s_id;
        this.s_name = s_name;
        this.s_uname = s_uname;
        this.s_pwd = s_pwd;
        this.s_grade = s_grade;
        this.s_gender = s_gender;
        this.s_error = s_error;
        this.s_state = s_state;
    }

}
