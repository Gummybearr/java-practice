package com.ververica.demo.backend.attributes.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

public class AttributeDto {

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Res {
        private String name;
        private String type;

        public Res(JsonNode columName, JsonNode dataType) {
            this.name = columName.textValue();
            this.type = dataType.textValue();
        }
    }
}
