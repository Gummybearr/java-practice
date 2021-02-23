package hellojpa;

import lombok.Data;

import javax.persistence.Entity;

@Entity
public class Book extends Item{
    private String author;
    private String isbn;
}
