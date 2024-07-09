package com.ll.md0701.domain.post.post.controller;

import com.ll.md0701.domain.member.member.entity.Member;
import com.ll.md0701.domain.post.post.dto.PostDto;
import com.ll.md0701.domain.post.post.entity.Post;
import com.ll.md0701.domain.post.post.service.PostService;
import com.ll.md0701.global.rq.Rq.Rq;
import com.ll.md0701.global.rsData.RsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/posts", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "ApiV1PostController", description = "글 CRUD 컨트롤러")
@SecurityRequirement(name = "bearerAuth")
public class ApiV1PostController {
    private final Rq rq;
    private final PostService postService;

    @Getter
    public static class GetItemsResponseBody {
        @NonNull
        private final List<PostDto> items;

        public GetItemsResponseBody(List<Post> posts) {
            this.items = posts
                    .stream()
                    .map(PostDto::new)
                    .toList();
        }
    }

    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary = "글 리스트")
    public RsData<GetItemsResponseBody> getItems() {
        Member member = rq.getMember();

        List<Post> posts = postService.findByIsPublished(true);

        return RsData.of(
                "200",
                "성공",
                new GetItemsResponseBody(posts)
        );
    }

    @Getter
    public static class GetMineResponseBody {
        @NonNull
        private final List<PostDto> items;

        public GetMineResponseBody(List<Post> posts) {
            this.items = posts
                    .stream()
                    .map(PostDto::new)
                    .toList();
        }
    }

    @GetMapping(value = "/mine", consumes = ALL_VALUE)
    @Operation(summary = "내 글 리스트")
    public RsData<GetMineResponseBody> getMine() {
        Member member = rq.getMember();

        List<Post> posts = postService.findByAuthor(member);

        return RsData.of(
                "200",
                "내 글 가져오기 성공",
                new GetMineResponseBody(posts)
        );
    }
}