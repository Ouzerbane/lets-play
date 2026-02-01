package play.demo.reposetory.auth;

import org.springframework.data.mongodb.repository.MongoRepository;

import play.demo.Entity.auth.Product;

public interface prodectRepository extends MongoRepository<Product,String>{
    
    
}