package com.forum.repositories;

import com.forum.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Long countTopicsByUser_Id(Long id);

    Topic findTopicById(Long id);

    List<Topic> findTopicsByCategoryOrderByCreatedDateDesc(String category);
    List<Topic> findTopicsByUser_IdOrderByCreatedDateDesc(Long id);
}
