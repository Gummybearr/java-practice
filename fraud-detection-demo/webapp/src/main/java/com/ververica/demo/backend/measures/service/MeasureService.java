package com.ververica.demo.backend.measures.service;

import com.ververica.demo.backend.datacubes.domain.Datacube;
import com.ververica.demo.backend.dimensions.domain.Dimension;
import com.ververica.demo.backend.dimensions.repository.DimensionRepository;
import com.ververica.demo.backend.measures.domain.Aggregation;
import com.ververica.demo.backend.measures.domain.Measure;
import com.ververica.demo.backend.measures.dto.MeasureDto;
import com.ververica.demo.backend.measures.dto.MeasureDto.Res;
import com.ververica.demo.backend.measures.repository.MeasureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MeasureService {

    private final MeasureRepository measureRepository;

    public List<Res> getMeasures(Datacube datacube){
        return measureRepository.findByDatacube(datacube)
                .stream()
                .map(Res::new)
                .collect(Collectors.toList());
    }

    public void autoPostMeasure(Datacube datacube, List<Dimension> dimensionList){
        dimensionList.stream()
                .filter(dimension -> !dimension.getType().equals("VARCHAR"))
                .forEach(dimension-> measureRepository.save(new Measure(datacube, dimension, Aggregation.Sum)));
    }

}
