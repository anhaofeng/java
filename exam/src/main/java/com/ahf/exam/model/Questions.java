package com.ahf.exam.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Component
@Data
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

}
