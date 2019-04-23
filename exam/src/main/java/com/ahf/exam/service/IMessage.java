package com.ahf.exam.service;

import com.ahf.exam.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static javafx.scene.input.KeyCode.T;

@Repository
public interface IMessage extends JpaRepository<Message,Integer>,JpaSpecificationExecutor<Message> {
    @Query(value = "select * from Message m where m.mes_state=1 order by m.mes_top desc,m.mes_time desc ",nativeQuery
            = true)
    List<Message> findAll();
    @Override
    void deleteById(Integer integer);

    Optional<Message> findById(Integer integer);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Message m set m.mes_head=?1,m.mes_content=?2 where m.mes_id=?3")
    int updateMessage(String head,String content,Integer id);
    @Transactional
    @Modifying
    @Query("update Message m set m.mes_top=?1 where m.mes_id=?2")
    int sortMessage(Integer top,Integer id);

}
