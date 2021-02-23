package com.ververica.demo.backend.measures.domain;

import com.ververica.demo.backend.datacubes.domain.Datacube;
import com.ververica.demo.backend.dimensions.domain.Dimension;
import com.ververica.demo.backend.measures.dto.MeasureDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Locale;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Measure {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measure_id")
    private Long id;

    private String name;
    private String description;
    private String aggregation;
    private boolean isDefault;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dimension_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dimension dimension;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "datacube_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Datacube datacube;

    public Measure(Datacube datacube, Dimension dimension, Aggregation aggregation) {
        this.name = dimension.getName()+aggregation.toString().toUpperCase(Locale.ROOT);
        this.description = "";
        this.aggregation = aggregation.toString();
        this.dimension = dimension;
        this.datacube = datacube;
        this.isDefault = false;
    }
}
