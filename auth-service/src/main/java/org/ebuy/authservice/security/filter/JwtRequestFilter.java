package org.ebuy.authservice.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ebuy.authservice.security.AppUserDetails;
import org.ebuy.authservice.security.AppUserDetailsService;
import org.ebuy.authservice.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtHeader = request.getHeader("Authorization");
        if (jwtHeader != null && jwtHeader.toString().startsWith("Bearer ")) {
            String jwt = jwtHeader.toString().substring(7);
            String userEmail = jwtUtil.extractUserEmail(jwt);

            //check whether user is already --authenticated SecurityContextHolder.getContext().getAuthentication()--
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                AppUserDetails userDetails = (AppUserDetails) this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
