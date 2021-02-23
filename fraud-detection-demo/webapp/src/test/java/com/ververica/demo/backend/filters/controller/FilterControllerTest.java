package com.ververica.demo.backend.filters.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ververica.demo.backend.filters.dto.FilterDto;
import junit.framework.TestCase;
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

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-dev.yaml")
@SpringBootTest
@AutoConfigureMockMvc
public class FilterControllerTest extends TestCase {

    @Autowired private MockMvc mvc;

    @Test
    @DisplayName("토픽이 카프카에 있고 디비에도 존재할때 200을 던짐")
    public void topicValidShouldReturn200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/topics/alerts/filters")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("토픽이 카프카에만 있을 때 200을 던짐")
    public void topicOnlyInKafkaShouldReturn200() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/topics/livetransactions/filters")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("토픽이 카프카에 존재하지 않을 때 400을 던짐")
    public void topicInvalidShouldReturn400() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/topics/NULL/filters")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 토픽에 필터를 추가하려할 때 400을 던짐")
    @Transactional
    public void postInvalidFilterShouldReturn400() throws Exception {
        FilterDto.FilterReq postBody = new FilterDto.FilterReq("postmanTesting");
        mvc.perform(MockMvcRequestBuilders
                .post("/api/topics/NULL/filters")
                .content(new ObjectMapper().writeValueAsBytes(postBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("존재하는 토픽에 필터를 추가하려 할 때 201을 던짐")
    @Transactional
    public void postValidFilterShouldReturn201() throws Exception {
        FilterDto.FilterReq payload = new FilterDto.FilterReq("postTest");
        String postBody = new ObjectMapper().writeValueAsString(payload);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/topics/alerts/filters")
                .content(postBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("존재하는 필터를 업데이트하면 200을 던짐")
    @Transactional
    public void putValidFilterShouldReturn200() throws Exception {
        String filters = mvc.perform(MockMvcRequestBuilders
                .get("/api/topics/alerts/filters"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        FilterDto.Res filterWrapper = new ObjectMapper().readValue(filters, FilterDto.Res.class);
        String fieldBefore = filterWrapper.getFilters().get(0);
        String fieldAfter = "testing";
        FilterDto.FilterUpdateReq userFilterUpdatePayload = new FilterDto.FilterUpdateReq(fieldBefore, fieldAfter);
        String putBody = new ObjectMapper().writeValueAsString(userFilterUpdatePayload);
        mvc.perform(MockMvcRequestBuilders
                .put("/api/topics/alerts/filters")
                .content(putBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("존재하지 않는 필터를 업데이트하려 했을 때 400을 던짐")
    @Transactional
    public void putInValidFilterShouldReturn400() throws Exception {
        String fieldBefore = "does not exist";
        String fieldAfter = "testing";
        FilterDto.FilterUpdateReq userFilterUpdatePayload = new FilterDto.FilterUpdateReq(fieldBefore, fieldAfter);
        String putBody = new ObjectMapper().writeValueAsString(userFilterUpdatePayload);
        mvc.perform(MockMvcRequestBuilders
                .put("/api/topics/alerts/filters")
                .content(putBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("존재하는 필터를 삭제하면 200을 던짐")
    @Transactional
    public void deleteValidFilterShouldReturn201() throws Exception {
        FilterDto.FilterReq payload = new FilterDto.FilterReq("postTest");
        String postBody = new ObjectMapper().writeValueAsString(payload);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/topics/alerts/filters")
                .content(postBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders
                .delete("/api/topics/alerts/filters")
                .content(postBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("존재하지 않는 필터를 삭제하려하면 400을 던짐")
    @Transactional
    public void deleteInValidFilterShouldReturn201() throws Exception {
        FilterDto.FilterReq payload = new FilterDto.FilterReq("does not exist");
        String postBody = new ObjectMapper().writeValueAsString(payload);

        mvc.perform(MockMvcRequestBuilders
                .delete("/api/topics/alerts/filters")
                .content(postBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
