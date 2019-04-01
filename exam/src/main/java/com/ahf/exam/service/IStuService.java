package com.ahf.exam.service;

import com.ahf.exam.model.Role;
import com.ahf.exam.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface IStuService extends JpaRepository<Student,Integer>,JpaSpecificationExecutor<Student> {
    @Query(value = "select * from student where s_uname=:uname and s_pwd=:pwd",nativeQuery=true)
     Student stuLogin(@Param("uname") String uname, @Param("pwd") String pwd);

    @Query(value = "select s.s_role from student s where s_uname=:uname",nativeQuery = true)
    Set<Role> findUserRoles(@Param("uname") String uname);

    String findS_pwdByS_uname(String username);
}
