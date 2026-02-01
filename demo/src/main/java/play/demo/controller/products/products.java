package play.demo.controller.products;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import play.demo.Entity.auth.Auth;
import play.demo.dto.prodectDto.proderctEditReqDto;
import play.demo.dto.prodectDto.proderctReqDto;
import play.demo.reposetory.auth.prodectRepository;
import play.demo.service.prodectService;
import play.demo.utils.ApiResponse;

@RestController
@RequestMapping("/api/products")
public class products {
    private final prodectRepository prodectRepository;
    private final prodectService prodectService;

    public products(prodectRepository prodectRepository, prodectService prodectService) {
        this.prodectRepository = prodectRepository;
        this.prodectService = prodectService;
    }

    @PostMapping("/creat-prodect")
    public ResponseEntity<?> CreatProdect(@Valid @RequestBody proderctReqDto proderctReqDto) {
        Auth currentUser = (Auth) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        ApiResponse<?> response = prodectService.CreatProdect(proderctReqDto, currentUser);

        return ResponseEntity.ok(response);

    }

    @PatchMapping("/update-prodect")
    public ResponseEntity<?> UpdateProdect(@Valid @RequestBody proderctEditReqDto proderctReqDto) {
        Auth currentUser = (Auth) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        ApiResponse<?> res = prodectService.EditProdect(proderctReqDto, currentUser);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/delet-prodect/{id}")
    public ResponseEntity<?> DeletProdect(@Valid @PathVariable String id) {
        Auth currentUser = (Auth) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        ApiResponse<?> res = prodectService.DeletProdect(id, currentUser);

        return ResponseEntity.ok(res);

    }

    @GetMapping({ "", "/" })
    public ResponseEntity<?> GetProdect() {
        ApiResponse<?> res = prodectService.GetProdect();
        return ResponseEntity.ok(res);
    }

    @GetMapping("get-post/{id}")
    public ResponseEntity<?> GetProdectById(@PathVariable String id) {
        ApiResponse<?> res = prodectService.GetProdectById(id);
        return ResponseEntity.ok(res);
    }
}
