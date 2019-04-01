package com.ahf.exam.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Component
@Data
public class Subject {
    private String qes_subname;
    @Id
    @GeneratedValue
    private int qes_subid;
    private int qes_state;
}
