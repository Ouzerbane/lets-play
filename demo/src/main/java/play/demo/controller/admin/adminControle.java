package play.demo.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class adminControle {
    
   

    public adminControle(){
    }

    @DeleteMapping("delet-post/{id}")
    public ResponseEntity<?> DeletProdect(@PathVariable String id){

        return  ResponseEntity.ok("ok");

    }

    @DeleteMapping("delet-user/{id}")
    public ResponseEntity<?> DeletUser(@PathVariable String id){

        return  ResponseEntity.ok("ok");

    }


}
