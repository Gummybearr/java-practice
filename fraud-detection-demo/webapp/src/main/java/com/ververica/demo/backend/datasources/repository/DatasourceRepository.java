package com.ververica.demo.backend.datasources.repository;

import com.ververica.demo.backend.datasources.domain.Datasource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatasourceRepository extends JpaRepository<Datasource, Long> {
    List<Datasource> findByName(String name);
}
