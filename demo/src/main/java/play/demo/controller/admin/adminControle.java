package play.demo.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import play.demo.service.admin.adminService;
import play.demo.utils.ApiResponse;

@RestController
@RequestMapping("/api/admin")
public class adminControle {
    private adminService adminService ;
   

    public adminControle(adminService adminService){
        this.adminService = adminService ;
    }   

    @DeleteMapping("delet-post/{id}")
    public ResponseEntity<?> DeletProdect(@PathVariable String id){
        ApiResponse<?> res = adminService.DeletProdect(id);
        return  ResponseEntity.ok(res);

    }

    @DeleteMapping("delet-user/{id}")
    public ResponseEntity<?> DeletUser(@PathVariable String id){
       ApiResponse<?> res=  adminService.Deletuser(id);
        return  ResponseEntity.ok(res);
    }


}
