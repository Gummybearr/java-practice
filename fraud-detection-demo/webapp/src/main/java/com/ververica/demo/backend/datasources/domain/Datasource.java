package com.ververica.demo.backend.datasources.domain;

import com.ververica.demo.backend.attributes.dto.AttributeDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Datasource {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "datasource_id")
    private Long id;

    private String name;
    private Long columns;
    private Long latestData;

    public Datasource(String name, int columns, Long latestData) {
        this.name = name;
        this.columns = (long)columns;
        this.latestData = latestData;
    }

    public Datasource(String name, int columns, int latestData) {
        this.name = name;
        this.columns = (long)columns;
        this.latestData = (long)latestData;
    }

    public Datasource(Long id, String datasourceName, List<AttributeDto.Res> datasourceAttributeList, Long datasourceLatestData) {
        this.id = id;
        this.name = datasourceName;
        this.columns = (long)datasourceAttributeList.size();
        this.latestData = datasourceLatestData;
    }
}
