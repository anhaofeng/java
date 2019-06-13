package com.restaurant.model;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class Food {
    @Id
    @GenericGenerator(name="myuuid",strategy="uuid")
    @GeneratedValue(generator="myuuid")
     private  String id;
     private  String foodName;
     private  String foodPhotoSrc;
    @ManyToMany(targetEntity = EatTime.class,mappedBy = "foods")
    @Cascade(CascadeType.ALL)
    private Set<EatTime> eatTimes = new HashSet<EatTime>();
    @OneToOne
    @JoinColumn(name = "evaluateId")
    private Evaluate evaluate;
    @ManyToMany(targetEntity = Cook.class,mappedBy = "foods" )
    @Cascade(CascadeType.ALL)
    private Set<Cook> cookSet = new HashSet<Cook>();
}
