package com.segware.text.post.repository;

import com.segware.text.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByText(String text);

}
