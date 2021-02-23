package com.ververica.demo.backend.topics.controller;

import com.ververica.demo.backend.topics.dto.TopicDto.ResList;
import com.ververica.demo.backend.topics.service.TopicService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class TopicController {

  private final TopicService topicsService;

  @GetMapping("/topics")
  @ApiOperation(value = "적용 가능한 토픽 조회")
  private ResponseEntity<ResList> getTopics() throws Throwable {
    return new ResponseEntity<>(topicsService.getTopics(), HttpStatus.OK);
  }

  @PostMapping("/topics")
  @ApiOperation(value = "토픽 선택 정보 업데이트")
  private ResponseEntity<String> postTopics(@RequestBody ResList topicWrapperList) throws Throwable {
    return new ResponseEntity<>(topicsService.postTopic(topicWrapperList), HttpStatus.CREATED);
  }
}
