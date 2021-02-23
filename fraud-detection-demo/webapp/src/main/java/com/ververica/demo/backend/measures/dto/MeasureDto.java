package com.ververica.demo.backend.measures.dto;

import com.ververica.demo.backend.measures.domain.Aggregation;
import com.ververica.demo.backend.measures.domain.Measure;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class MeasureDto {

    @AllArgsConstructor
    @Getter
    @ToString
    @NoArgsConstructor
    public static class MeasureReq {
        @NotNull
        private String name;
        private String description;
        @Valid
        private Aggregation aggregation;
        @NotNull
        private String column;
    }

    @AllArgsConstructor
    @Getter
    @ToString
    @NoArgsConstructor
    public static class Res{
        private String name;
        private String description;
        private Aggregation aggregation;
        private String column;

        public Res(Measure measure){
            this.name = measure.getName();
            this.description = measure.getDescription();
            this.aggregation = Aggregation.valueOf(measure.getAggregation());
            this.column = measure.getDimension().getName();
        }
    }
}
