package se331.lab.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;
  private final RestAuthenticationEntryPoint authenticationEntryPoint;
  private final RestAccessDeniedHandler accessDeniedHandler;

  public SecurityConfiguration(
    JwtAuthenticationFilter jwtAuthFilter,
    AuthenticationProvider authenticationProvider,
    LogoutHandler logoutHandler,
    RestAuthenticationEntryPoint authenticationEntryPoint,
    RestAccessDeniedHandler accessDeniedHandler
  ) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.authenticationProvider = authenticationProvider;
    this.logoutHandler = logoutHandler;
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.accessDeniedHandler = accessDeniedHandler;
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.headers((headers) -> {
      headers.frameOptions((frameOptions) -> frameOptions.disable());
    });
    http
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .csrf((crsf) -> crsf.disable())
      .authorizeHttpRequests((authorize) -> {
        authorize.requestMatchers("/api/v1/auth/**").permitAll()
          .requestMatchers(HttpMethod.GET, "/news", "/news/**").permitAll()
          .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
          .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
          .anyRequest().authenticated();
      })
      .exceptionHandling(exception -> exception
        .authenticationEntryPoint(authenticationEntryPoint)
        .accessDeniedHandler(accessDeniedHandler)
      )
      .sessionManagement((session) ->{
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      })
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .logout((logout) -> {
        logout.logoutUrl("/api/v1/auth/logout");
        logout.addLogoutHandler(logoutHandler);
        logout.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
      });
    return http.build();
  }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
  CorsConfiguration config = new CorsConfiguration();
  config.setAllowedOriginPatterns(List.of("*", "http://98.91.1.150:8001")); // Allow all origins for dev
  config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
  config.setAllowedHeaders(List.of("*"));
  config.setExposedHeaders(List.of("x-total-count"));
  config.setAllowCredentials(true);

  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  source.registerCorsConfiguration("/**", config);
  return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterBean() {
      FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
      bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
      return bean;
    }
}
