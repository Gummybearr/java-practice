package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue
    private long id;
    private String username;

    public Member(String username) {
        this.username = username;
    }

    protected Member() {
        this("");
    }
}
