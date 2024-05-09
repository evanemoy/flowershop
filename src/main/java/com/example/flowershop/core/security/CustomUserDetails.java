package com.example.flowershop.core.security;

import com.example.flowershop.core.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
  private String role;
  private String password;
  private String username;
  private Long userId;
  public CustomUserDetails(User user){
    this.role = user.getRole().name();
    this.password = user.getPassword();
    this.username = user.getUserName();
    this.userId = user.getUserId();
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(role).stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toSet());
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
