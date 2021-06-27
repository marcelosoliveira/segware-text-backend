package com.segware.text.upvote.model;

import com.segware.text.post.model.Post;
import com.segware.text.user.model.User;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class UpVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean upVotes;

    @Column(nullable = false)
    private Boolean downVotes;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;
}
