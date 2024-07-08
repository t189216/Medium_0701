package com.ll.md0701.domain.post.post.entity;

import com.ll.md0701.domain.member.member.entity.Member;
import com.ll.md0701.global.jpa.Base.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseTime {
    @ManyToOne
    private Member author;

    private String title;

    private String body;

    private boolean isPublished;
}