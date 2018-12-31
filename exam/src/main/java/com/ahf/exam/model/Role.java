package com.ahf.exam.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
@Component
public class Role {
    @Id
    @GeneratedValue
    private int res_id;
    private String res_name;
    private int res_state;
    public int getRes_id() {
        return res_id;
    }
    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }
    public String getRes_name() {
        return res_name;
    }
    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }
    public int getRes_state() {
        return res_state;
    }
    public void setRes_state(int res_state) {
        this.res_state = res_state;
    }


}
