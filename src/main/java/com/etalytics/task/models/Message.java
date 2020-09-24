package com.etalytics.task.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue
    @Column(name = "ID", columnDefinition = "LONGVARBINARY")
    private Long id;
    @NotNull(message = "Message can not be empty") @Size(max= 512)
    private String message;
    @NotNull(message = "Name can not be empty") @Size(max= 256)
    private String name;
    @NotNull(message = "Email can not be empty") @Email(message = "Email should be valid")
    private String email;
    private Date created = new Date();
    private Date updated = new Date();
    private String token;

    public Message( String message, String name, String email) {
        this.message = message;
        this.name= name;
        this.email = email;
        created = new Date();
        updated = new Date();
        token = generateToken();
    }

    public String generateToken(){

        String uuid = UUID.randomUUID().toString();
        //System.out.println(uuid + " of length " + uuid.length());

        // Remove dashes
        String uuid2 = uuid.replaceAll("-", "");
        return uuid2;
    }
}
