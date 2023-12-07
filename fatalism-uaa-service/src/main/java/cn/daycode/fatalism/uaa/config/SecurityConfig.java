package cn.daycode.fatalism.uaa.config;

import cn.daycode.fatalism.uaa.domain.IntegrationUserDetailsAuthenticationHandler;
import cn.daycode.fatalism.uaa.domain.IntegrationUserDetailsAuthenticationProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.*;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig implements WebSecurityConfigurer {

    @Bean
    public IntegrationUserDetailsAuthenticationHandler integrationUserDetailsAuthenticationHandler() {
        IntegrationUserDetailsAuthenticationHandler authenticationHandler = new IntegrationUserDetailsAuthenticationHandler();
        return authenticationHandler;
    }

    @Bean
    public IntegrationUserDetailsAuthenticationProvider integrationUserDetailsAuthenticationProvider() {
        IntegrationUserDetailsAuthenticationProvider provider = new IntegrationUserDetailsAuthenticationProvider(integrationUserDetailsAuthenticationHandler());
        return provider;
    }

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> integrationWebAuthenticationDetailsSource;


    private RequestCache getRequestCache(HttpSecurity http) {
        RequestCache result = http.getSharedObject(RequestCache.class);
        if (result != null) {
            return result;
        }
        HttpSessionRequestCache defaultCache = new HttpSessionRequestCache();
        defaultCache.setRequestMatcher(createDefaultSavedRequestMatcher(http));
        return defaultCache;
    }


    private RequestMatcher createDefaultSavedRequestMatcher(HttpSecurity http) {
        ContentNegotiationStrategy contentNegotiationStrategy = http
                .getSharedObject(ContentNegotiationStrategy.class);
        if (contentNegotiationStrategy == null) {
            contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        }

        RequestMatcher notFavIcon = new NegatedRequestMatcher(new AntPathRequestMatcher(
                "/**/favicon.ico"));

        MediaTypeRequestMatcher jsonRequest = new MediaTypeRequestMatcher(
                contentNegotiationStrategy, MediaType.APPLICATION_JSON);
        jsonRequest.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        RequestMatcher notJson = new NegatedRequestMatcher(jsonRequest);

        RequestMatcher notXRequestedWith = new NegatedRequestMatcher(
                new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest"));

        boolean isCsrfEnabled = http.getConfigurer(CsrfConfigurer.class) != null;

        List<RequestMatcher> matchers = new ArrayList<>();
        if (isCsrfEnabled) {
            RequestMatcher getRequests = new AntPathRequestMatcher("/**", "GET");
            matchers.add(0, getRequests);
        }
        matchers.add(notFavIcon);
        matchers.add(notJson);
        //matchers.add(notXRequestedWith);

        return new AndRequestMatcher(matchers);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new OAuth2AuthenticationManager();
    }


    @Override
    public void init(SecurityBuilder builder) throws Exception {
        //@todo
    }

    @Override
    public void configure(SecurityBuilder builder) throws Exception {
        //@todo
    }
}
