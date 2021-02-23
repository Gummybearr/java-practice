package com.ververica.demo.backend.dimensions.dto;

import com.ververica.demo.backend.attributes.domain.AttributeType;
import com.ververica.demo.backend.dimensions.domain.Dimension;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class DimensionDto {

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Req{
        @NotNull
        private Long id;
        @NotNull
        private String name;
        private String description;
        @Valid
        private AttributeType type;
        @Valid
        private String attributeName;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Res {
        private Long id;
        private String name;
        private AttributeType type;
        private Boolean isDefault;
        private String attribute;

        public Res(Dimension dimension) {
            this.id = dimension.getId();
            this.name = dimension.getName();
            this.type = AttributeType.valueOf(dimension.getType());
            this.isDefault = dimension.isPinned();
            this.attribute = dimension.getAttribute().getName();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResList {
        List<Res> dimensions;
    }
}
