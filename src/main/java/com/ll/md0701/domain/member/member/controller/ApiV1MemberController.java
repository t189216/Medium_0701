package com.ll.md0701.domain.member.member.controller;

import com.ll.md0701.domain.member.member.dto.MemberDto;
import com.ll.md0701.domain.member.member.entity.Member;
import com.ll.md0701.domain.member.member.service.MemberService;
import com.ll.md0701.global.rq.Rq.Rq;
import com.ll.md0701.global.rsData.RsData.RsData;
import com.ll.md0701.standard.base.Empty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/members", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "ApiV1MemberController", description = "회원 CRUD 컨트롤러")
@SecurityRequirement(name = "bearerAuth")
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @Getter
    @Setter
    public static class LoginRequestBody {
        @NotBlank
        private String username;

        @NotBlank
        private String password;
    }

    @AllArgsConstructor
    @Getter
    public static class LoginResponseBody {
        @NonNull
        private MemberDto item;
    }

    @PostMapping("/login")
    @Operation(summary = "로그인, 로그인 성공시 accessToken, refreshToken 쿠키 설정")
    public RsData<LoginResponseBody> login(
            @Valid @RequestBody LoginRequestBody body
    ) {
        RsData<MemberService.AuthAndMakeTokensResponseBody> authAndMakeTokensRs = memberService.authAndMakeTokens(body.getUsername(), body.getPassword());

        rq.setCrossDomainCookie("refreshToken", authAndMakeTokensRs.getData().getRefreshToken());
        rq.setCrossDomainCookie("accessToken", authAndMakeTokensRs.getData().getAccessToken());

        return RsData.of(
                authAndMakeTokensRs.getResultCode(),
                authAndMakeTokensRs.getMsg(),
                new LoginResponseBody(
                        new MemberDto(
                                authAndMakeTokensRs.getData().getMember()
                        )
                )
        );
    }

    @Getter
    public static class MeResponseBody {
        @NonNull
        private MemberDto item;

        public MeResponseBody(Member member) {
            this.item = new MemberDto(member);
        }
    }

    @GetMapping(value = "/me", consumes = ALL_VALUE)
    @Operation(summary = "내 정보")
    public RsData<MeResponseBody> getMe() {
        return RsData.of(
                "200",
                "내 정보 가져오기 성공",
                new MeResponseBody(rq.getMember())
        );
    }

    @PostMapping(value = "/logout", consumes = ALL_VALUE)
    @Operation(summary = "로그아웃")
    public RsData<Empty> logout() {
        rq.setLogout();

        return RsData.of("200", "로그아웃 성공");
    }
}