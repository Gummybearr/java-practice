package com.ververica.demo.backend.datacubes.service;

import com.ververica.demo.backend.attributes.service.AttributeService;
import com.ververica.demo.backend.datacubes.domain.Datacube;
import com.ververica.demo.backend.datacubes.dto.DatacubeDto;
import com.ververica.demo.backend.datacubes.dto.DatacubeDto.Res;
import com.ververica.demo.backend.datacubes.repository.DatacubeRepository;
import com.ververica.demo.backend.datasources.domain.Datasource;
import com.ververica.demo.backend.dimensions.domain.Dimension;
import com.ververica.demo.backend.dimensions.service.DimensionService;
import com.ververica.demo.backend.measures.service.MeasureService;
import com.ververica.demo.backend.userDatasources.domain.UserDatasource;
import com.ververica.demo.backend.userDatasources.service.UserDatasourceService;
import com.ververica.demo.backend.users.domain.User;
import com.ververica.demo.backend.users.service.UserService;
import com.ververica.demo.backend.utils.EntityProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DatacubeService {

    private final DatacubeRepository datacubeRepository;

    private final UserService userService;
    private final UserDatasourceService userDatasourceService;
    private final DimensionService dimensionService;
    private final MeasureService measureService;
    private final AttributeService attributeService;

    public List<Res> getDatacubes(Datasource datasource) throws Throwable {
        User user = userService.checkUserValidity("root");
        UserDatasource userDatasource = userDatasourceService.checkUserDatasourceValidity(user, datasource);

        return datacubeRepository.findByUserDatasource(userDatasource)
                .stream()
                .map(datacube -> new Res(datacube, dimensionService.getDimensions(datacube), measureService.getMeasures(datacube)))
                .collect(Collectors.toList());
    }

    public DatacubeDto.DetailRes getDatacube(Long datacubeId) {
        Datacube datacube = checkDatacubeValidity(datacubeId);
        return new DatacubeDto.DetailRes(dimensionService.getDimensions(datacube), measureService.getMeasures(datacube));
   }

    public Res postDatacube(DatacubeDto.DatacubeReq param) throws Throwable {
        User user = userService.checkUserValidity("root");
        Long datasourceId = param.getDatasourceId();
        UserDatasource userDatasource = userDatasourceService.checkUserDatasourceValidity(user, datasourceId);

        Datacube datacube = datacubeRepository.save(new Datacube(userDatasource.getDatasource().getName(), userDatasource));
        List<Dimension> dimensionList = attributeService.convertAttributeToDimension(userDatasource.getDatasource(), datacube);
        dimensionService.saveDimensions(dimensionList);
        measureService.autoPostMeasure(datacube, dimensionList);

        return new Res(datacube, dimensionService.getDimensions(datacube), measureService.getMeasures(datacube));
    }

    public Res putDatacube(DatacubeDto.UpdateReq datacubePutPayload){
        Datacube datacube = checkDatacubeValidity(datacubePutPayload.getId());
        datacube = new Datacube(datacube, datacubePutPayload);
        datacubeRepository.save(datacube);
        return new Res(datacube, dimensionService.getDimensions(datacube), measureService.getMeasures(datacube));
    }

    public Res deleteDatacube(Long datacubeId){
        Datacube datacube = checkDatacubeValidity(datacubeId);
        dimensionService.deleteDimensionsByDatacube(datacube);
        datacubeRepository.delete(datacube);
        return new Res(datacube, dimensionService.getDimensions(datacube), measureService.getMeasures(datacube));
    }

    private Datacube checkDatacubeValidity(Long datacubeId) {
        return new EntityProxy<Datacube, Exception>().entityCheckThrowException(datacubeRepository.findById(datacubeId));
    }

}
