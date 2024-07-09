package com.ll.md0701.global.security;

import com.ll.md0701.domain.member.member.service.AuthTokenService;
import com.ll.md0701.global.rq.Rq.Rq;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final Rq rq;
    private final AuthTokenService authTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String redirectUrlAfterSocialLogin = rq.getCookieValue("redirectUrlAfterSocialLogin", "");

        if (rq.isFrontUrl(redirectUrlAfterSocialLogin)) {
            String accessToken = authTokenService.genAccessToken(rq.getMember());
            String refreshToken = rq.getMember().getRefreshToken();

            rq.destroySession();
            rq.setCrossDomainCookie("accessToken", accessToken);
            rq.setCrossDomainCookie("refreshToken", refreshToken);
            rq.removeCookie("redirectUrlAfterSocialLogin");

            response.sendRedirect(redirectUrlAfterSocialLogin);
            return;
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}