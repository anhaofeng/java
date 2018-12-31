package com.ahf.exam.service;

import com.ahf.exam.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IStuService extends JpaRepository<Student,Integer> {
    @Query(value = "select * from student where s_uname=:uname and s_pwd=:pwd",nativeQuery=true)
     Student stuLogin(@Param("uname") String uname, @Param("pwd") String pwd);

}
