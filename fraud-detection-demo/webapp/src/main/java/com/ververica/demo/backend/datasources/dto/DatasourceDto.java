package com.ververica.demo.backend.datasources.dto;

import com.ververica.demo.backend.attributes.dto.AttributeDto;
import com.ververica.demo.backend.datacubes.dto.DatacubeDto;
import com.ververica.demo.backend.datasources.domain.Datasource;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class DatasourceDto {

    @Getter
    @ToString
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class DetailRes{
        private List<AttributeDto.Res> attributes;
        private List<DatacubeDto.Res> datacubes;
        private List<String> dashboards;

        public DetailRes(List<AttributeDto.Res> attributes, List<DatacubeDto.Res> datacubes){
            this.attributes = attributes;
            this.datacubes = datacubes;
            this.dashboards = new ArrayList<>();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Res {
        private Long id;
        private String name;
        private Long columns;
        private Long latestData;

        public Res(Datasource datasource) {
            this.id = datasource.getId();
            this.name = datasource.getName();
            this.columns = datasource.getColumns();
            this.latestData = datasource.getLatestData();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class ResList {
        List<DatasourceDto.Res> datasources;
    }
}
