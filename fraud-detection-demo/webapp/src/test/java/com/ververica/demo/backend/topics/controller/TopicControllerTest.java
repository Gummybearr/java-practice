package com.ververica.demo.backend.topics.controller;

import org.junit.Before;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-dev.yaml")
@SpringBootTest
@AutoConfigureMockMvc
public class TopicControllerTest {

    @Autowired private MockMvc mvc;

    @Test
    @DisplayName("토픽 목록을 조회하면 200을 던짐")
    public void getTopicsShouldReturn200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("토픽 목록의 활성화 상태를 업데이트하면 201을 던짐")
    @Transactional
    public void postValidTopicsShouldReturn201() throws Exception {
        String payload = mvc.perform(MockMvcRequestBuilders
                .get("/api/topics"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        mvc.perform(MockMvcRequestBuilders
                .post("/api/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("토픽 목록을 업데이트하려 할 때 잘못된 body를 전달하면 400을 던짐")
    @Transactional
    public void postInvalidTopicsShouldReturn400() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("토픽 api에 put요청을 하면 415를 던짐")
    public void putTopicsShouldReturn405() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .put("/api/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("토픽 api에 delete요청을 하면 415를 던짐")
    public void deleteTopicsShouldReturn405() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}
