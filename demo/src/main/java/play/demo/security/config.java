package play.demo.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import play.demo.exception.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class config extends AbstractMongoClientConfiguration {

    private FilterJwt FilterJwt ;
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    public config(FilterJwt FilterJwt, CustomAccessDeniedHandler customAccessDeniedHandler){
        this.FilterJwt = FilterJwt ; 
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Override
    protected String getDatabaseName() {
        return "play_db";
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/regester", "/api/auth/login").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/").permitAll()
                        .anyRequest().authenticated())
                .logout(logout -> logout.disable())
                .exceptionHandling(exceptions -> exceptions.accessDeniedHandler(customAccessDeniedHandler))
                .addFilterBefore(FilterJwt, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CommandLineRunner checkConnection(MongoTemplate mongoTemplate) {
        return args -> {
            String dbName = mongoTemplate.getDb().getName();
            System.out.println("Connected to database: " + dbName);
        };
    }

}
