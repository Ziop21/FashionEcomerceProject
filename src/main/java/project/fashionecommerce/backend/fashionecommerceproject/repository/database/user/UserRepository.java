package project.fashionecommerce.backend.fashionecommerceproject.repository.database.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
}
