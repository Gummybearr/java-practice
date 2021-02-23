/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ververica.demo.backend.lagecy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AlertsController {

  //  private final RuleRepository repository;
  //  private final KafkaTransactionsPusher transactionsPusher;
  //  private SimpMessagingTemplate simpSender;
  //
  //  @Value("${web-socket.topic.alerts}")
  //  private String alertsWebSocketTopic;
  //
  //  @Autowired
  //  public AlertsController(
  //      RuleRepository repository,
  //      KafkaTransactionsPusher transactionsPusher,
  //      SimpMessagingTemplate simpSender) {
  //    this.repository = repository;
  //    this.transactionsPusher = transactionsPusher;
  //    this.simpSender = simpSender;
  //  }
  //
  //  ObjectMapper mapper = new ObjectMapper();

  //  @GetMapping("/rules/{id}/alert")
  //  AlertPayload mockAlert(@PathVariable Integer id) throws JsonProcessingException {
  //    Rule rule = repository.findById(id).orElseThrow(() -> new RuleNotFoundException(id));
  //    Transaction triggeringEvent = transactionsPusher.getLastTransaction();
  //    String violatedRule = rule.getRulePayload();
  //    BigDecimal triggeringValue = triggeringEvent.getPaymentAmount().multiply(new
  // BigDecimal(10));
  //
  //    AlertPayload alertPayload = new AlertPayload(rule.getId(), violatedRule, triggeringEvent,
  // triggeringValue);
  //
  //    String result = mapper.writeValueAsString(alertPayload);
  //
  //    simpSender.convertAndSend(alertsWebSocketTopic, result);
  //
  //    return alertPayload;
  //  }
}
