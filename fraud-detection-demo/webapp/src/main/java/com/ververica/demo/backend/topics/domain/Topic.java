package com.ververica.demo.backend.topics.domain;

import com.ververica.demo.backend.users.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@OnDelete(action = OnDeleteAction.CASCADE)
public class Topic {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "topic_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  private String topicStatus;

  private String name;

  public Topic(String name, User user, TopicStatus topicStatus) {
    this.name = name;
    this.user = user;
    this.topicStatus = topicStatus.toString();
  }

  public void setTopicStatus(TopicStatus topicStatus) {
    this.topicStatus = topicStatus.toString();
  }
}