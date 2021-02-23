package com.ververica.demo.backend.attributes.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ververica.demo.backend.attributes.dto.AttributeDto;
import com.ververica.demo.backend.attributes.domain.Attribute;
import com.ververica.demo.backend.attributes.domain.AttributeType;
import com.ververica.demo.backend.attributes.dto.AttributeDto.Res;
import com.ververica.demo.backend.attributes.repository.AttributeRepository;
import com.ververica.demo.backend.datacubes.domain.Datacube;
import com.ververica.demo.backend.datasources.domain.Datasource;
import com.ververica.demo.backend.dimensions.domain.Dimension;
import com.ververica.demo.backend.druid.dto.DruidDto;
import com.ververica.demo.backend.druid.service.DruidService;
import com.ververica.demo.backend.utils.EntityProxy;
import com.ververica.demo.backend.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepository;

    private final DruidService druidService;

    public List<Res> getAttributes(String datasourceName) throws IOException {
        URI uri = druidService.getDatasourceAttributeURI();
        DruidDto.AttributeReq druidDatasourceAttributeRequest =
                druidService.setDruidDatasourceAttributeRequest(datasourceName);
        JsonNode attributeDumpList = WebUtils.sendJsonAndRead(uri, druidDatasourceAttributeRequest);
        return extractAttributeRes(attributeDumpList);
    }

    public void postAttributes(List<Res> attributeResponseList, Datasource datasource){
        attributeResponseList.forEach(attributeResponse -> postAttribute(attributeResponse, datasource));
    }

    public Attribute postAttribute(Res attributeResponse, Datasource datasource){
        Attribute attribute = new EntityProxy<Attribute, Exception>().entityCheckReturnNullOnError(
                attributeRepository.findByName(attributeResponse.getName()));
        if(attribute==null) return attributeRepository.save(new Attribute(attributeResponse, datasource));
        attribute = new Attribute(datasource, attribute, attributeResponse);
        return attributeRepository.save(attribute);
    }

    public List<List<Res>> getAttributeLists(List<String> datasourceNames) {
        return datasourceNames.stream()
                .map(datasourceName -> {
                    try { return getAttributes(datasourceName); }
                    catch (IOException e) {}
                    return null;
                })
                .collect(Collectors.toList());
    }

    public List<Dimension> convertAttributeToDimension(Datasource datasource, Datacube datacube){
        return attributeRepository
                .findByDatasource(datasource)
                .stream()
                .map(attribute -> new Dimension(attribute, datacube, ""))
                .collect(Collectors.toList());
    }

    private List<Res> extractAttributeRes(JsonNode attributeDumpList){
        List<Res> attributes = new ArrayList<>();
        attributeDumpList.forEach(attributeDump->attributes.add(
                new Res(attributeDump.get("COLUMN_NAME"), attributeDump.get("DATA_TYPE"))));
        return attributes;
    }

}
