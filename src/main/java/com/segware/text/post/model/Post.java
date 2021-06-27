package com.segware.text.post.model;

import com.segware.text.upvote.model.UpVotes;
import com.segware.text.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String text;

    @Column(nullable = false)
    private Integer upCount;

    @Column(nullable = false)
    private Integer downCount;

    @Column(nullable = false)
    private LocalDate createdAt;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<UpVotes> votes;

}
