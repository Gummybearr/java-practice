package com.ververica.demo.backend.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class WebUtils {

    public static JsonNode sendGetRequest(URI uri){
        RequestEntity<String> requestEntity = new RequestEntity<>(HttpMethod.GET,uri);
        return new CustomRestTemplate().exchange(requestEntity, JsonNode.class).getBody();
    }

    public static HttpPost setHttpPostUriAndHeaderAndEntity(URI uri, String header, String body, StringEntity entity){
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader(header, body);
        httpPost.setEntity(entity);
        return httpPost;
    }

    public static String sendHttpClientRequest(HttpPost httpPost) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        String stringResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
                .collect(Collectors.joining());
        closeableHttpClient.close();
        return stringResponse;
    }

    public static URI mixUrl(String baseUrl, String apiUrl){ return URI.create(baseUrl+apiUrl); }

    public static JsonNode sendJsonAndRead(URI uri, Object body) throws IOException {
        CustomObjectMapper customObjectMapper = new CustomObjectMapper();
        String druidQueryPayloadString = customObjectMapper.writeValueAsString(body);
        StringEntity entity = new StringEntity(druidQueryPayloadString);

        HttpPost httpPost = WebUtils.setHttpPostUriAndHeaderAndEntity(uri, "Content-type", "application/json", entity);
        String notYetSerialized = sendHttpClientRequest(httpPost);
        return customObjectMapper.readValue(notYetSerialized,JsonNode.class);
    }

}
