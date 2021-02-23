package com.ververica.demo.backend.filters.domain;

import com.ververica.demo.backend.filters.dto.FilterDto;
import com.ververica.demo.backend.topics.domain.Topic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor
@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
public class Filter {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "filter_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "topic_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Topic topic;

  private String field;
  private Long createdTime;
  private Long accessedTime;

  public Filter(Topic topic, String field) {
    this.topic = topic;
    this.field = field;
    this.createdTime = System.currentTimeMillis();
    this.accessedTime = System.currentTimeMillis();
  }

  public Filter(Long id, Topic topic, String field, Long accessedTime) {
    this.id = id;
    this.topic = topic;
    this.field = field;
    this.accessedTime = accessedTime;
  }

  public Filter(Filter filter, FilterDto.FilterUpdateReq param) {
    this.id = filter.getId();
    this.topic = filter.getTopic();
    this.field = param.getFieldAfter();
    this.createdTime = filter.getCreatedTime();
    this.accessedTime = System.currentTimeMillis();
  }
}
