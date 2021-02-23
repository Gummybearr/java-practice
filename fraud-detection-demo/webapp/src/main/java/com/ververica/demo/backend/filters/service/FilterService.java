package com.ververica.demo.backend.filters.service;

import com.ververica.demo.backend.filters.dto.FilterDto;
import com.ververica.demo.backend.filters.domain.Filter;
import com.ververica.demo.backend.filters.repository.FilterRepository;
import com.ververica.demo.backend.topics.domain.Topic;
import com.ververica.demo.backend.topics.service.TopicService;
import com.ververica.demo.backend.users.domain.User;
import com.ververica.demo.backend.users.service.UserService;
import com.ververica.demo.backend.utils.EntityProxy;
import com.ververica.demo.backend.utils.ErrorChecker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FilterService {

  private final FilterRepository filterRepository;

  private final TopicService topicService;
  private final UserService userService;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public FilterDto.Res getFilters(String topicName) throws Throwable {
      User user = userService.checkUserValidity("root");
      Topic topic = topicService.isTopicInDB(user, topicName);

      topicService.isTopicInKafka(user, topicName);

      return new FilterDto.Res(filterRepository
              .findByTopic(topic)
              .stream()
              .map(Filter::getField)
              .collect(Collectors.toList()));
  }

  public FilterDto.Res createFilter(FilterDto.FilterReq param, String topicName) throws Throwable {
      User user = userService.checkUserValidity("root");
      Topic topic = topicService.isTopicInDB(user, topicName);
      String field = param.getField();

      kafkaTemplate.send("filters", field);
      if(filterRepository.findByTopicAndField(topic, field).isEmpty()){ filterRepository.save(new Filter(topic, field)); }

      new EntityProxy<Filter, Exception>().entityCheckThrowException(filterRepository.findByTopicAndField(topic, field));

      return getFilters(topicName);
  }

  public FilterDto.Res updateFilter(FilterDto.FilterUpdateReq param, String topicName) throws Throwable {
      User user = userService.checkUserValidity("root");
      Topic topic = topicService.isTopicInDB(user, topicName);

      Filter filter = new EntityProxy<Filter, Exception>().entityCheckThrowException(
              filterRepository.findByTopicAndField(topic, param.getFieldBefore()));
      filter = new Filter(filter, param);
      filterRepository.save(filter);

      return getFilters(topicName);
  }

  public FilterDto.Res deleteFilter(FilterDto.FilterReq param, String topicName) throws Throwable {
      User user = userService.checkUserValidity("root");
      Topic topic = topicService.isTopicInDB(user, topicName);

      Filter filter = new EntityProxy<Filter, Exception>().entityCheckThrowException(
                filterRepository.findByTopicAndField(topic, param.getField()));

      filterRepository.delete(filter);
      return getFilters(topicName);
  }
}
