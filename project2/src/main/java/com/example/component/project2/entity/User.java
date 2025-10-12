package com.example.component.project2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "_user")
public class User implements UserDetails {
  @Id @GeneratedValue
  private Long id;

  private String firstname;
  private String lastname;

  @Column(unique = true) private String username; // login id
  @Column(unique = true) private String email;

  private String password;
  private Boolean enabled;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private List<Role> roles = new ArrayList<>();

  // UserDetails
  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
  }
  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return enabled != null ? enabled : true; }
}

