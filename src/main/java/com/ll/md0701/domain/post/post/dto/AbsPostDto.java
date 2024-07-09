package com.ll.md0701.domain.post.post.dto;

import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public interface AbsPostDto {
    @NonNull
    long getId();

    @NonNull
    LocalDateTime getCreateDate();

    @NonNull
    LocalDateTime getModifyDate();

    @NonNull
    long getAuthorId();

    @NonNull
    String getAuthorUsername();

    @NonNull
    String getTitle();
}