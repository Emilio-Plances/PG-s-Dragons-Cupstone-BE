package com.example.PG.s.Dragons.security;

import com.example.PG.s.Dragons.entities.User;
import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.exceptions.UnauthorizedException;
import com.example.PG.s.Dragons.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth= request.getHeader("Authorization");
        if(auth==null || !auth.startsWith("Bearer ")) try {
            throw new UnauthorizedException("Token not found!");
        }
        catch (UnauthorizedException e) {throw new RuntimeException(e.getMessage());}

        String token=auth.substring(7);
        try {jwtTools.validateToken(token);}
        catch(UnauthorizedException e){throw new RuntimeException(e.getMessage());}

        String username=jwtTools.extractUsernameFromToken(token);
        try {
            User user=userService.findByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        }
        catch (NotFoundException e){throw new RuntimeException(e.getMessage());}
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] pathsToMatch = {"/api/auth/**","/api/noAuth/**"};
        for(String path:pathsToMatch){
            if(new AntPathMatcher().match(path, request.getServletPath())) return true;
        }
        return false;
    }
}
