package com.ververica.demo.backend.topics.service;

import com.ververica.demo.backend.topics.domain.Topic;
import com.ververica.demo.backend.topics.domain.TopicStatus;
import com.ververica.demo.backend.topics.dto.TopicDto;
import com.ververica.demo.backend.topics.dto.TopicDto.Res;
import com.ververica.demo.backend.topics.dto.TopicDto.ResList;
import com.ververica.demo.backend.topics.exception.TopicJpaErrorCode;
import com.ververica.demo.backend.topics.exception.TopicJpaException;
import com.ververica.demo.backend.topics.repository.TopicRepository;
import com.ververica.demo.backend.users.domain.User;
import com.ververica.demo.backend.users.service.UserService;
import com.ververica.demo.backend.utils.EntityProxy;
import com.ververica.demo.backend.utils.ErrorChecker;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TopicService {

  private final KafkaConsumer<String, Object> kafkaConsumer;
  private final TopicRepository topicRepository;
  private final UserService userService;

  public ResList getTopics() throws Throwable {
      User user = userService.checkUserValidity("root");
      HashMap<String, Topic> topicNameHashMap = makeTopicNameHashMap(topicRepository.findByUser(user));

      return new ResList(kafkaConsumer.listTopics()
              .keySet()
              .stream()
              .filter(this::isInternalTopic)
              .map(topic->{
                  if(topicNameHashMap.get(topic)!=null)
                      return new Res(topic, TopicStatus.valueOf(topicNameHashMap.get(topic).getTopicStatus()));
                  return new Res(topic, TopicStatus.inactive);
              })
              .collect(Collectors.toList()));
  }

  public String postTopic(ResList topicWrapperList) throws Throwable {
      User user = userService.checkUserValidity("root");

      HashMap<String, TopicStatus> topicWrapperHashMap = new HashMap<>();
      for (Res topicWrapper : topicWrapperList.getTopics()) {
          topicWrapperHashMap.put(topicWrapper.getName(), topicWrapper.getStatus());
      }

      List<String> kafkaTopicList = kafkaConsumer
              .listTopics()
              .keySet()
              .stream()
              .filter(this::isInternalTopic)
              .filter(topicWrapperHashMap::containsKey)
              .collect(Collectors.toList());

      new ErrorChecker<>().checkInputSizeFulfilled(kafkaTopicList.size(),
              topicWrapperList.getTopics().size(), new TopicJpaException.TopicSizeMismatchException(user, TopicJpaErrorCode.TOPIC_SIZE_MISMATCH));

      HashMap<String, Topic> topicNameHashMap = makeTopicNameHashMap(topicRepository.findByUser(user));

      for (Res topicWrapper : topicWrapperList.getTopics()) {
          String topicWrapperName = topicWrapper.getName();
          if (topicNameHashMap.containsKey(topicWrapperName)) {
              Topic topic = topicNameHashMap.get(topicWrapperName);
              topic.setTopicStatus(topicWrapper.getStatus());
              topicRepository.save(topic);
              continue;
          }
          Topic topic = new Topic(topicWrapperName, user, topicWrapper.getStatus());
          topicRepository.save(topic);
      }
      return null;
  }

  public Topic isTopicInDB(User user, String topicName){
      return new EntityProxy<Topic, Exception>().entityCheckThrowException(topicRepository.findByUserAndName(user, topicName));
  }

  public void isTopicInKafka(User user, String topicName) throws Throwable {
      new ErrorChecker<String, TopicJpaException.TopicNotFoundException>().checkEmptyListThrowError(kafkaConsumer
              .listTopics()
              .keySet()
              .stream()
              .filter(topicName::equals)
              .collect(Collectors.toList())
              , new TopicJpaException.TopicNotFoundException(user, topicName, TopicJpaErrorCode.TOPIC_NOT_FOUND));
  }

  private HashMap<String, Topic> makeTopicNameHashMap(List<Topic> topicList){
      HashMap<String, Topic> topicNameHashMap = new HashMap<>();
      for (Topic topic : topicList) { topicNameHashMap.put(topic.getName(), topic); }
      return topicNameHashMap;
  }

  private boolean isInternalTopic(String topic) {
      String internalTopic = "_";
      return !topic.startsWith(internalTopic);
  }

}
