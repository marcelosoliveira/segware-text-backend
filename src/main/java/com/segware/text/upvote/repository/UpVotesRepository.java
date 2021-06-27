package com.segware.text.upvote.repository;

import com.segware.text.upvote.model.UpVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface UpVotesRepository extends JpaRepository<UpVotes, Long> {
    Optional<UpVotes> findByUserIdAndPostId(UUID id, Long id1);
}
