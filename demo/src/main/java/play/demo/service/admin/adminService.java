package play.demo.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import play.demo.Entity.auth.Auth;
import play.demo.Entity.auth.Product;
import play.demo.dto.UserDto.UserDto;
import play.demo.dto.UserDto.updatUserToAdminDto;
import play.demo.dto.prodectDto.proderctEditReqDto;
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
        Product post = prodectRepository.findById(id)
                .orElseThrow(() -> new CustomException("post", 404, "post not found"));
        prodectRepository.delete(post);
        return new ApiResponse<>(true, null, "remove successfully");

    }

    public ApiResponse<?> Getuser() {
        List<Auth> alluser = authReposetory.findAll();
        List<UserDto> allUserDto = alluser.stream().map(ele -> {
            return new UserDto(ele.getId(), ele.getUsername(), ele.getEmail());
        }).collect(Collectors.toList());

        return new ApiResponse<>(true, null, allUserDto);

    }

    public ApiResponse<?> UpdateProdect(proderctEditReqDto proderctEditReqDto) {
        Product post = prodectRepository.findById(proderctEditReqDto.getId())
                .orElseThrow(() -> new CustomException("post", 404, "post not found"));
        if (proderctEditReqDto.getName() != null && !proderctEditReqDto.getName().equals(post.getName()))
            post.setName(proderctEditReqDto.getName());

        if (proderctEditReqDto.getDescription() != null
                && !proderctEditReqDto.getDescription().equals(post.getDescription()))
            post.setDescription(proderctEditReqDto.getDescription());

        if (proderctEditReqDto.getPrice() != null && !proderctEditReqDto.getPrice().equals(post.getPrice()))
            post.setPrice(proderctEditReqDto.getPrice());

        prodectRepository.save(post);
        return new ApiResponse<>(true, null, "update successfully");
    }


        public ApiResponse<?> UpdateUser(updatUserToAdminDto updatUserToAdminDto) {
            Auth user = authReposetory.findById(updatUserToAdminDto.getId())
                    .orElseThrow(() -> new CustomException("user", 404, "user not found"));
            if (user.getRole().equals("ADMIN")) {
                throw new CustomException("admin", 403, "you are not allow to update admin");
            }
            if (updatUserToAdminDto.getUsername() != null
                    && !updatUserToAdminDto.getUsername().equals(user.getUsername()))
                user.setUsername(updatUserToAdminDto.getUsername());
    
            if (updatUserToAdminDto.getEmail() != null && !updatUserToAdminDto.getEmail().equals(user.getEmail()))
                user.setEmail(updatUserToAdminDto.getEmail());
    
            if (updatUserToAdminDto.getRole() != null && !updatUserToAdminDto.getRole().equals(user.getRole()))
                user.setRole(updatUserToAdminDto.getRole());
    
            authReposetory.save(user);
            return new ApiResponse<>(true, null, "update successfully");
        }
}
