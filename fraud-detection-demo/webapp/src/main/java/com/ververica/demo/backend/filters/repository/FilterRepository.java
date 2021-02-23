package com.ververica.demo.backend.filters.repository;

import com.ververica.demo.backend.filters.domain.Filter;
import com.ververica.demo.backend.topics.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilterRepository extends JpaRepository<Filter, Long> {
  List<Filter> findByTopic(Topic topic);
  List<Filter> findByField(String field);
  List<Filter> findByTopicAndField(Topic topic, String field);
}
