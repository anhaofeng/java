package com.ahf.exam.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Component
public class Message {
    @Id
    @GeneratedValue
    private Integer mes_id;
    private String mes_head;
    private String mes_content;
    private Date mes_time;
    private Integer mes_state;
    private  Integer mes_top;

    public Integer getMes_id() {
        return mes_id;
    }

    public void setMes_id(Integer mes_id) {
        this.mes_id = mes_id;
    }

    public String getMes_head() {
        return mes_head;
    }

    public void setMes_head(String mes_head) {
        this.mes_head = mes_head;
    }

    public String getMes_content() {
        return mes_content;
    }

    public void setMes_content(String mes_content) {
        this.mes_content = mes_content;
    }

    public Date getMes_time() {
        return mes_time;
    }

    public void setMes_time(Date mes_time) {
        this.mes_time = mes_time;
    }

    public Integer getMes_state() {
        return mes_state;
    }

    public void setMes_state(Integer mes_state) {
        this.mes_state = mes_state;
    }

    public Integer getMes_top() {
        return mes_top;
    }

    public void setMes_top(Integer mes_top) {
        this.mes_top = mes_top;
    }
}
