package com.ververica.demo.backend.topics.repository;

import com.ververica.demo.backend.topics.domain.Topic;
import com.ververica.demo.backend.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
  List<Topic> findByUser(User user);
  List<Topic> findByUserAndName(User user, String topicName);
}
