package com.ververica.demo.backend.lagecy.repository;

import com.ververica.demo.backend.lagecy.domain.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Integer> {
}
