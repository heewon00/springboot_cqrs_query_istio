package com.kt.edu.thirdproject.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                     ReactiveAuthenticationManager authenticationManager,
                                                     ServerAuthenticationConverter authenticationConverter) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter);

        return http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/login","/h2-console/**")
                        .permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .build();
    }
/*
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) //session STATELESS
                .exceptionHandling()
                .authenticationEntryPoint(serverAuthenticationEntryPoint())
                .and()
                .authorizeExchange()
                .pathMatchers(NO_AUTH_LIST)
                .permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .anonymous().disable()
                .logout().disable()
                .headers()
                .frameOptions()
                .mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN)
                .and()
                .addFilterBefore(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        ;
        return http.build();
    }

    private ServerAuthenticationEntryPoint serverAuthenticationEntryPoint(){
        return (exchange, authEx) -> {
            String requestPath = exchange.getRequest().getPath().value();

            log.error("Unauthorized error: {}", authEx.getMessage());
            log.error("Requested path    : {}", requestPath);

            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);

            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED.value()
                    , LocalDateTime.now()
                    , authEx.getMessage()
                    , requestPath);

            try {
                byte[] errorByte = new ObjectMapper()
                        .registerModule(new JavaTimeModule())
                        .writeValueAsBytes(errorMessage);
                DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(errorByte);
                return serverHttpResponse.writeWith(Mono.just(dataBuffer));
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
                return serverHttpResponse.setComplete();
            }
        };
    }*/
}
