package com.ahf.exam.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
@Component
@Data
public class Role {
    @Id
    @GeneratedValue
    private int res_id;
    private String res_name;
    private int res_state;

}
