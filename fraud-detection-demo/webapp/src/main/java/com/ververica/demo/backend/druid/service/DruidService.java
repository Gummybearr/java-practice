package com.ververica.demo.backend.druid.service;

import com.ververica.demo.backend.druid.dto.DruidDto;
import com.ververica.demo.backend.utils.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class DruidService {

    @Value("${druid.server}")
    private String druidApiServer;

    public URI getDatasourceAttributeURI(){ return WebUtils.mixUrl(druidApiServer,"/druid/v2/sql"); }
    public URI getDatasourceListURI(){return WebUtils.mixUrl(druidApiServer, "/druid/coordinator/v1/datasources/?simple");}

    public DruidDto.AttributeReq setDruidDatasourceAttributeRequest(String datasourceName){
        String query = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='"+datasourceName+"'";
        return new DruidDto.AttributeReq(query);
    }
}
