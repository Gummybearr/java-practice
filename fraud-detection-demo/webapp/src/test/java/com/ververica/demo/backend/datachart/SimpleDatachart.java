package com.ververica.demo.backend.datachart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ververica.demo.backend.druid.dto.DruidDto;
import lombok.Data;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleDatachart {

    private String druidApiServer="http://localhost:8888";

    @Test
    public void simpleChartWithWikipediaExample() throws IOException {

        //data example
        String datasourceName = "wikipedia";

        List<String> show = new ArrayList<>();
        show.add("channel");

        String startTime = "2016-06-27 00:00:00";//time filtering
        String endTime = "2016-06-28 00:00:00";

        //query
        String simpleFixTimeQuery = "SELECT "+ showColumns(show)+" , "+"\n"
                +"COUNT(*) AS \"count\""+"\n"
                + "FROM "+ datasourceName+"\n"
        +"WHERE \"__time\" BETWEEN TIMESTAMP  '"+startTime+"' AND TIMESTAMP '"+endTime+"'"+"\n"
                + "GROUP BY 1\n" +
                "ORDER BY 2 DESC";

        //response
        List<BarChart> barCharts = queryDruid(simpleFixTimeQuery);

    }

    private List<BarChart> queryDruid(String simpleFixTimeQuery) throws IOException {
        String apiURL = "/druid/v2/sql";
        URI url = URI.create(druidApiServer+apiURL);

        DruidDto.AttributeReq druidQueryPayload = new DruidDto.AttributeReq(simpleFixTimeQuery);

        String druidQueryPayloadString = new ObjectMapper().writeValueAsString(druidQueryPayload);
        StringEntity entity = new StringEntity(druidQueryPayloadString);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(entity);

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        String notYetSerialized = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
                .collect(Collectors.joining());

        ObjectMapper mapper = new ObjectMapper();
        List<BarChart> barCharts = mapper.readValue(notYetSerialized,List.class);
        return barCharts;
    }

    //return data
    @Data
    static class BarChart{
        String category;
        Integer count;
    }

    //get dimension list and convert to string
    public String showColumns(List<String> stringList){
        return String.join(", ",stringList);
    }

}
