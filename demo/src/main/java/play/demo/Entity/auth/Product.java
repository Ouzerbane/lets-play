package play.demo.Entity.auth;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "products")
public class Product {

    @Builder.Default
    @Id
    private String id = UUID.randomUUID().toString();

    private String name;
    private String description;
    private Double price;

    @Indexed
    private String userId;
}
