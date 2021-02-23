package com.ververica.demo.backend.lagecy.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alert {

  public Alert(String alertPayload) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = mapper.readValue(alertPayload, Map.class);
    this.id =
        Integer.parseInt(String.valueOf(System.currentTimeMillis() % 1000000 * 100))
            + Integer.parseInt(map.get("ruleId").toString());
    this.ruleId = map.get("ruleId").toString();
    this.violatedRule = map.get("violatedRule").toString();
    this.triggeringEvent = map.get("triggeringEvent").toString();
    this.triggeringValue = map.get("triggeringValue").toString();
  }

  public Alert(Integer id, String alertPayload) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = mapper.readValue(alertPayload, Map.class);
    this.id = id;
    this.ruleId = map.get("ruleId").toString();
    this.violatedRule = map.get("violatedRule").toString();
    this.triggeringEvent = map.get("triggeringEvent").toString();
    this.triggeringValue = map.get("triggeringValue").toString();
  }

  @Id private Integer id;

  private String ruleId;
  private String violatedRule;

  private String triggeringEvent;
  private String triggeringValue;
}
