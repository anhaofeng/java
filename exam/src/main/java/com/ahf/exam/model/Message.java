package com.ahf.exam.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Component
@Data
public class Message {
    @Id
    @GeneratedValue
    private Integer mes_id;
    private String mes_head;
    private String mes_content;
    private Date mes_time;
    private Integer mes_state;
    private  Integer mes_top;

}
