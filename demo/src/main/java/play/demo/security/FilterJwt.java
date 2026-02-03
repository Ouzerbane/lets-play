package play.demo.security;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import play.demo.Entity.auth.Auth;
import play.demo.reposetory.auth.authReposetory;
import play.demo.utils.ApiResponse;
import play.demo.utils.ErrorItem;
import play.demo.utils.Jwt;
import tools.jackson.databind.ObjectMapper;

@Component
public class FilterJwt extends OncePerRequestFilter {

    private final Jwt jwt;
    private final authReposetory authReposetory;

    public FilterJwt(Jwt jwt, @Lazy authReposetory auth) {
        this.jwt = jwt;
        this.authReposetory = auth;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.equals("/api/auth/regester") || path.equals("/api/auth/login") || path.equals("/api/products") || path.equals("/api/products/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = Jwt.extractTokenFromCookie(request);
        if (token == null || !jwt.isTokenValid(token)) {
            sendError(response, "token", "Invalid or missing token", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String userId = jwt.getUserIdFromToken(token);
        Auth user = authReposetory.findById(userId).orElse(null);

        if (user == null) {
            sendError(response, "user", "User not found", HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // if (!"ADMIN".equals(user.getType()) && "BAN".equals(user.getAction())) {
        // sendError(response, "user", "User is banned",
        // HttpServletResponse.SC_FORBIDDEN);
        // return;
        // }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            String role = user.getRole(); // ADMIN / USER

            List<SimpleGrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + role));

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
                    authorities);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            request.setAttribute("jwt", token);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);

    }

    private void sendError(HttpServletResponse response, String field, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");

        ApiResponse<Object> errorResponse = new ApiResponse<>(false, List.of(new ErrorItem(field, message)), null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), errorResponse);
    }

}
