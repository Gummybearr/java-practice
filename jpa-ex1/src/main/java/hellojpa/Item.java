package hellojpa;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn
public abstract class Item extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    private String name;
    private Long price;
}
