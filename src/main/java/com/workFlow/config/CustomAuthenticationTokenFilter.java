package com.workFlow.config;

import com.workFlow.serviceImpl.UserDetailsServiceImpl;
import com.workFlow.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("current api called : : " + request.getRequestURI());

      try {
         String jwtToken = extractTokenFromRequest(request);
         if (StringUtils.hasText(jwtToken) && jwtUtil.validateToken(jwtToken)) {
            String username = jwtUtil.extractUsername(jwtToken);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
         }
      } catch (IllegalArgumentException e) {
          logger.info("Illegal Argument while fetching the username !!");
          e.printStackTrace();
      } catch (ExpiredJwtException e) {
          logger.info("Given jwt token is expired !!");
          e.printStackTrace();
      } catch (MalformedJwtException e) {
          logger.info("Some changed has done in token !! Invalid Token");
          e.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();

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
