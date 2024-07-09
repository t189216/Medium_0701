package com.ll.md0701.domain.home.home.controller;

import com.ll.md0701.domain.post.post.service.PostService;
import com.ll.md0701.global.rq.Rq.Rq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Tag(name = "HomeController", description = "홈 컨트롤러")
public class HomeController {
    private final Rq rq;
    private final PostService postService;

    @GetMapping("/")
    @Operation(summary = "메인")
    public String showMain() {
        rq.setAttribute("posts", postService.findTop30ByIsPublishedOrderByIdDesc(true));

        return "domain/home/home/main";
    }
}