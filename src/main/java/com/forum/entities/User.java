package com.forum.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 10)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(length = 16)
    private String introduction;

    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "user")
    private List<Topic> topics;

    @OneToMany(mappedBy = "user")
    private List<Answer> answers;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "User_Like_Topic",
            joinColumns = { @JoinColumn(name = "id_user")},
            inverseJoinColumns = { @JoinColumn(name = "id_topic")}
    )
    private Set<Topic> likedTopics = new HashSet<>();

    public Set<Topic> getLikedTopics() {
        return likedTopics;
    }

    public void setLikedTopics(Set<Topic> likedTopics) {
        this.likedTopics = likedTopics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Optional getIntroduction() {
        return Optional.ofNullable(introduction);
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String displayContentOfOptional() {
        if (Optional.ofNullable(introduction).isPresent())
            return Optional.ofNullable(introduction).get();
        else
            return "";
    }

    public String displayParsedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return this.createdDate.format(formatter);
    }
}
