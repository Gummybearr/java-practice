package com.ververica.demo.backend.attributes.domain;

import com.ververica.demo.backend.attributes.dto.AttributeDto;
import com.ververica.demo.backend.datasources.domain.Datasource;
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
public class Attribute {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Long id;

    private String name;
    private String type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="datasource_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Datasource datasource;

    public Attribute(AttributeDto.Res attributeResponse, Datasource datasource) {
        this.name = attributeResponse.getName();
        this.type = attributeResponse.getType();
        this.datasource = datasource;
    }

    public Attribute(Datasource datasource, Attribute attribute, AttributeDto.Res attributeResponse){
        this.id = attribute.id;
        this.name = attributeResponse.getName();
        this.type = attributeResponse.getType();
        this.datasource = datasource;
    }
}