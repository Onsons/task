package com.etalytics.task.controllers;

import com.etalytics.task.models.Message;
import com.etalytics.task.repositories.MessageRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageContrroller {

    @Autowired
    public MessageRepository messageRepository;

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    @PostMapping("/messages")
    public Message saveMessage(@RequestBody  Message message) {
        return messageRepository.save(new Message(message.getMessage(), message.getName(), message.getEmail()));
    }
    @PutMapping("/messages/{id}")
    public ResponseEntity <String> updateMessage(@RequestBody Message newMesg, @PathVariable Long id) {
        Message message = messageRepository.findById(id).get();
        if (message.getToken().equals(newMesg.getToken())) {
            message.setMessage(newMesg.getMessage());
            message.setUpdated(new Date());
            messageRepository.save(message);
        } else
            return new ResponseEntity<>("401 unautorized",
                    HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>("Updated!",
                HttpStatus.OK);
    }
    //View the Latest messages that not older than ageMax minutes
    @GetMapping("messages/filter")
    public  List<Message> getMessagesByAge(@RequestParam  int age)
    {

            return messageRepository.getMessagesByAge(age);

    }



    //Scheduled Job that deletes entries after 120 minutes
    @Transactional
    @Scheduled(fixedRate = 2000, initialDelay = 5000)
    public void delete() {
       // System.out.println("ok ok ok ");
       messageRepository.deleteScheduledMessage();

    }
}
