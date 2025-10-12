package com.example.component.project2.security.filter;

import com.example.component.project2.config.JwtService;
import com.example.component.project2.repo.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component @RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {
  private final JwtService jwtService;
  private final UserRepository userRepo;

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) { chain.doFilter(req, res); return; }
    String jwt = authHeader.substring(7);
    String username = jwtService.extractUsername(jwt);
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      var user = userRepo.findByUsername(username).orElse(null);
      if (user != null && jwtService.isTokenValid(jwt, user)) {
        UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }
    chain.doFilter(req, res);
  }
}

