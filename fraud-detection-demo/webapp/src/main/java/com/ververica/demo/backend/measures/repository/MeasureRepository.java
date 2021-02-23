package com.ververica.demo.backend.measures.repository;

import com.ververica.demo.backend.datacubes.domain.Datacube;
import com.ververica.demo.backend.dimensions.domain.Dimension;
import com.ververica.demo.backend.measures.domain.Measure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasureRepository  extends JpaRepository<Measure, Long> {
    List<Measure> findByDatacube(Datacube datacube);
    List<Measure> findByDatacubeAndAggregationAndDimension(Datacube datacube, String aggregation, Dimension dimension);
}