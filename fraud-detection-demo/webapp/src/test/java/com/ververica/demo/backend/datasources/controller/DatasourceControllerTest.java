package com.ververica.demo.backend.datasources.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureMockMvc
public class DatasourceControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("데이터소스 조회시 서버가 정상작동을 하면 200을 던진다.")
    @Transactional
    public void getValidDatasourceShouldReturn200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/datasources")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("데이터소스 메타데이터 조회시 데이터 소스가 존재하면 200을 던진다.")
    @Transactional
    public void getValidDatasourceMetadataShouldReturn200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/datasources/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("데이터소스 메타데이터 조회시 데이터 소스가 존재하면 200을 던진다.")
    @Transactional
    public void getInvalidDatasourceMetadataShouldReturn400() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/datasources/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}