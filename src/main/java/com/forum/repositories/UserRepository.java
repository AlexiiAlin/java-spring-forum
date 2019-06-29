package com.forum.repositories;

import com.forum.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByUsername(String username);
    User getUserById(long Id);

    List<User> findAll();

    @Query (
        value = "(SELECT SUM(points) FROM (SELECT COUNT(topic.id_user) AS points FROM topic WHERE topic.id_user = :id" +
                " UNION ALL SELECT 2 * COUNT(answer.id_user) AS points FROM answer WHERE answer.id_user = :id UNION ALL " +
                "SELECT 3 * COUNT(answer.id_user) AS points FROM answer WHERE answer.id_user = :id AND answer.useful = TRUE) t)",
        nativeQuery = true
    )
    Long getPoints(@Param("id") Long id);

//
//    @Transactional
//    void likeTopic(Long idUser, Long idTopic);
}
