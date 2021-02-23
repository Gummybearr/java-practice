package com.ververica.demo.backend.userDatasources.domain;

import com.ververica.demo.backend.datasources.domain.Datasource;
import com.ververica.demo.backend.users.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
@NoArgsConstructor
@OnDelete(action = OnDeleteAction.CASCADE)
public class UserDatasource {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_datasource_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="datasource_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Datasource datasource;

    public UserDatasource(User user, Datasource datasource) {
        this.user = user;
        this.datasource = datasource;
    }
}
