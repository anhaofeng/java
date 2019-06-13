package com.restaurant.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Data
@Entity
@Table
public class Evaluate {
    @Id
    @GenericGenerator(name="myuuid",strategy="uuid")
    @GeneratedValue(generator="myuuid")
    private  String id;
    private Integer satisfied;
    private Integer unSatisfied;
    @OneToOne(mappedBy = "evaluate",cascade = CascadeType.ALL)
    private  Food food;
}
