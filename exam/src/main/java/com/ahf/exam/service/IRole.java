package com.ahf.exam.service;

import com.ahf.exam.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IRole extends JpaRepository<Role,Integer>,JpaSpecificationExecutor<Role> {
    List<Role> findAll();
}
