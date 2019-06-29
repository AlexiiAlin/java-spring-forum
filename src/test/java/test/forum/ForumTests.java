package test.forum;

import com.forum.ForumApplication;
import com.forum.entities.Answer;
import com.forum.entities.Topic;
import com.forum.entities.User;
import com.forum.repositories.AnswerRepository;
import com.forum.repositories.TopicRepository;
import com.forum.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = ForumApplication.class)
@ContextConfiguration(classes = ForumApplication.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ForumTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    public void whenFindByName_thenReturnUser() {
        // given
        User alex = new User();
        alex.setUsername("Alex");
        alex.setPassword("passwordAlex");

        entityManager.persist(alex);
        entityManager.flush();

        // when
        User found = userRepository.getUserByUsername("Alex");

        // then
        assertThat(found.getUsername())
                .isEqualTo("Alex");
    }

    @Test
    public void whenFindAll_returnAllUsers() {
        // given
        User alex = new User();
        alex.setUsername("Alex");
        alex.setPassword("passwordAlex");

        User alin = new User();
        alex.setUsername("Alin");
        alex.setPassword("passwordAlin");

        entityManager.persist(alex);
        entityManager.flush();

        // when
        List<User> found = userRepository.findAll();

        // then
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(alex);
        assertThat(found.stream().filter(e -> e.getUsername().equals("Alex")).findFirst())
                .isEqualTo(expectedUsers.stream().filter(e -> e.getUsername().equals("Alex")).findFirst());
    }

    @Test
    public void whenCountingTheTopicsByUserId_thenReturnTheSize() {
        // given
        User user = userRepository.getUserById(1L);

        Long count = topicRepository.countTopicsByUser_Id(1L);

        // then
        assertThat(count > 0)
                .isEqualTo(true);
    }

    @Test
    public void whenSettingAnAnswerToUseful_thenDoIt() {
        // given
        answerRepository.setUsefulForAnswer(true, 4L);

        Answer found = answerRepository.getOne(4L);

        // then
        assertThat(found.isUseful())
                .isEqualTo(true);
    }

    @Test
    public void whenCountingTheTopicsByUserId_returnTheCorrectSize() {
        // given
        User user = userRepository.getUserByUsername("TEST-USER");

        Topic newTopic = new Topic();
        newTopic.setContent("Content");
        newTopic.setTitle("Title");
        newTopic.setCategory("cat");
        newTopic.setUser(user);

        entityManager.persist(newTopic);
        entityManager.flush();

        // then
        User foundUser = userRepository.getUserByUsername("TEST-USER");
        Long nrOfTopics = topicRepository.countTopicsByUser_Id(foundUser.getId());

        assertThat(nrOfTopics)
                .isEqualTo(2);
    }

}