package com.ll.md0701.domain.post.post.repository;

import com.ll.md0701.domain.member.member.entity.Member;
import com.ll.md0701.domain.post.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop30ByPublishedOrderByIdDesc(boolean isPublished);

    Page<Post> findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(String kw, String kw_, Pageable pageable);

    <T> List<T> findByPublishedOrderByIdDesc(boolean isPublished, Class<T> type);

    <T> List<T> findByAuthorOrderByIdDesc(Member author, Class<T> type);

}