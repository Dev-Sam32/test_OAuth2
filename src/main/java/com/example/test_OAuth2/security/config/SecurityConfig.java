package com.example.test_OAuth2.security.config;

import com.example.test_OAuth2.member.service.MemberService;
import com.example.test_OAuth2.security.handler.MemberAccessDeniedHandler;
import com.example.test_OAuth2.security.handler.MemberAuthenticationEntryPoint;
import com.example.test_OAuth2.security.handler.OAuth2MemberSuccessHandler;
import com.example.test_OAuth2.security.jwt.JwtTokenizer;
import com.example.test_OAuth2.security.jwt.filter.JwtVerificationFilter;
import com.example.test_OAuth2.security.service.OAuth2MemberService;
import com.example.test_OAuth2.security.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberService memberService;

    private final OAuth2MemberService oAuth2MemberService;

    //    @Value("${spring.security.oauth2.client.registration.google.clientId}")
//    private String google_clientId;
//
//    @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
//    private String google_clientSecret;
//
//    @Value("${spring.security.oauth2.client.registration.github.clientId}")
//    private String github_clientId;
//
//    @Value("${spring.security.oauth2.client.registration.github.clientSecret}")
//    private String github_clientSecret;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()

                .csrf().disable()

                .cors(withDefaults())

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션 사용 X, STATELESS 하게
                .and()

                .formLogin().disable()
                .httpBasic().disable()

                .exceptionHandling()                    // 예외 처리
                    .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                    .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()

                .apply(new CustomFilterConfigurer())    // Custom Configurer
                .and()

                .authorizeHttpRequests(authorize -> authorize
//                        .antMatchers(HttpMethod.POST, "/*/member").permitAll()
//                        .antMatchers(HttpMethod.PATCH, "/*/member/**").hasRole("USER")
//                        .antMatchers(HttpMethod.GET, "/*/member").hasRole("ADMIN")
//                        .antMatchers(HttpMethod.GET, "/*/member/**").hasAnyRole("USER", "ADMIN")
//                        .antMatchers(HttpMethod.DELETE, "/*/member/**").hasRole("USER")
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, authorityUtils, oAuth2MemberService))
                );

//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .formLogin().disable()
//
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated()
//                )
//
//                .oauth2Login();
////                .loginPage("/hello-oauth2").permitAll()
////                .defaultSuccessUrl("/")
////                .userInfoEndpoint();
////                .userService()
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // CustomConfigurer : JwtAutheticationFilter 등록
//    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
//        @Override
//        public void configure(HttpSecurity builder) throws Exception {
//            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
//
//            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
//            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
//
//            // JWT 인증 필터, 성공, 실패
//            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
//            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());
//
//            // JWT 검증
//            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);
//
//            builder
//                    .addFilter(jwtAuthenticationFilter)
//                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
//        }
//    }
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }

//    // ClientRegistration을 저장하기 위한 Responsitory
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        var clientRegistration = clientRegistration();       // ClientRegistration 인스턴스를 리턴
//
//        return new InMemoryClientRegistrationRepository(clientRegistration);   // ClientRegistration 을 메모리에 저장
//    }
//
//    // ClientRegistration 인스턴스를 생성
//    private ClientRegistration clientRegistration() {
//        // ⚠️ 현재는 Google만 있어서 Google만 만들어주면 되는데, 다른 Provider 사용시 수정 필요
//        return CommonOAuth2Provider
//                .GOOGLE
//                .getBuilder("google")
//                .clientId(google_clientId)
//                .clientSecret(google_clientSecret)
//                .build();
////        return CommonOAuth2Provider
////                .GITHUB
////                .getBuilder("github")
////                .clientId(github_clientId)
////                .clientSecret(github_clientSecret)
////                .build();
//    }
}

