package play.demo.initialSettings;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import play.demo.Entity.auth.Auth;
import play.demo.reposetory.auth.authReposetory;

@Component
public class initialSettings implements CommandLineRunner {
    private final authReposetory authReposetory;

    public initialSettings(authReposetory authReposetory) {
        this.authReposetory = authReposetory;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!authReposetory.existsByRole("ADMIN")) {
            Auth admin = new Auth();
            admin.setUsername("youssef");
            admin.setPassword(new BCryptPasswordEncoder().encode("1234"));
            admin.setEmail("youussefouzerbane@gmail.com");
            admin.setRole("ADMIN");
            authReposetory.save(admin);
        }else {
            System.out.println("admin already exists");
        }
    }
}
// migration
