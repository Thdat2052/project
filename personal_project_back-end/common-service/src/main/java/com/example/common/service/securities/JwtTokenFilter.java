package com.example.common.service.securities;

import com.example.common.service.entities.UserEntity;
import com.example.common.service.utils.AuthUtils;
import com.example.common.service.utils.UrlUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenComponent jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if (UrlUtils.PUBLIC_URLS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }


        if (!AuthUtils.hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = AuthUtils.getTokenFromHeader(request);

        if (!jwtUtil.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }



    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {
        Claims claims = jwtUtil.parseClaims(token);
        String userName = (String) claims.get(Claims.SUBJECT);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userName);
        return CustomUserDetail.build(userEntity,new ArrayList<>());
    }
}