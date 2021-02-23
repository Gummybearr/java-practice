package com.ververica.demo.backend.datacubes.repository;

import com.ververica.demo.backend.datacubes.domain.Datacube;
import com.ververica.demo.backend.userDatasources.domain.UserDatasource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatacubeRepository  extends JpaRepository<Datacube, Long> {
    List<Datacube> findByUserDatasource(UserDatasource userDatasource);
}