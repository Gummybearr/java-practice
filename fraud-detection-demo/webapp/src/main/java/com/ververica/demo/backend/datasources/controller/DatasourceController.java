package com.ververica.demo.backend.datasources.controller;

import com.ververica.demo.backend.datasources.dto.DatasourceDto.DetailRes;
import com.ververica.demo.backend.datasources.dto.DatasourceDto.ResList;
import com.ververica.demo.backend.datasources.service.DatasourceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class DatasourceController {

    private final DatasourceService druidDatasourceService;

    @GetMapping("/datasources")
    public ResponseEntity<ResList> getDatasources() throws IOException {
        return new ResponseEntity<>(druidDatasourceService.getDatasources(), HttpStatus.OK);
    }

    @GetMapping("/datasources/{datasourceId}")
    public ResponseEntity<DetailRes> getDatasourceMetadata(@PathVariable Long datasourceId) throws Throwable {
        return new ResponseEntity<>(druidDatasourceService.getDatasourceDetail(datasourceId), HttpStatus.OK);
    }

}
