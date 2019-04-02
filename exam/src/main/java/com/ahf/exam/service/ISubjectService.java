package com.ahf.exam.service;

import com.ahf.exam.model.Questions;
import com.ahf.exam.model.Student;
import com.ahf.exam.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ISubjectService extends JpaRepository<Subject,String> {
    @Query(value = "select * from subject where qes_subname=:subname ",nativeQuery=true)
    Subject findByName(@Param("subname") String subname);

    @Query("from Questions where qes_subname=?1")
    List<Questions> getQesBySub(int subname);

}
