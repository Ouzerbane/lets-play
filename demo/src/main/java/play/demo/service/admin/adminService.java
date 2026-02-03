package play.demo.service.admin;

import org.springframework.stereotype.Service;

import play.demo.Entity.auth.Auth;
import play.demo.Entity.auth.Product;
import play.demo.exception.CustomException;
import play.demo.reposetory.auth.authReposetory;
import play.demo.reposetory.auth.prodectRepository;
import play.demo.utils.ApiResponse;

@Service
public class adminService {

    private final prodectRepository prodectRepository;
    private final authReposetory authReposetory;

    public adminService(prodectRepository prodectRepository, authReposetory authReposetory) {
        this.prodectRepository = prodectRepository;
        this.authReposetory = authReposetory;
    }

    public ApiResponse<?> Deletuser(String id) {
        Auth user = authReposetory.findById(id).orElseThrow(() -> new CustomException("user", 404, "user not found"));
        if (user.getRole().equals("ADMIN")) {
            throw new CustomException("admin", 403, "you are not allow to remove admin");
        }
        authReposetory.delete(user);
        return new ApiResponse<>(true, null, "remove successfully");

    }


    public ApiResponse<?> DeletProdect(String id) {
        Product post = prodectRepository.findById(id).orElseThrow(() -> new CustomException("post", 404, "post not found"));
        prodectRepository.delete(post);
        return new ApiResponse<>(true, null, "remove successfully");

    }
}
