package jpabook.jpashop.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Member {
    @Id @GeneratedValue
    private Long id;
    @Column(name = "MEMBER_ID")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
