package com.ververica.demo.backend.users.domain;

import com.ververica.demo.backend.users.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@OnDelete(action = OnDeleteAction.CASCADE)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    private String email;
    private String name;
    private String password;

    public User(UserDto.UserReq userRequest){
        this.email = userRequest.getEmail();
        this.password = userRequest.getPassword();
        this.name = userRequest.getName();
    }
}
