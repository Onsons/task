package com.etalytics.task.repositories;

import com.etalytics.task.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Long> {
    // Problem when age is passed in the url :(
    @Query(value = "select * from Messages m where (m.created < (NOW() - INTERVAL 1 MINUTE)) ", nativeQuery = true)
    List<Message> getMessagesByAge(@Param("age") int age);
    @Modifying
    @Query(value = "delete from Messages m where (m.created < (NOW() - INTERVAL 2 MINUTE)) ", nativeQuery = true)
    void deleteScheduledMessage ();
}
