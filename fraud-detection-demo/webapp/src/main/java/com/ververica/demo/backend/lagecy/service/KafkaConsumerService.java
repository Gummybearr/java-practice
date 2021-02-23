/// *
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */

package com.ververica.demo.backend.lagecy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ververica.demo.backend.lagecy.domain.Alert;
import com.ververica.demo.backend.lagecy.domain.Rule;
import com.ververica.demo.backend.lagecy.repository.AlertRepository;
import com.ververica.demo.backend.lagecy.repository.RuleRepository;
import com.ververica.demo.backend.lagecy.dto.RulePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class KafkaConsumerService {

  private final SimpMessagingTemplate simpTemplate;
  private final RuleRepository ruleRepository;
  private final AlertRepository alertRepository;
  private final ObjectMapper mapper = new ObjectMapper();

  @Value("${web-socket.topic.alerts}")
  private String alertsWebSocketTopic;

  @Value("${web-socket.topic.latency}")
  private String latencyWebSocketTopic;

  @Autowired
  public KafkaConsumerService(
      SimpMessagingTemplate simpTemplate,
      RuleRepository ruleRepository,
      AlertRepository alertRepository) {
    this.simpTemplate = simpTemplate;
    this.ruleRepository = ruleRepository;
    this.alertRepository = alertRepository;
  }

  @KafkaListener(topics = "${kafka.topic.alerts}", groupId = "alerts")
  public void templateAlerts(@Payload String message) throws IOException {
    Alert alert = new Alert(message);
    Integer id = alert.getId();

    Optional<Alert> existingRule = alertRepository.findById(id);
    if (!existingRule.isPresent()) {
      alertRepository.save(alert);
      simpTemplate.convertAndSend(alertsWebSocketTopic, message);
    }
  }

  @KafkaListener(topics = "${kafka.topic.latency}", groupId = "latency")
  public void templateLatency(@Payload String message) {
    log.debug("{}", message);
    simpTemplate.convertAndSend(latencyWebSocketTopic, message);
  }

  @KafkaListener(topics = "${kafka.topic.current-rules}", groupId = "current-rules")
  public void templateCurrentFlinkRules(@Payload String message) throws IOException {
    log.info("{}", message);
    RulePayload payload = mapper.readValue(message, RulePayload.class);
    Integer payloadId = payload.getRuleId();
    Optional<Rule> existingRule = ruleRepository.findById(payloadId);
    if (!existingRule.isPresent()) {
      ruleRepository.save(new Rule(payloadId, mapper.writeValueAsString(payload)));
    }
  }
}
