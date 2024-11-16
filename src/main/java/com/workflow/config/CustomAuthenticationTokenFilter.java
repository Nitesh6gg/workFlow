package com.workflow.config;

import com.workflow.serviceimpl.UserDetailsServiceImpl;
import com.workflow.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {

        log.info("Processing request for API endpoint: {}", request.getRequestURI());

        try {
         String jwtToken = extractTokenFromRequest(request);
         if (StringUtils.hasText(jwtToken) && jwtUtil.validateToken(jwtToken)) {
            String username = jwtUtil.extractUsername(jwtToken);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication  = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication );
         }
      } catch (IllegalArgumentException e) {
          log.warn("Illegal Argument while fetching the username !!");
      } catch (ExpiredJwtException e) {
          log.warn("Given jwt token is expired !!");
      } catch (MalformedJwtException e) {
          log.warn("Some changes has done in token !! Invalid Token");
      } catch (Exception e) {
          log.info("An unexpected error occurred during token processing", e);
      }
      filterChain.doFilter(request, response);
    }


    private String extractTokenFromRequest(HttpServletRequest request) {
        String tokenWithBearer = request.getHeader("Authorization");
        if (tokenWithBearer != null && tokenWithBearer.startsWith("Bearer ")) {
           return tokenWithBearer.substring(7, tokenWithBearer.length());
        }
        return null;
    }

}
