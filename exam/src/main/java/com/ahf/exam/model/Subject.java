package com.ahf.exam.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Component
public class Subject {
    private String qes_subname;
    @Id
    @GeneratedValue
    private int qes_subid;
    private int qes_state;
    public String getQes_subname() {
        return qes_subname;
    }
    public void setQes_subname(String qes_subname) {
        this.qes_subname = qes_subname;
    }
    public int getQes_subid() {
        return qes_subid;
    }
    public void setQes_subid(int qes_subid) {
        this.qes_subid = qes_subid;
    }
    public int getQes_state() {
        return qes_state;
    }
    public void setQes_state(int qes_state) {
        this.qes_state = qes_state;
    }



}
