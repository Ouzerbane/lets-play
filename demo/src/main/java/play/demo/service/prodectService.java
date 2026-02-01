package play.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import play.demo.Entity.auth.Auth;
import play.demo.Entity.auth.Product;
import play.demo.dto.prodectDto.proderctEditReqDto;
import play.demo.dto.prodectDto.proderctReqDto;
import play.demo.dto.prodectDto.proderctResDto;
import play.demo.exception.CustomException;
import play.demo.reposetory.auth.prodectRepository;
import play.demo.utils.ApiResponse;

@Service
public class prodectService {
    private final prodectRepository prodectRepository;

    public prodectService(prodectRepository prodectRepository) {
        this.prodectRepository = prodectRepository;
    }

    public ApiResponse<?> CreatProdect(proderctReqDto Prodect, Auth Auth) {
        Product prodectDocument = Product.builder().description(Prodect.getDescription())
                .name(Prodect.getName()).price(Prodect.getPrice()).userId(Auth.getId()).build();
        prodectRepository.save(prodectDocument);
        return new ApiResponse<>(true, null, "pose save successfully");
    }

    public ApiResponse<?> EditProdect(proderctEditReqDto productReq, Auth Auth) {
        if (productReq.getId() == null)
            throw new CustomException("id", 400, "id should be not null");

        Product product = prodectRepository.findById(productReq.getId())
                .orElseThrow(() -> new CustomException("post", 404, "post not fund"));

        if (!product.getUserId().equals(Auth.getId()))
            throw new CustomException("post", 403, "you are not the owner");

        if (productReq.getName() != null) {
            if (productReq.getName().length() > 30 || productReq.getName().length() < 3)
                throw new CustomException("post name", 400, "name should be between 3 and 30");

            product.setName(productReq.getName());
        }

        if (productReq.getDescription() != null) {
            if (productReq.getDescription().length() > 50 || productReq.getDescription().length() < 6)
                throw new CustomException("post description", 400, "description should be between 6 and 50");

            product.setDescription(productReq.getDescription());
        }

        if (productReq.getPrice() != null) {
            if (productReq.getPrice() < 0)
                throw new CustomException("post price", 400, "price should be to 0");

            product.setPrice(productReq.getPrice());
        }
        prodectRepository.save(product);
        return new ApiResponse<>(true, null, "edit save successfully");
    }

    public ApiResponse<?> DeletProdect(String id, Auth curentUser) {
        Product product = prodectRepository.findById(id)
                .orElseThrow(() -> new CustomException("post", 404, "post not fund"));

        if (!product.getUserId().equals(curentUser.getId()))
            throw new CustomException("post", 403, "you are not the owner");

        prodectRepository.delete(product);

        return new ApiResponse<>(true, null, "delet post successfully");
    }

    public ApiResponse<?> GetProdectById(String Id) {
        Product prodect = prodectRepository.findById(Id)
                .orElseThrow(() -> new CustomException("post", 404, "post not fund"));
        proderctResDto res = proderctResDto.builder().description(prodect.getDescription()).name(prodect.getName())
                .price(prodect.getPrice()).id(prodect.getId()).userId(prodect.getUserId()).build();
                // System.out.println(res.getName());
        return new ApiResponse<>(true, null, res);

        // proderctResDto
    }

    public ApiResponse<?> GetProdect() {
       List<Product> prodect = prodectRepository.findAll();
                // .orElseThrow(() -> new CustomException("post", 404, "post not fund"));
        // proderctResDto res = proderctResDto.builder().description(prodect.getDescription()).name(prodect.getName())
        //         .price(prodect.getPrice()).id(prodect.getId()).userId(prodect.getUserId()).build();
        // return new ApiResponse<>(true, null, res);

        return new ApiResponse<>(true,null,prodect);

        // proderctResDto
    }
}
