package com.ll.md0701.domain.post.post.dto;

import com.ll.md0701.domain.post.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class PostDto {
    @NonNull
    private long id;

    @NonNull
    private LocalDateTime createDate;

    @NonNull
    private LocalDateTime modifyDate;

    @NonNull
    private long authorId;

    @NonNull
    private String authorUsername;

    @NonNull
    private String title;

    @NonNull
    private String body;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createDate = post.getCreateDate();
        this.modifyDate = post.getModifyDate();
        this.authorId = post.getAuthor().getId();
        this.authorUsername = post.getAuthor().getUsername();
        this.title = post.getTitle();
        this.body = post.getBody();
    }
}