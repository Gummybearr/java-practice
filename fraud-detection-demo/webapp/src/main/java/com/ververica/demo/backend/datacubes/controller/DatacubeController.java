package com.ververica.demo.backend.datacubes.controller;

import com.ververica.demo.backend.datacubes.dto.DatacubeDto.DetailRes;
import com.ververica.demo.backend.datacubes.dto.DatacubeDto.DatacubeReq;
import com.ververica.demo.backend.datacubes.dto.DatacubeDto.Res;
import com.ververica.demo.backend.datacubes.dto.DatacubeDto.UpdateReq;
import com.ververica.demo.backend.datacubes.service.DatacubeService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DatacubeController {

    private final DatacubeService datacubeService;

    @GetMapping(value = "/datacubes/{datacubeId}")
    @ApiOperation(value="데이터큐브 메타데이터 조회")
    private ResponseEntity<DetailRes> getDatacube(@PathVariable Long datacubeId){
        return new ResponseEntity<>(datacubeService.getDatacube(datacubeId), HttpStatus.OK);
    }

    @PostMapping(value = "/datacubes")
    @ApiOperation(value="데이터큐브 추가")
    private ResponseEntity<Res> postDatacube(@RequestBody DatacubeReq param) throws Throwable {
        return new ResponseEntity<>(datacubeService.postDatacube(param), HttpStatus.CREATED);
    }

    @PutMapping(value = "/datacubes")
    @ApiOperation(value = "데이터큐브 수정")
    private ResponseEntity<Res> putDatacube(@RequestBody @Valid UpdateReq param){
        return new ResponseEntity<>(datacubeService.putDatacube(param), HttpStatus.OK);
    }

    @DeleteMapping(value = "datacubes/{datacubeId}")
    @ApiOperation(value="데이터큐브 삭제")
    private ResponseEntity<Res> deleteDatacube(@PathVariable Long datacubeId){
        return new ResponseEntity<>(datacubeService.deleteDatacube(datacubeId), HttpStatus.OK);
    }

}