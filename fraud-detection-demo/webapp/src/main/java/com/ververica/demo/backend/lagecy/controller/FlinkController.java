///*
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
//
//package com.ververica.demo.backend.lagecy.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ververica.demo.backend.lagecy.entity.Rule;
//import com.ververica.demo.backend.lagecy.vo.RulePayload;
//import com.ververica.demo.backend.lagecy.vo.RulePayload.ControlType;
//import com.ververica.demo.backend.lagecy.vo.RulePayload.RuleState;
//import com.ververica.demo.backend.filters.repository.UserFilterRepository;
//import com.ververica.demo.backend.filters.service.FlinkFiltersService;
//import com.ververica.demo.backend.lagecy.service.FlinkRulesService;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//@AllArgsConstructor
//public class FlinkController {
//
//  private final UserFilterRepository repository;
//  private final FlinkRulesService flinkRulesService;
//  private final FlinkFiltersService flinkFiltersService;
//
//  private final ObjectMapper mapper = new ObjectMapper();
//
//  @GetMapping("/syncRules")
//  void syncRules() throws JsonProcessingException {
//    Rule command = createControllCommand(ControlType.EXPORT_RULES_CURRENT);
//    flinkRulesService.addRule(command);
//
//    //        repository.save(new Filter("root", "{filters: payerId}"));
//  }
//
//  @GetMapping("/clearState")
//  void clearState() throws JsonProcessingException {
//    Rule command = createControllCommand(ControlType.CLEAR_STATE_ALL);
//    //    flinkRulesService.addRule(command);
//  }
//
//  private Rule createControllCommand(ControlType clearStateAll) throws JsonProcessingException {
//    RulePayload payload = new RulePayload();
//    payload.setRuleState(RuleState.CONTROL);
//    payload.setControlType(clearStateAll);
//    Rule rule = new Rule();
//    rule.setRulePayload(mapper.writeValueAsString(payload));
//    return rule;
//  }
//}
