package com.ahf.exam.service;

import com.ahf.exam.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IMessage extends JpaRepository<Message,Integer>,JpaSpecificationExecutor<Message> {
    List<Message> findAll();

    @Override
    void deleteById(Integer integer);
}
