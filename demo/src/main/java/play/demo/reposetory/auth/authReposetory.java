package play.demo.reposetory.auth;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import play.demo.Entity.auth.Auth;

public interface authReposetory extends MongoRepository<Auth, String> {
    boolean existsByEmailOrUsername(String email, String username);

    Optional<Auth> findByEmailOrUsername(String email, String username);

}
