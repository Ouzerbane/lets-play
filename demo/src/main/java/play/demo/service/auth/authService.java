package play.demo.service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import play.demo.Entity.auth.Auth;
import play.demo.dto.authDto.loginDto;
import play.demo.dto.authDto.regesterDto;
import play.demo.exception.CustomException;
import play.demo.reposetory.auth.authReposetory;
import play.demo.utils.ApiResponse;
import play.demo.utils.Jwt;

@Service
public class authService {

    private final authReposetory authReposetory;
    private final PasswordEncoder passwordEncoder;
    private final Jwt jwt;

    public authService(authReposetory AuthReposetory, PasswordEncoder passwordEncoder, Jwt jwt) {
        authReposetory = AuthReposetory;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    public ApiResponse<?> regesterService(regesterDto user) {
        if (authReposetory.existsByEmailOrUsername(user.getEmail(), user.getUsername()))
            throw new CustomException("email ore username", 401, "email or user alredy use");
        String hashpassword = passwordEncoder.encode(user.getPassword());
        Auth authEntity = Auth.builder().email(user.getEmail().toLowerCase()).password(hashpassword)
                .username(user.getUsername().toLowerCase()).role("USER").build();
        Auth auth = authReposetory.save(authEntity);
        return new ApiResponse<>(true, null, auth);

    }

    public String loginService(loginDto user) {

        Auth userAuth = authReposetory
                .findByEmailOrUsername(user.getUsernameOremail().toLowerCase(), user.getUsernameOremail().toLowerCase())
                .orElseThrow(() -> new CustomException("user", 404, "User not found"));
        if (!passwordEncoder.matches(user.getPassword(), userAuth.getPassword())) {
            throw new CustomException("password", 401, "Invalid password");
        }

        String jwtTocken = jwt.generateToken(userAuth.getId(), userAuth.getUsername());
        return jwtTocken;

    }
}
