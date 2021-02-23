package com.ververica.demo.backend.filters.controller;

import com.ververica.demo.backend.filters.dto.FilterDto.FilterReq;
import com.ververica.demo.backend.filters.dto.FilterDto.Res;
import com.ververica.demo.backend.filters.dto.FilterDto.FilterUpdateReq;
import com.ververica.demo.backend.filters.service.FilterService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class FilterController {

  FilterService filterService;

  @GetMapping("topics/{topic}/filters")
  @ApiOperation(value = "토픽에 적용한 필터 목록 조회")
  private ResponseEntity<Res> getFilters(@PathVariable String topic) throws Throwable {
    return new ResponseEntity<>(filterService.getFilters(topic), HttpStatus.OK);
  }

  @PostMapping(value = "/topics/{topic}/filters")
  @ApiOperation(value = "토픽에 적용할 필터 추가")
  private ResponseEntity<Res> postFilter(
          @RequestBody FilterReq param, @PathVariable String topic) throws Throwable {
    return new ResponseEntity<>(filterService.createFilter(param, topic), HttpStatus.CREATED);
  }

  @PutMapping(value = "/topics/{topic}/filters")
  @ApiOperation(value = "토픽에 적용된 필터 수정")
  private ResponseEntity<Res> putFilter(
          @RequestBody FilterUpdateReq param, @PathVariable String topic) throws Throwable {
    return new ResponseEntity<>(filterService.updateFilter(param, topic), HttpStatus.OK);
  }

  @DeleteMapping(value = "/topics/{topic}/filters")
  @ApiOperation(value = "토픽에 적용된 필터 삭제")
  private ResponseEntity<Res> deleteFilter(
          @RequestBody FilterReq param, @PathVariable String topic) throws Throwable {
    return new ResponseEntity<>(filterService.deleteFilter(param, topic), HttpStatus.OK);
  }
}
