package project.fashionecommerce.backend.fashionecommerceproject.repository.database.token;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends MongoRepository<TokenEntity, String> {
    Optional<TokenEntity> findByToken(String token);

    void deleteByUserId(String userId);

    boolean existsByUserId(String userId);
}