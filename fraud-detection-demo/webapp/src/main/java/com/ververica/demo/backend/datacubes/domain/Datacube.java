package com.ververica.demo.backend.datacubes.domain;

import com.ververica.demo.backend.datacubes.dto.DatacubeDto;
import com.ververica.demo.backend.userDatasources.domain.UserDatasource;
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
public class Datacube {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "datacube_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_datasource_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserDatasource userDatasource;

    private String name;
    private String description;
    private Long refreshRate;
    private String duration;

    public Datacube(String name, UserDatasource userDatasource) {
        this.name = name;
        this.userDatasource = userDatasource;
        this.description="";
        this.refreshRate = (long)0;
        this.duration = "Day";
    }

    public Datacube(Datacube datacube, DatacubeDto.UpdateReq datacubePutPayload) {
        this.id = datacube.getId();
        this.userDatasource = datacube.getUserDatasource();
        this.name = datacubePutPayload.getName();
        this.description = datacubePutPayload.getDescription();
        this.refreshRate = datacube.getRefreshRate();
        this.duration = datacube.getDuration();
    }
}
