package com.restaurant.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table
public class EatTime {
    @Id
    @GenericGenerator(name="myuuid",strategy="uuid")
    @GeneratedValue(generator="myuuid")
    private  String id;
    private  String timeName;
    @ManyToMany(targetEntity = Food.class)
    @JoinTable(name = "EatTime_Food",joinColumns = {@JoinColumn(name = "EatTime_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "Food_id",referencedColumnName = "id")})
    private Set<Food> foods = new HashSet<Food>();
}
