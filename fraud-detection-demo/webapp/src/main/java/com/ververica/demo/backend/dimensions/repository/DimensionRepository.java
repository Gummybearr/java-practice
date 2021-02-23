package com.ververica.demo.backend.dimensions.repository;

import com.ververica.demo.backend.datacubes.domain.Datacube;
import com.ververica.demo.backend.dimensions.domain.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DimensionRepository  extends JpaRepository<Dimension, Long> {
    List<Dimension> findByDatacube(Datacube datacube);
    List<Dimension> findByDatacubeAndName(Datacube datacube, String name);
}