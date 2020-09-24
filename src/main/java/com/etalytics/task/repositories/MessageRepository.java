package com.etalytics.task.repositories;

import com.etalytics.task.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Long> {
    // Tried to extract the time part and convert to minutes but .... :( ! I should change my approach
    @Query(value = "select * from Messages m where (sysdate() - m.created) >= :age ", nativeQuery = true)
    List<Message> getMessagesByAge(@Param("age") int age);
}
