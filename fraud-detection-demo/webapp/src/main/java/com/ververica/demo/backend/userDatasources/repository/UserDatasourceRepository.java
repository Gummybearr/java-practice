package com.ververica.demo.backend.userDatasources.repository;

import com.ververica.demo.backend.datasources.domain.Datasource;
import com.ververica.demo.backend.userDatasources.domain.UserDatasource;
import com.ververica.demo.backend.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDatasourceRepository extends JpaRepository<UserDatasource, Long> {
    List<UserDatasource> findByUser(User user);
    List<UserDatasource> findByUserAndDatasource(User user, Datasource datasource);
}
