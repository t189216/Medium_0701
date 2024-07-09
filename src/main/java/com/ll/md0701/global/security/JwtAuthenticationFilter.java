package com.ll.md0701.global.security;

import com.ll.md0701.domain.member.member.service.MemberService;
import com.ll.md0701.global.rq.Rq.Rq;
import com.ll.md0701.global.rsData.RsData.RsData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Rq rq;
    private final MemberService memberService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if (!request.getRequestURI().startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (List.of("/api/v1/members/login", "/api/v1/members/join").contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null) {
            String refreshToken = bearerToken.substring("Bearer ".length());
            RsData<String> rs = memberService.refreshAccessToken(refreshToken);
            String accessToken = rs.getData();
            SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);
            rq.setLogin(securityUser);
        } else {
            String accessToken = rq.getCookieValue("accessToken", "");

            if (!accessToken.isBlank()) {
                if (!memberService.validateToken(accessToken)) {
                    String refreshToken = rq.getCookieValue("refreshToken", "");

                    RsData<String> rs = memberService.refreshAccessToken(refreshToken);
                    accessToken = rs.getData();
                    rq.setCrossDomainCookie("accessToken", accessToken);
                }

                SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);
                rq.setLogin(securityUser);
            }
        }

        filterChain.doFilter(request, response);
    }
}