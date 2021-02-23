package com.ververica.demo.backend.datacubes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ververica.demo.backend.datacubes.dto.DatacubeDto;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureMockMvc
public class DatacubeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("데이터큐브 조회시 데이터 큐브의 아이디가 존재하면, 200을 던진다.")
    @Transactional
    public void getValidDatacubeShouldReturn200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/datacubes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("데이터큐브 조회시 데이터 큐브의 아이디가 존재하지 않으면, 400을 던진다.")
    @Transactional
    public void getInvalidDatacubeShouldReturn400() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/datacubes/NULL")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("데이터큐브 생성시 데이터 소스의 아이디가 존재하면 201을 던진다")
    @Transactional
    public void postValidDatacubeShouldReturn201() throws Exception {
        DatacubeDto.DatacubeReq postBody = new DatacubeDto.DatacubeReq(1L);
        mvc.perform(MockMvcRequestBuilders
                .post("/api/datacubes")
                .content(new ObjectMapper().writeValueAsBytes(postBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("데이터큐브 생성시 데이터 소스의 아이디가 존재하지 않으면, 400을 던진다.")
    @Transactional
    public void postInvalidDatacubeShouldReturn400() throws Exception {
        DatacubeDto.DatacubeReq postBody = new DatacubeDto.DatacubeReq(-1L);
        mvc.perform(MockMvcRequestBuilders
                .post("/api/datacubes")
                .content(new ObjectMapper().writeValueAsBytes(postBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("데이터큐브 수정시 데이터 큐브의 아이디가 존재하고 입력값이 적합하면 200을 던진다.")
    @Transactional
    public void putValidDatacubeShouldReturn200() throws Exception {
        DatacubeDto.UpdateReq putBody = new DatacubeDto.UpdateReq(1L, "", "wikipedia");
        mvc.perform(MockMvcRequestBuilders
                .put("/api/datacubes")
                .content(new ObjectMapper().writeValueAsBytes(putBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("데이터큐브 수정시 데이터 큐브의 아이디가 존재하고 입력값이 적합하지 않으면, 405을 던진다.")
    @Transactional
    public void putInValidDatacubeBodyShouldReturn405() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .put("/api/datacubes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("데이터큐브 수정시 데이터 큐브의 아이디가 존재하지 않으면, 405을 던진다.")
    @Transactional
    public void putInValidDatacubeShouldReturn405() throws Exception {
        DatacubeDto.UpdateReq putBody = new DatacubeDto.UpdateReq(1L, "", "wikipedia");
        mvc.perform(MockMvcRequestBuilders
                .put("/api/datacubes/NULL")
                .content(new ObjectMapper().writeValueAsBytes(putBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("데이터큐브 삭제시 데이터 소스의 아이디가 존재하면, 200을 던진다.")
    @Transactional
    public void deleteValidDatacubeShouldReturn200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/datacubes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("데이터큐브 삭제시 데이터 소스의 아이디가 존재하지 않으면, 400을 던진다.")
    @Transactional
    public void deleteInvalidDatacubeShouldReturn400() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/datacubes/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}