package com.ververica.demo.backend.filters.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

public class FilterDto {

  @Getter
  @ToString
  @AllArgsConstructor
  @NoArgsConstructor
  public static class FilterReq {
    @NotNull
    private String field;
  }

  @Getter
  @ToString
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Res {
    List<String> filters;
  }

  @Getter
  @ToString
  @NoArgsConstructor
  @AllArgsConstructor
  public static class FilterUpdateReq {
    private String fieldBefore;
    private String fieldAfter;
  }
}
