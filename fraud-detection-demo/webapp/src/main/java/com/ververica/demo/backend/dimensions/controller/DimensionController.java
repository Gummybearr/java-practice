package com.ververica.demo.backend.dimensions.controller;

import com.ververica.demo.backend.dimensions.dto.DimensionDto;
import com.ververica.demo.backend.dimensions.dto.DimensionDto.Req;
import com.ververica.demo.backend.dimensions.dto.DimensionDto.Res;
import com.ververica.demo.backend.dimensions.service.DimensionService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DimensionController {

    private final DimensionService dimensionService;

    @DeleteMapping(value = "/dimensions")
    @ApiOperation(value="데이터큐브 디멘션 삭제")
    private ResponseEntity<Res> deleteDimension(@RequestBody Req param, @PathVariable Long datacubeId){
        return new ResponseEntity<>(dimensionService.deleteDimension(param), HttpStatus.OK);
    }
}
