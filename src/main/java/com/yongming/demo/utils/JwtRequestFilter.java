package com.yongming.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("执行到了doFilterInternal");
        final String authorizationHeader = request.getHeader("Authorization");

        String uid = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                Claims claims=JwtUtils.getClaimsByTokens(jwt);
                uid = claims.getSubject();
            } catch (ExpiredJwtException e) {
                // 令牌过期的处理逻辑
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired. Please obtain a new token.");
                return;
            } catch (Exception e) {
                // 其他令牌验证错误的处理逻辑
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token. Please provide a valid token.");
                return;
            }
        }

        // 验证令牌
        if (uid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //UserDetails userDetails = null; // 获取用户详细信息的逻辑，例如从数据库中查询用户信息

            /*if (jwtUtils.validateToken(jwt, userDetails)) {
                uidPasswordAuthenticationToken authenticationToken = new uidPasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }*/
        }

        chain.doFilter(request, response);
    }
}