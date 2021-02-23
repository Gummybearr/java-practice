package com.ververica.demo.backend.dimensions.service;

import com.ververica.demo.backend.datacubes.domain.Datacube;
import com.ververica.demo.backend.dimensions.dto.DimensionDto;
import com.ververica.demo.backend.dimensions.domain.Dimension;
import com.ververica.demo.backend.dimensions.dto.DimensionDto.Res;
import com.ververica.demo.backend.dimensions.repository.DimensionRepository;
import com.ververica.demo.backend.utils.EntityProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DimensionService {

    private final DimensionRepository dimensionRepository;

    public List<Res> getDimensions(Datacube datacube){
        return dimensionRepository.findByDatacube(datacube)
                .stream()
                .map(Res::new)
                .collect(Collectors.toList());
    }

    public void saveDimensions(List<Dimension> dimensionList){ dimensionRepository.saveAll(dimensionList); }

    public Res deleteDimension(DimensionDto.Req dimensionRequest){
        Dimension dimension = new EntityProxy<Dimension, Exception>().entityCheckThrowException(
                dimensionRepository.findById(dimensionRequest.getId()));

        dimensionRepository.delete(dimension);
        return new Res(dimension);
    }

    public void deleteDimensionsByDatacube(Datacube datacube){
        List<Dimension> dimensionList = dimensionRepository.findByDatacube(datacube);
        dimensionRepository.deleteInBatch(dimensionList);
    }

}
