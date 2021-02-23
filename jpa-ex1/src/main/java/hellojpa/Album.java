package hellojpa;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "album")
public class Album extends Item{
    private String artist;
}
