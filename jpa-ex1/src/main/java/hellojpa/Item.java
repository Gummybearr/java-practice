package hellojpa;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Item {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private Long price;
//    private Long stockQuantity;

//    @ManyToMany(mappedBy = "items")
//    private List<Category> categoryList = new ArrayList<>();
}
