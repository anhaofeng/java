package com.ahf.exam.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Component
public class Questions {
    @Id
    @GeneratedValue
    private int qes_id;//问题编号
    private int qes_subname;//试题科目
    private int qes_state;//问题状态
    private String qes_head;//题干
    private String  qes_answer;//问题答案
    private String qes_optionA;//选项A
    private String qes_optionB;//选项A
    private String qes_optionC;//选项C
    private String qes_optionD;//选项D
    private String qes_optionE;//选项E
    private String qes_optionF;//选项F
    private String qes_optionG;//选项G

    public int getQes_id() {
        return qes_id;
    }

    public void setQes_id(int qes_id) {
        this.qes_id = qes_id;
    }

    public int getQes_subname() {
        return qes_subname;
    }

    public void setQes_subname(int qes_subname) {
        this.qes_subname = qes_subname;
    }

    public int getQes_state() {
        return qes_state;
    }

    public void setQes_state(int qes_state) {
        this.qes_state = qes_state;
    }

    public String getQes_head() {
        return qes_head;
    }

    public void setQes_head(String qes_head) {
        this.qes_head = qes_head;
    }

    public String getQes_answer() {
        return qes_answer;
    }

    public void setQes_answer(String qes_answer) {
        this.qes_answer = qes_answer;
    }

    public String getQes_optionA() {
        return qes_optionA;
    }

    public void setQes_optionA(String qes_optionA) {
        this.qes_optionA = qes_optionA;
    }

    public String getQes_optionB() {
        return qes_optionB;
    }

    public void setQes_optionB(String qes_optionB) {
        this.qes_optionB = qes_optionB;
    }

    public String getQes_optionC() {
        return qes_optionC;
    }

    public void setQes_optionC(String qes_optionC) {
        this.qes_optionC = qes_optionC;
    }

    public String getQes_optionD() {
        return qes_optionD;
    }

    public void setQes_optionD(String qes_optionD) {
        this.qes_optionD = qes_optionD;
    }

    public String getQes_optionE() {
        return qes_optionE;
    }

    public void setQes_optionE(String qes_optionE) {
        this.qes_optionE = qes_optionE;
    }

    public String getQes_optionF() {
        return qes_optionF;
    }

    public void setQes_optionF(String qes_optionF) {
        this.qes_optionF = qes_optionF;
    }

    public String getQes_optionG() {
        return qes_optionG;
    }

    public void setQes_optionG(String qes_optionG) {
        this.qes_optionG = qes_optionG;
    }
}
