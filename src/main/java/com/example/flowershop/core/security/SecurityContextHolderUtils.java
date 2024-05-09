package com.example.flowershop.core.security;

import com.example.flowershop.core.model.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class SecurityContextHolderUtils {

  public static Long getUserId() {
    return ((CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal()).getUserId();
  }

  public static void updateRole(Role newRole) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    ((CustomUserDetails) auth.getPrincipal()).setRole(newRole.name());
    Authentication newAuth = new UsernamePasswordAuthenticationToken(
        auth.getPrincipal(),
        auth.getCredentials(),
        List.of(new SimpleGrantedAuthority(newRole.name())));

    SecurityContextHolder.getContext().setAuthentication(newAuth);
  }
}
