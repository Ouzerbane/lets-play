package play.demo.controller.auth;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import play.demo.dto.authDto.loginDto;
import play.demo.dto.authDto.regesterDto;
import play.demo.service.authService;
import play.demo.utils.ApiResponse;

@RestController
@RequestMapping("/api/auth")

public class authController {
    private final authService authService ;

    public authController(authService authService){
        this.authService = authService ;
    }
 


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody loginDto auth , HttpServletResponse resp){
        String tocken = authService.loginService(auth);

        ResponseCookie cookie = ResponseCookie.from("jwt", tocken)
                .httpOnly(true)
                .path("/")
                // .maxAge(24 * 60 * 60)
                .secure(false)
                .build();
        resp.addHeader("Set-Cookie", cookie.toString());
        return  ResponseEntity.ok(true);
    }

    @PostMapping("regester")
    public ResponseEntity<?> regester(@Valid @RequestBody regesterDto auth){
        ApiResponse<?> res = authService.regesterService(auth);
        return ResponseEntity.ok(res);
    }
}
