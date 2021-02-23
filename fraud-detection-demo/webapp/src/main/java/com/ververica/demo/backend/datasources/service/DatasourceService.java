package com.ververica.demo.backend.datasources.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.ververica.demo.backend.attributes.dto.AttributeDto;
import com.ververica.demo.backend.attributes.service.AttributeService;
import com.ververica.demo.backend.datacubes.dto.DatacubeDto;
import com.ververica.demo.backend.datacubes.service.DatacubeService;
import com.ververica.demo.backend.datasources.domain.Datasource;
import com.ververica.demo.backend.datasources.dto.DatasourceDto;
import com.ververica.demo.backend.datasources.dto.DatasourceDto.DetailRes;
import com.ververica.demo.backend.datasources.dto.DatasourceDto.Res;
import com.ververica.demo.backend.datasources.dto.DatasourceDto.ResList;
import com.ververica.demo.backend.datasources.exception.DatasourceJpaErrorCode;
import com.ververica.demo.backend.datasources.exception.DatasourceJpaException;
import com.ververica.demo.backend.datasources.exception.DatasourceJpaException.DatasourceNotFoundException;
import com.ververica.demo.backend.datasources.repository.DatasourceRepository;
import com.ververica.demo.backend.druid.service.DruidService;
import com.ververica.demo.backend.userDatasources.service.UserDatasourceService;
import com.ververica.demo.backend.users.domain.User;
import com.ververica.demo.backend.users.exception.UserJpaException;
import com.ververica.demo.backend.users.service.UserService;
import com.ververica.demo.backend.utils.EntityProxy;
import com.ververica.demo.backend.utils.TimeUtils;
import com.ververica.demo.backend.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ververica.demo.backend.datasources.exception.DatasourceJpaErrorCode.DATASOURCE_NOT_FOUND;
import static com.ververica.demo.backend.users.exception.UserJpaErrorCode.INVALID_USER;

@Service
@RequiredArgsConstructor
public class DatasourceService {

    private final DatasourceRepository datasourceRepository;

    private final AttributeService attributeService;
    private final DatacubeService datacubeService;
    private final UserDatasourceService userDatasourceService;
    private final UserService userService;
    private final DruidService druidService;

    public ResList getDatasources() throws IOException {
        URI uri = druidService.getDatasourceListURI();
        JsonNode datasourceList = WebUtils.sendGetRequest(uri);

        List<String> datasourceNames = extractDatasourceNames(datasourceList);
        List<List<AttributeDto.Res>> datasourceAttributeList = attributeService.getAttributeLists(datasourceNames);
        List<Long> datasourceLatestData = extractDatasourceTimestamp(datasourceList);

        List<Datasource> syncDatasources = syncDatasourcesWithDB(datasourceNames, datasourceAttributeList, datasourceLatestData);
        return parseDatasourceToDatasourceResponse(syncDatasources);
    }

    public DetailRes getDatasourceDetail(Long datasourceId) throws Throwable {
        User user = userService.checkUserValidity("root");
        Datasource datasource = new EntityProxy<Datasource, DatasourceNotFoundException>().entityCheckThrowException(
                datasourceRepository.findById(datasourceId), new DatasourceNotFoundException(user.getEmail(), datasourceId, DATASOURCE_NOT_FOUND));

        List<AttributeDto.Res> attributeResponseList = attributeService.getAttributes(datasource.getName());
        userDatasourceService.postUserDatasource(user, datasource);
        List<DatacubeDto.Res> datacubeList = datacubeService.getDatacubes(datasource);

        return new DetailRes(attributeResponseList, datacubeList);
    }

    public List<Datasource> syncDatasourcesWithDB(List<String> datasourceNames, List<List<AttributeDto.Res>> datasourceAttributeList,
                                  List<Long> datasourceLatestData){
        List<Datasource> datasourceList = new ArrayList<>();
        IntStream.range(0, datasourceNames.size())
                .forEach (idx->datasourceList.add(
                        syncDatasourceWithDB(datasourceNames.get(idx),
                        datasourceAttributeList.get(idx), datasourceLatestData.get(idx))));
        return datasourceList;
    }

    public Datasource syncDatasourceWithDB(String datasourceName, List<AttributeDto.Res> datasourceAttributeList, Long datasourceLatestData){
        Datasource datasource = new EntityProxy<Datasource, Exception>().entityCheckReturnNullOnError(
                datasourceRepository.findByName(datasourceName));
        if(datasource==null){
            datasource = datasourceRepository.save(new Datasource(datasourceName, datasourceAttributeList.size(), datasourceLatestData));
            attributeService.postAttributes(datasourceAttributeList, datasource);
            return datasource;
        }

        Datasource updatedDatasource = new Datasource(datasource.getId(), datasourceName, datasourceAttributeList, datasourceLatestData);
        datasourceRepository.save(updatedDatasource);
        attributeService.postAttributes(datasourceAttributeList, updatedDatasource);
        return updatedDatasource;
    }

    public List<String> extractDatasourceNames(JsonNode objectList){
        List<String> datasourceNames = new ArrayList<>();
        objectList.forEach(datasource->datasourceNames.add(datasource.get("name").textValue()));
        return datasourceNames;
    }
    
    public List<Long> extractDatasourceTimestamp(JsonNode datasourceList){
        List<Long> timestamps = new ArrayList<>();
        if(datasourceList.size()==1) {
            timestamps.add(TimeUtils.convertStringToUnixTimestamp(datasourceList.get(0)
                    .get("segments")
                    .get("maxTime")
                    .textValue()));
            return timestamps;
        }
        datasourceList.forEach(datasource->timestamps.add(
                TimeUtils.convertStringToUnixTimestamp(
                datasource.get("properties")
                .get("segments")
                .get("maxTime")
                .textValue())));
        return timestamps;
    }

    private ResList parseDatasourceToDatasourceResponse(List<Datasource> datasourceList){
        return new ResList(datasourceList.stream()
                .map(Res::new)
                .collect(Collectors.toList()));
    }
}
