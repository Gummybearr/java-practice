package com.ververica.demo.backend.topics.dto;

import com.ververica.demo.backend.topics.domain.TopicStatus;
import lombok.*;

import java.util.List;

public class TopicDto {

  @Getter
  @ToString
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Res{
    private String name;
    private TopicStatus status;
  }

  @Getter
  @ToString
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ResList{
    private List<Res> topics;
  }

}
