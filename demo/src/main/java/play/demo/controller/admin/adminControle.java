package play.demo.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import play.demo.Entity.auth.Auth;
import play.demo.dto.UserDto.updatUserToAdminDto;
import play.demo.dto.prodectDto.proderctEditReqDto;
import play.demo.exception.CustomException;
import play.demo.service.admin.adminService;
import play.demo.utils.ApiResponse;

@RestController
@RequestMapping("/api/admin")
public class adminControle {
    private adminService adminService;

    public adminControle(adminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("delet-product/{id}")
    public ResponseEntity<?> DeletProdect(@PathVariable String id) {
        // Auth user = (Auth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // checkAdmin(user);
        ApiResponse<?> res = adminService.DeletProdect(id);
        return ResponseEntity.ok(res);

    }

    @DeleteMapping("delet-user/{id}")
    public ResponseEntity<?> DeletUser(@PathVariable String id) {
        // Auth user = (Auth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // checkAdmin(user);
        ApiResponse<?> res = adminService.Deletuser(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("get-users")
    public ResponseEntity<?> GetUsers() {
        // Auth user = (Auth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // checkAdmin(user);
        return ResponseEntity.ok(adminService.Getuser());
    }

    @PatchMapping("update-prodect")
    public ResponseEntity<?> UpdateProdect(@Valid @RequestBody proderctEditReqDto proderctReqDto) {
        // Auth user = (Auth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // checkAdmin(user);
        ApiResponse<?> res = adminService.UpdateProdect(proderctReqDto);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("update-user")
    public ResponseEntity<?> UpdateUser(@Valid @RequestBody updatUserToAdminDto proderctReqDto) {
        // Auth user = (Auth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // checkAdmin(user);
        ApiResponse<?> res = adminService.UpdateUser(proderctReqDto);
        return ResponseEntity.ok(res);
    }


   private void checkAdmin(Auth user) {
        if (!user.getRole().equals("ADMIN")) {
            throw new CustomException("admin",403,"you are not allow to access this resource");
        }
    }

}
