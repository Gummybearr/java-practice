package com.ververica.demo.backend.userDatasources.service;

import com.ververica.demo.backend.datasources.domain.Datasource;
import com.ververica.demo.backend.userDatasources.domain.UserDatasource;
import com.ververica.demo.backend.userDatasources.exception.UserDatasourceJpaErrorCode;
import com.ververica.demo.backend.userDatasources.exception.UserDatasourceJpaException;
import com.ververica.demo.backend.userDatasources.exception.UserDatasourceJpaException.DatasourceNotAllocatedException;
import com.ververica.demo.backend.userDatasources.repository.UserDatasourceRepository;
import com.ververica.demo.backend.users.domain.User;
import com.ververica.demo.backend.utils.EntityProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.ververica.demo.backend.userDatasources.exception.UserDatasourceJpaErrorCode.DATASOURCE_NOT_ALLOCATED;

@Service
@AllArgsConstructor
public class UserDatasourceService {
    private final UserDatasourceRepository userDatasourceRepository;

    public UserDatasource checkUserDatasourceValidity(User user, Long datasourceId) throws Throwable {
        return new EntityProxy<UserDatasource, DatasourceNotAllocatedException>().entityCheckThrowException(
                userDatasourceRepository.findByUser(user)
                .stream()
                .filter((userDatasource) -> datasourceId == extractDatasourceIdFromUserDatasourceId(userDatasource))
                .collect(Collectors.toList())
                ,new DatasourceNotAllocatedException(user, datasourceId, DATASOURCE_NOT_ALLOCATED)
        );
    }

    private Long extractDatasourceIdFromUserDatasourceId(UserDatasource userDatasource) {
        return userDatasource.getDatasource().getId();
    }

    public UserDatasource checkUserDatasourceValidity(User user, Datasource datasource){
        return new EntityProxy<UserDatasource, Exception>().entityCheckThrowException(
                userDatasourceRepository.findByUserAndDatasource(user, datasource));
    }

    public UserDatasource postUserDatasource(User user, Datasource datasource){
        UserDatasource userDatasource = new EntityProxy<UserDatasource, Exception>().entityCheckReturnNullOnError(
                userDatasourceRepository.findByUserAndDatasource(user, datasource));
        if(userDatasource== null) return userDatasourceRepository.save(new UserDatasource(user, datasource));
        return userDatasource;
    }

}
