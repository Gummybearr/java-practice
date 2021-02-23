package com.ververica.demo.backend.datacubes.dto;

import com.ververica.demo.backend.datacubes.domain.Datacube;
import com.ververica.demo.backend.dimensions.dto.DimensionDto;
import com.ververica.demo.backend.measures.dto.MeasureDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class DatacubeDto {

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateReq{
        @NotNull
        private Long id;
        private String description;
        private String name;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class DetailRes {
        List<DimensionDto.Res> dimensions;
        List<MeasureDto.Res> measures;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Res {
        private Long id;
        private String name;
        private Long dimensions;
        private Long measures;

        public Res(Long id, String name, int dimensions, int measures){
            this.id = id;
            this.name = name;
            this.dimensions = (long)dimensions;
            this.measures = (long)measures;
        }

        public Res(Datacube datacube, List<DimensionDto.Res> dimensions, List<MeasureDto.Res> measures) {
            this.id = datacube.getId();
            this.name = datacube.getName();
            this.dimensions = (long)dimensions.size();
            this.measures = (long)measures.size();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResList {
        List<DatacubeDto.Res> datacubes;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DatacubeReq {
        private Long datasourceId;
    }
}
