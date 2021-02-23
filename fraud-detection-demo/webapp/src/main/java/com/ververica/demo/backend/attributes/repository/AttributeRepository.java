package com.ververica.demo.backend.attributes.repository;

import com.ververica.demo.backend.attributes.domain.Attribute;
import com.ververica.demo.backend.datasources.domain.Datasource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    List<Attribute> findByDatasource(Datasource datasource);
    List<Attribute> findByName(String name);
}