package com.ververica.demo.backend.dimensions.domain;

import com.ververica.demo.backend.attributes.domain.Attribute;
import com.ververica.demo.backend.attributes.domain.AttributeType;
import com.ververica.demo.backend.datacubes.domain.Datacube;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Dimension {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dimension_id")
    private Long id;

    private String name;
    private String description;
    private String type;
    private boolean isPinned;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "datacube_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Datacube datacube;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "attribute_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Attribute attribute;

    public Dimension(Attribute attribute, Datacube datacube, String description) {
        this.name = attribute.getName();
        this.attribute = attribute;
        this.type = attribute.getType();
        this.datacube = datacube;
        this.description = description;
        this.isPinned = false;
    }
}
