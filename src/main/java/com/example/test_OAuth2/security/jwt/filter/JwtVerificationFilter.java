package com.example.test_OAuth2.security.jwt.filter;

import com.example.test_OAuth2.security.jwt.JwtTokenizer;
import com.example.test_OAuth2.security.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {  // OncePerRequestFilter -> request 당 한번만 실행
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);  // veryfyJwt -> JWT를 검증 메서드
            setAuthenticationToContext(claims); // Authentication 객체를 SecurityContext에 저장 메서드
        } catch (SignatureException se) {       // Signature 가 다를 때
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {      // 유효기간이 만료 되었을 때
            request.setAttribute("exception", ee);
        } catch (Exception e) {                 // 그 외 예외처리
            request.setAttribute("exception", e);
        }
        // Exception을 catch한 후에 Exception을 다시 throw 한다든지하는 처리를 하지 않고, 단순히 request.setAttribute()를 설정하는 일 밖에 하지 않는다
        // --> 예외가 발생하게되면 SecurityContext에 클라이언트의 인증 정보(Authentication 객체)가 저장되지 않습니다.
        // --> 결국에는 AuthenticationException 발생

        filterChain.doFilter(request, response); // Security Filter 호출
    }

    // OncePerRequestFilter의 shouldNotFilter()를 오버라이드
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        // Authorization Header가 Null 이거나 "Bearer" 가 아니면, ShouldNotFilter
        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", ""); // JWS(JSON Web Token Signed) / "Bearer_“부분을 제거
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); // JWT 서명(Signature)을 검증하기 위한 Secret Key get, 암호화
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();   // 내부적으로 서명(Signature) 검증에 성공, JWT에서 Claims를 파싱

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");   // JWT에서 파싱한 Claims에서 username Get
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));  // JWT의 Claims에서 얻은 권한 정보를 기반으로 List<GrantedAuthority 를 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);  // username과 List<GrantedAuthority 를 포함한 Authentication 객체를 생성
        SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 Authentication 객체를 저장
    }
}