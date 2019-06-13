package com.restaurant.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class Cook {
    @Id
    @GenericGenerator(name="myuuid",strategy="uuid")
    @GeneratedValue(generator="myuuid")
    private  String id;
    private String cookName;
    private String photo;
    @ManyToMany(targetEntity = Food.class)
   @JoinTable(name = "Cook_Food",joinColumns = {@JoinColumn(name ="Cook_id",referencedColumnName = "id")},
           inverseJoinColumns ={@JoinColumn(name = "Food_id",referencedColumnName = "id")} )
    private Set<Food> foods = new HashSet<Food>();

}
